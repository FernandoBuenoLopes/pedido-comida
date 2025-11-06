package org.vortxyz.pedido.domain.event;

import org.vortxyz.domain.event.publisher.DomainEventPublisher;
import org.vortxyz.pedido.domain.entity.Pedido;

import java.time.ZonedDateTime;

public class PedidoPagoEvent extends PedidoEvent {

    private final DomainEventPublisher<PedidoPagoEvent> pedidoPagoEventDomainEventPublisher;

    public PedidoPagoEvent(Pedido pedido, ZonedDateTime criadoEm, DomainEventPublisher<PedidoPagoEvent> pedidoPagoEventDomainEventPublisher) {
        super(pedido, criadoEm);
        this.pedidoPagoEventDomainEventPublisher = pedidoPagoEventDomainEventPublisher;
    }

    @Override
    public void disparar() {
        pedidoPagoEventDomainEventPublisher.publish(this);
    }
}