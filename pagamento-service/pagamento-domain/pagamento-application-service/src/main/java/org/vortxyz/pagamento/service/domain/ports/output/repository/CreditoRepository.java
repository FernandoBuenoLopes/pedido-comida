package org.vortxyz.pagamento.service.domain.ports.output.repository;

import org.vortxyz.domain.valueobject.ClienteId;
import org.vortxyz.pagamento.service.domain.entity.Credito;

import java.util.Optional;

public interface CreditoRepository {

    Credito save(Credito credito);

    Optional<Credito> findByClienteId(ClienteId clienteId);
}
