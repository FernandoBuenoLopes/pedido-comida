package org.vortxyz.restaurante.service.domain.ports.output.message.publisher;

import org.vortxyz.domain.event.publisher.DomainEventPublisher;
import org.vortxyz.restaurante.domain.event.PedidoRejeitadoEvent;

public interface PedidoRejeitadoMessagePublisher extends DomainEventPublisher<PedidoRejeitadoEvent> {
}
