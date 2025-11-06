package org.vortxyz.pagamento.service.dataaccess.pagamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vortxyz.pagamento.service.dataaccess.pagamento.entity.PagamentoEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PagamentoJpaRepository extends JpaRepository<PagamentoEntity, UUID> {

    Optional<PagamentoEntity> findByPedidoId(UUID pedidoId);
}
