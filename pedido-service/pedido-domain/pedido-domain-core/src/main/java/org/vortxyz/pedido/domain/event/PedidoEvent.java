package org.vortxyz.pedido.domain.event;

import org.vortxyz.domain.event.DomainEvent;
import org.vortxyz.pedido.domain.entity.Pedido;

import java.time.ZonedDateTime;

public abstract class PedidoEvent implements DomainEvent<Pedido> {

    private final Pedido pedido;
    private final ZonedDateTime criadoEm;

    public PedidoEvent(Pedido pedido, ZonedDateTime criadoEm) {
        this.pedido = pedido;
        this.criadoEm = criadoEm;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public ZonedDateTime getCriadoEm() {
        return criadoEm;
    }
}
