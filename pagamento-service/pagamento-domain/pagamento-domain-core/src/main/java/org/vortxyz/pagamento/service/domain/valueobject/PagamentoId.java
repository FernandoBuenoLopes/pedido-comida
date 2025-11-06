package org.vortxyz.pagamento.service.domain.valueobject;

import org.vortxyz.domain.valueobject.BaseId;
import org.vortxyz.domain.valueobject.PedidoId;

import java.util.UUID;

public class PagamentoId extends BaseId<UUID> {

    public PagamentoId(UUID value) {
        super(value);
    }
}
