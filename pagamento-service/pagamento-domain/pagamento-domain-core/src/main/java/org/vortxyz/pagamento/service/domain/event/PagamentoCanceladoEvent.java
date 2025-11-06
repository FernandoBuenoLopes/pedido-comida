package org.vortxyz.pagamento.service.domain.event;

import org.vortxyz.domain.event.publisher.DomainEventPublisher;
import org.vortxyz.pagamento.service.domain.entity.Pagamento;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

public class PagamentoCanceladoEvent extends PagamentoEvent{

    private final DomainEventPublisher<PagamentoCanceladoEvent> pagamentoCanceladoEventDomainEventPublisher;

    public PagamentoCanceladoEvent(Pagamento pagamento, ZonedDateTime criadoEm, DomainEventPublisher<PagamentoCanceladoEvent> pagamentoCanceladoEventDomainEventPublisher) {
        super(pagamento, criadoEm, Collections.emptyList());
        this.pagamentoCanceladoEventDomainEventPublisher = pagamentoCanceladoEventDomainEventPublisher;
    }

    @Override
    public void disparar() {
        pagamentoCanceladoEventDomainEventPublisher.publish(this);
    }
}
