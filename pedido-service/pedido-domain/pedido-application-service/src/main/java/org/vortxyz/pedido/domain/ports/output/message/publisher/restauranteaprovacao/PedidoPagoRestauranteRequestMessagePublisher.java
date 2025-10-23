package org.vortxyz.pedido.domain.ports.output.message.publisher.restauranteaprovacao;

import org.vortxyz.domain.event.publisher.DomainEventPublisher;
import org.vortxyz.pedido.domain.event.PedidoPagoEvent;

public interface PedidoPagoRestauranteRequestMessagePublisher extends DomainEventPublisher<PedidoPagoEvent> {
}
