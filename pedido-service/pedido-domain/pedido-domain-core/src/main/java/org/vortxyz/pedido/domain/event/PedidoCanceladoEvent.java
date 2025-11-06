package org.vortxyz.pedido.domain.event;

import org.vortxyz.domain.event.publisher.DomainEventPublisher;
import org.vortxyz.pedido.domain.entity.Pedido;

import java.time.ZonedDateTime;

public class PedidoCanceladoEvent extends PedidoEvent {

    private final DomainEventPublisher<PedidoCanceladoEvent> pedidoCanceladoEventDomainEventPublisher;

    public PedidoCanceladoEvent(Pedido pedido, ZonedDateTime criadoEm, DomainEventPublisher<PedidoCanceladoEvent> pedidoCanceladoEventDomainEventPublisher) {
        super(pedido, criadoEm);
        this.pedidoCanceladoEventDomainEventPublisher = pedidoCanceladoEventDomainEventPublisher;
    }

    @Override
    public void disparar() {
        pedidoCanceladoEventDomainEventPublisher.publish(this);
    }
}
