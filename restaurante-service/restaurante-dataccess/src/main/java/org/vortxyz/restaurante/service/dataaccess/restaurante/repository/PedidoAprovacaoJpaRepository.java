package org.vortxyz.restaurante.service.dataaccess.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vortxyz.restaurante.service.dataaccess.restaurante.entity.PedidoAprovacaoEntity;

import java.util.UUID;

@Repository
public interface PedidoAprovacaoJpaRepository extends JpaRepository<PedidoAprovacaoEntity, UUID> {

}
