package org.vortxyz.pagamento.service.domain.ports.output.repository;

import org.vortxyz.pagamento.service.domain.entity.Pagamento;

import java.util.Optional;
import java.util.UUID;

public interface PagamentoRepository {

    Pagamento save(Pagamento pagamento);

    Optional<Pagamento> findByPedidoId(UUID pedidoId);
}
