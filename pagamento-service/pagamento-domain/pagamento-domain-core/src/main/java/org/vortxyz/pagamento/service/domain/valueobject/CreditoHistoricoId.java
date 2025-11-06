package org.vortxyz.pagamento.service.domain.valueobject;

import org.vortxyz.domain.valueobject.BaseId;

import java.util.UUID;

public class CreditoHistoricoId extends BaseId<UUID> {

    public CreditoHistoricoId(UUID value) {
        super(value);
    }
}
