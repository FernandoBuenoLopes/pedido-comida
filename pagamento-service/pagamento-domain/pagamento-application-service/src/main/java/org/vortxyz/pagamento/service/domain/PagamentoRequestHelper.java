package org.vortxyz.pagamento.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.vortxyz.domain.valueobject.ClienteId;
import org.vortxyz.pagamento.service.domain.dto.PagamentoRequest;
import org.vortxyz.pagamento.service.domain.entity.Credito;
import org.vortxyz.pagamento.service.domain.entity.CreditoHistorico;
import org.vortxyz.pagamento.service.domain.entity.Pagamento;
import org.vortxyz.pagamento.service.domain.event.PagamentoEvent;
import org.vortxyz.pagamento.service.domain.exception.PagamentoApplicationServiceException;
import org.vortxyz.pagamento.service.domain.mapper.PagamentoDataMapper;
import org.vortxyz.pagamento.service.domain.ports.output.message.publisher.PagamentoCanceladoMessagePublisher;
import org.vortxyz.pagamento.service.domain.ports.output.message.publisher.PagamentoCompletoMessagePublisher;
import org.vortxyz.pagamento.service.domain.ports.output.message.publisher.PagamentoFalhoMessagePublisher;
import org.vortxyz.pagamento.service.domain.ports.output.repository.CreditoHistoricoRepository;
import org.vortxyz.pagamento.service.domain.ports.output.repository.CreditoRepository;
import org.vortxyz.pagamento.service.domain.ports.output.repository.PagamentoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class PagamentoRequestHelper {

    private final PagamentoDomainService pagamentoDomainService;
    private final PagamentoDataMapper pagamentoDataMapper;
    private final PagamentoRepository pagamentoRepository;
    private final CreditoRepository creditoRepository;
    private final CreditoHistoricoRepository creditoHistoricoRepository;
    private final PagamentoCompletoMessagePublisher pagamentoCompletoMessagePublisher;
    private final PagamentoCanceladoMessagePublisher pagamentoCanceladoEventPublisher;
    private final PagamentoFalhoMessagePublisher pagamentoFalhoMessagePublisher;

    public PagamentoRequestHelper(PagamentoDomainService pagamentoDomainService, PagamentoDataMapper pagamentoDataMapper, PagamentoRepository pagamentoRepository, CreditoRepository creditoRepository, CreditoHistoricoRepository creditoHistoricoRepository, PagamentoCompletoMessagePublisher pagamentoCompletoMessagePublisher, PagamentoCanceladoMessagePublisher pagamentoCanceladoEventPublisher, PagamentoFalhoMessagePublisher pagamentoFalhoMessagePublisher) {
        this.pagamentoDomainService = pagamentoDomainService;
        this.pagamentoDataMapper = pagamentoDataMapper;
        this.pagamentoRepository = pagamentoRepository;
        this.creditoRepository = creditoRepository;
        this.creditoHistoricoRepository = creditoHistoricoRepository;
        this.pagamentoCompletoMessagePublisher = pagamentoCompletoMessagePublisher;
        this.pagamentoCanceladoEventPublisher = pagamentoCanceladoEventPublisher;
        this.pagamentoFalhoMessagePublisher = pagamentoFalhoMessagePublisher;
    }

    //TODO Otimizar os dois métodos transacionais extraindo código comum.
    @Transactional
    public PagamentoEvent persistirPagamento(PagamentoRequest pagamentoRequest) {

        log.info("Recebido evento de pagamento completo para pedido com id: {}.", pagamentoRequest.getPedidoId());
        Pagamento pagamento = pagamentoDataMapper.pagamentoRequestToPagamento(pagamentoRequest);
        Credito credito = obterCredito(pagamento.getClienteId());
        List<CreditoHistorico> creditoHistoricos = listarCreditoHistoricos(pagamento.getClienteId());
        List<String> mensagensFalha = new ArrayList<>();
        PagamentoEvent pagamentoEvent = pagamentoDomainService.validarEIniciarPagamento(pagamento, credito, creditoHistoricos, mensagensFalha, pagamentoCompletoMessagePublisher, pagamentoFalhoMessagePublisher);
        persistirObjetosDb(pagamento, mensagensFalha, credito, creditoHistoricos);
        return pagamentoEvent;
    }

    @Transactional
    public PagamentoEvent persistirPagamentoCancelar(PagamentoRequest pagamentoRequest) {

        log.info("Recebido evento de cancelamento do pagamento para pedido com id: {}.", pagamentoRequest.getPedidoId());
        Optional<Pagamento> pagamentoResponse = pagamentoRepository.findByPedidoId(UUID.fromString(pagamentoRequest.getPedidoId()));
        if (pagamentoResponse.isEmpty()) {
            log.error("Pagamento do pedido com id: {} não encontrado.", pagamentoRequest.getPedidoId());
            throw new PagamentoApplicationServiceException("Pagamento do pedido com id: " + pagamentoRequest.getPedidoId() + " não encontrado.");
        }
        Pagamento pagamento = pagamentoResponse.get();
        Credito credito = obterCredito(pagamento.getClienteId());
        List<CreditoHistorico> creditoHistoricos = listarCreditoHistoricos(pagamento.getClienteId());
        List<String> mensagensFalha =  new ArrayList<>();
        PagamentoEvent pagamentoEvent = pagamentoDomainService.validarECancelarPagamento(pagamento, credito, creditoHistoricos, mensagensFalha, pagamentoCanceladoEventPublisher, pagamentoFalhoMessagePublisher);
        persistirObjetosDb(pagamento, mensagensFalha, credito, creditoHistoricos);
        return pagamentoEvent;
    }

    private Credito obterCredito(ClienteId clienteId) {

        Optional<Credito> credito = creditoRepository.findByClienteId(clienteId);
        if (credito.isEmpty()) {
            log.error("Crédito não encontrado para cliente com id: {}.", clienteId.getValue());
            throw new PagamentoApplicationServiceException("Crédito não encontrado para cliente com id: " + clienteId.getValue() + ".");
        }
        return credito.get();
    }

    private List<CreditoHistorico> listarCreditoHistoricos(ClienteId clienteId) {

        Optional<List<CreditoHistorico>> creditoHistoricos = creditoHistoricoRepository.findByClienteId(clienteId);
        if (creditoHistoricos.isEmpty()) {
            log.error("Histórico de crédito não encontrado para cliente com id: {}.", clienteId.getValue());
            throw new PagamentoApplicationServiceException("Histórico de crédito não encontrado para cliente com id: " + clienteId.getValue() + ".");
        }
        return creditoHistoricos.get();
    }

    private void persistirObjetosDb(Pagamento pagamento, List<String> mensagensFalha, Credito credito, List<CreditoHistorico> creditoHistoricos) {

        pagamentoRepository.save(pagamento);
        if (mensagensFalha.isEmpty()) {
            creditoRepository.save(credito);
            creditoHistoricoRepository.save(creditoHistoricos.getLast());
        }
    }
}
