package org.vortxyz.pedido.service.dataaccess.pedido.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vortxyz.pedido.service.dataaccess.pedido.entity.PedidoEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PedidoJpaRepository extends JpaRepository<PedidoEntity, UUID> {

    Optional<PedidoEntity> findByRastreamentoId(UUID rastreamentoId);
}
