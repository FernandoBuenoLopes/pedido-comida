package org.vortxyz.pagamento.service.dataaccess.credito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vortxyz.pagamento.service.dataaccess.credito.entity.CreditoEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CreditoJpaRepository extends JpaRepository<CreditoEntity, UUID> {

    Optional<CreditoEntity> findByClienteId(UUID clienteId);
}
