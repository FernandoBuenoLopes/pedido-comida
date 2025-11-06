package org.vortxyz.pagamento.service.domain.valueobject;

import org.vortxyz.domain.valueobject.BaseId;

import java.util.UUID;

public class CreditoId extends BaseId<UUID> {

    public CreditoId(UUID value) {
        super(value);
    }
}
