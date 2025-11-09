package org.vortxyz.restaurante.domain.valueobject;

import org.vortxyz.domain.valueobject.BaseId;

import java.util.UUID;

public class PedidoAprovacaoId extends BaseId<UUID> {

    public PedidoAprovacaoId(UUID value) {
        super(value);
    }
}
