package org.vortxyz.pedido.domain.entity;

import org.vortxyz.domain.entity.AggregateRoot;
import org.vortxyz.domain.valueobject.ClienteId;

public class Cliente extends AggregateRoot<ClienteId> {
    public Cliente() {;
    }

    public Cliente(ClienteId clienteId) {
        super.setId(clienteId);
    }
}
