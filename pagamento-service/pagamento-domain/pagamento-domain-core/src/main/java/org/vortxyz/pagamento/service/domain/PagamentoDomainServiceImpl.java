package org.vortxyz.pagamento.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.vortxyz.domain.event.publisher.DomainEventPublisher;
import org.vortxyz.domain.valueobject.Dinheiro;
import org.vortxyz.domain.valueobject.PagamentoStatus;
import org.vortxyz.pagamento.service.domain.entity.Credito;
import org.vortxyz.pagamento.service.domain.entity.CreditoHistorico;
import org.vortxyz.pagamento.service.domain.entity.Pagamento;
import org.vortxyz.pagamento.service.domain.event.PagamentoCanceladoEvent;
import org.vortxyz.pagamento.service.domain.event.PagamentoCompletoEvent;
import org.vortxyz.pagamento.service.domain.event.PagamentoEvent;
import org.vortxyz.pagamento.service.domain.event.PagamentoFalhoEvent;
import org.vortxyz.pagamento.service.domain.valueobject.CreditoHistoricoId;
import org.vortxyz.pagamento.service.domain.valueobject.TipoTransacao;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static org.vortxyz.domain.DomainConstants.FUSO_HORARIO;

@Slf4j
public class PagamentoDomainServiceImpl implements PagamentoDomainService {

    @Override
    public PagamentoEvent validarEIniciarPagamento(Pagamento pagamento, Credito credito, List<CreditoHistorico> creditoHistoricos, List<String> mensagensFalha, DomainEventPublisher<PagamentoCompletoEvent> pagamentoCompletoEventDomainEventPublisher, DomainEventPublisher<PagamentoFalhoEvent> pagamentoFalhoEventDomainEventPublisher) {

        pagamento.validarPagamento(mensagensFalha);
        pagamento.inicializarPagamento();
        validarCredito(pagamento, credito, mensagensFalha);
        subrairCredito(pagamento, credito);
        atualizarCreditoHistorico(pagamento, creditoHistoricos, TipoTransacao.DEBITO);
        validarCreditoHistorico(credito, creditoHistoricos, mensagensFalha);

        if(mensagensFalha.isEmpty()) {
            log.info("Pagamento está inicializado para pedido com id: {}", pagamento.getPedidoId().getValue());
            pagamento.atualizarStatus(PagamentoStatus.COMPLETO);
            return new PagamentoCompletoEvent(pagamento, ZonedDateTime.now(ZoneId.of(FUSO_HORARIO)), pagamentoCompletoEventDomainEventPublisher);
        } else {
            log.info("Inicialização do pagamento falhou para pedido com id: {}", pagamento.getPedidoId().getValue());
            pagamento.atualizarStatus(PagamentoStatus.FALHO);
            return new PagamentoFalhoEvent(pagamento, ZonedDateTime.now(ZoneId.of(FUSO_HORARIO)), mensagensFalha, pagamentoFalhoEventDomainEventPublisher);
        }
    }

    @Override
    public PagamentoEvent validarECancelarPagamento(Pagamento pagamento, Credito credito, List<CreditoHistorico> creditoHistoricos, List<String> mensagensFalha, DomainEventPublisher<PagamentoCanceladoEvent> pagamentoCanceladoEventPublisher, DomainEventPublisher<PagamentoFalhoEvent> pagamentoFalhoEventDomainEventPublisher) {

        pagamento.validarPagamento(mensagensFalha);
        somarCredito(pagamento, credito);
        atualizarCreditoHistorico(pagamento, creditoHistoricos, TipoTransacao.CREDITO);

        if(mensagensFalha.isEmpty()) {
            log.info("Pagamento está cancelado para o pedido com id: {}.", pagamento.getPedidoId().getValue());
            pagamento.atualizarStatus(PagamentoStatus.CANCELADO);
            return new PagamentoCanceladoEvent(pagamento, ZonedDateTime.now(ZoneId.of(FUSO_HORARIO)), pagamentoCanceladoEventPublisher);
        } else {
            log.info("Cancelamento do pagamento falhou para pedido com id: {}", pagamento.getPedidoId().getValue());
            pagamento.atualizarStatus(PagamentoStatus.FALHO);
            return new PagamentoFalhoEvent(pagamento, ZonedDateTime.now(ZoneId.of(FUSO_HORARIO)), mensagensFalha, pagamentoFalhoEventDomainEventPublisher);
        }
    }

    private void validarCredito(Pagamento pagamento, Credito credito, List<String> mensagensFalha) {

        if(pagamento.getPreco().eMaiorQue(credito.getCreditoQuantiaTotal())) {
            log.error("Cliente com id: {} não possui crédito suficiente para pagamento.", pagamento.getClienteId());
            mensagensFalha.add("Cliente com id: " + pagamento.getClienteId() + " não possui crédito suficiente para pagamento.");
        }
    }

    private void subrairCredito(Pagamento pagamento, Credito credito) {

        credito.subtrairCredito(pagamento.getPreco());
    }

    private void atualizarCreditoHistorico(Pagamento pagamento, List<CreditoHistorico> creditoHistoricos, TipoTransacao tipoTransacao) {

        creditoHistoricos.add(CreditoHistorico.builder()
                .id(new CreditoHistoricoId(UUID.randomUUID()))
                .clienteId(pagamento.getClienteId())
                .quantia(pagamento.getPreco())
                .tipoTransacao(tipoTransacao)
                .build());
    }

    private void validarCreditoHistorico(Credito credito, List<CreditoHistorico> creditoHistoricos, List<String> mensagensFalha) {

        Dinheiro historicoCredito = obterCreditoTotal(creditoHistoricos, TipoTransacao.CREDITO);
        Dinheiro historicoDebito = obterCreditoTotal(creditoHistoricos, TipoTransacao.DEBITO);

        if(historicoDebito.eMaiorQue(historicoCredito)) {
            log.error("Cliente com id: {} não possui credito suficiente, de acordo com seu histórico de crédito.", credito.getClienteId().getValue());
            mensagensFalha.add("Cliente com id: " + credito.getClienteId().getValue() + " não possui credito suficiente, de acordo com seu histórico de crédito.");
        }

        if(!credito.getCreditoQuantiaTotal().equals(historicoCredito.subtrair(historicoDebito))) {
            log.error("Total do histórico de crédito é diferente do crédito atual do cliente com id: {}.", credito.getClienteId().getValue());
            mensagensFalha.add("Total do histórico de crédito é diferente do crédito atual do cliente com id: " + credito.getClienteId().getValue() + ".");
        }
    }

    private static Dinheiro obterCreditoTotal(List<CreditoHistorico> creditoHistoricos, TipoTransacao tipoTransacao) {
        return creditoHistoricos.stream()
                .filter(creditoHistorico -> creditoHistorico.getTipoTransacao().equals(tipoTransacao))
                .map(CreditoHistorico::getQuantia)
                .reduce(Dinheiro.ZERO, Dinheiro::somar);
    }

    private void somarCredito(Pagamento pagamento, Credito credito) {

        credito.adicionarCredito(pagamento.getPreco());
    }
}
