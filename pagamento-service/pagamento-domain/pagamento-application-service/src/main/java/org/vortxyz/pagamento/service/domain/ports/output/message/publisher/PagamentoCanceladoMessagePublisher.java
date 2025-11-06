package org.vortxyz.pagamento.service.domain.ports.output.message.publisher;

import org.vortxyz.domain.event.publisher.DomainEventPublisher;
import org.vortxyz.pagamento.service.domain.event.PagamentoCanceladoEvent;

public interface PagamentoCanceladoMessagePublisher extends DomainEventPublisher<PagamentoCanceladoEvent> {
}
