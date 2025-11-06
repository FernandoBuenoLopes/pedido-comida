package org.vortxyz.pagamento.service.domain.event;

import org.vortxyz.domain.event.publisher.DomainEventPublisher;
import org.vortxyz.pagamento.service.domain.entity.Pagamento;

import java.time.ZonedDateTime;
import java.util.Collections;

public class PagamentoCompletoEvent extends PagamentoEvent {

    private final DomainEventPublisher<PagamentoCompletoEvent> pagamentoCompletoEventDomainEventPublisher;

    public PagamentoCompletoEvent(Pagamento pagamento, ZonedDateTime criadoEm, DomainEventPublisher<PagamentoCompletoEvent> pagamentoCompletoEventDomainEventPublisher) {
        super(pagamento, criadoEm, Collections.emptyList());
        this.pagamentoCompletoEventDomainEventPublisher = pagamentoCompletoEventDomainEventPublisher;
    }

    @Override
    public void disparar() {
        pagamentoCompletoEventDomainEventPublisher.publish(this);
    }
}
