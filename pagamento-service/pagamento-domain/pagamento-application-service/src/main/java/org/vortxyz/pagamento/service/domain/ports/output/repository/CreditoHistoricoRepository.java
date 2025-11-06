package org.vortxyz.pagamento.service.domain.ports.output.repository;

import org.vortxyz.domain.valueobject.ClienteId;
import org.vortxyz.pagamento.service.domain.entity.CreditoHistorico;

import java.util.List;
import java.util.Optional;

public interface CreditoHistoricoRepository {

    CreditoHistorico save(CreditoHistorico creditoHistorico);

    Optional<List<CreditoHistorico>> findByClienteId(ClienteId clienteId);
}
