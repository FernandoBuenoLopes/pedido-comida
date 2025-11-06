package org.vortxyz.pedido.domain.event;

import org.vortxyz.domain.event.publisher.DomainEventPublisher;
import org.vortxyz.pedido.domain.entity.Pedido;

import java.time.ZonedDateTime;

public class PedidoCriadoEvent extends PedidoEvent {

    private final DomainEventPublisher<PedidoCriadoEvent> pedidoCriadoEventDomainEventPublisher;

    public PedidoCriadoEvent(Pedido pedido, ZonedDateTime criadoEm, DomainEventPublisher<PedidoCriadoEvent> pedidoCriadoEventDomainEventPublisher) {
        super(pedido, criadoEm);
        this.pedidoCriadoEventDomainEventPublisher = pedidoCriadoEventDomainEventPublisher;
    }

    @Override
    public void disparar() {
        pedidoCriadoEventDomainEventPublisher.publish(this);
    }
}
