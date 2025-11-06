package org.vortxyz.pagamento.service.domain.event;

import org.vortxyz.domain.event.publisher.DomainEventPublisher;
import org.vortxyz.pagamento.service.domain.entity.Pagamento;

import java.time.ZonedDateTime;
import java.util.List;

public class PagamentoFalhoEvent extends PagamentoEvent {

    private final DomainEventPublisher<PagamentoFalhoEvent> pagamentoFalhoEventDomainEventPublisher;

    public PagamentoFalhoEvent(Pagamento pagamento, ZonedDateTime criadoEm, List<String> mensagensFalha, DomainEventPublisher<PagamentoFalhoEvent> pagamentoFalhoEventDomainEventPublisher) {
        super(pagamento, criadoEm, mensagensFalha);
        this.pagamentoFalhoEventDomainEventPublisher = pagamentoFalhoEventDomainEventPublisher;
    }

    @Override
    public void disparar() {

    }
}
