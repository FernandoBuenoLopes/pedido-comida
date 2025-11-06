package org.vortxyz.pagamento.service.dataaccess.creditohistorico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vortxyz.pagamento.service.dataaccess.creditohistorico.entity.CreditoHistoricoEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CreditoHistoricoJpaRepository extends JpaRepository<CreditoHistoricoEntity, UUID> {

    Optional<List<CreditoHistoricoEntity>> findByClienteId(UUID clienteId);
}
