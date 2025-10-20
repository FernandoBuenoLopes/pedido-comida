package org.vortxyz.pedido.domain.event;

import org.vortxyz.domain.event.DomainEvent;
import org.vortxyz.pedido.domain.entity.Pedido;

import java.time.ZonedDateTime;

public class PedidoCriadoEvent extends PedidoEvent {

    public PedidoCriadoEvent(Pedido pedido, ZonedDateTime criadoEm) {
        super(pedido, criadoEm);
    }
}
