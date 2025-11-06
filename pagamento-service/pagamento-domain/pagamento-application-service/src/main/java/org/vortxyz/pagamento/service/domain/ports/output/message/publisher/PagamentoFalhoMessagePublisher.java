package org.vortxyz.pagamento.service.domain.ports.output.message.publisher;

import org.vortxyz.domain.event.publisher.DomainEventPublisher;
import org.vortxyz.pagamento.service.domain.event.PagamentoFalhoEvent;

public interface PagamentoFalhoMessagePublisher extends DomainEventPublisher<PagamentoFalhoEvent> {
}
