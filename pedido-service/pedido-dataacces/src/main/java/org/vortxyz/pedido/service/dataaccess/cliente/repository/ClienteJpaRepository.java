package org.vortxyz.pedido.service.dataaccess.cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vortxyz.pedido.service.dataaccess.cliente.entity.ClienteEntity;

import java.util.UUID;

@Repository
public interface ClienteJpaRepository extends JpaRepository<ClienteEntity, UUID> {
}
