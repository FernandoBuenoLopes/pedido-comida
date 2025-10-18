package org.vortxyz.pedido.domain.valueobject;

import org.vortxyz.domain.valueobject.BaseId;

import java.util.UUID;

public class RastreamentoId extends BaseId<UUID> {
    public RastreamentoId(UUID value) {
        super(value);
    }
}
