package org.vortxyz.pedido.domain.event;

import org.vortxyz.domain.event.DomainEvent;
import org.vortxyz.pedido.domain.entity.Pedido;

import java.time.ZonedDateTime;

public class PedidoPagoEvent extends PedidoEvent {

    public PedidoPagoEvent(Pedido pedido, ZonedDateTime criadoEm) {
        super(pedido, criadoEm);
    }
}