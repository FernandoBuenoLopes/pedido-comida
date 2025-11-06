package org.vortxyz.pagamento.service.domain;

import org.vortxyz.domain.event.publisher.DomainEventPublisher;
import org.vortxyz.pagamento.service.domain.entity.Credito;
import org.vortxyz.pagamento.service.domain.entity.CreditoHistorico;
import org.vortxyz.pagamento.service.domain.entity.Pagamento;
import org.vortxyz.pagamento.service.domain.event.PagamentoCanceladoEvent;
import org.vortxyz.pagamento.service.domain.event.PagamentoCompletoEvent;
import org.vortxyz.pagamento.service.domain.event.PagamentoEvent;
import org.vortxyz.pagamento.service.domain.event.PagamentoFalhoEvent;

import java.util.List;

public interface PagamentoDomainService {

    PagamentoEvent validarEIniciarPagamento(Pagamento pagamento, Credito credito, List<CreditoHistorico> creditoHistoricos, List<String> mensagensFalha, DomainEventPublisher<PagamentoCompletoEvent> pagamentoCompletoEventDomainEventPublisher, DomainEventPublisher<PagamentoFalhoEvent> pagamentoFalhoEventDomainEventPublisher);

    PagamentoEvent validarECancelarPagamento(Pagamento pagamento, Credito credito, List<CreditoHistorico> creditoHistoricos, List<String> mensagensFalha, DomainEventPublisher<PagamentoCanceladoEvent> pagamentoCanceladoEventPublisher, DomainEventPublisher<PagamentoFalhoEvent> pagamentoFalhoEventDomainEventPublisher);
}
