package org.vortxyz.pagamento.service.dataaccess.credito.adapter;

import org.springframework.stereotype.Component;
import org.vortxyz.domain.valueobject.ClienteId;
import org.vortxyz.pagamento.service.dataaccess.credito.mapper.CreditoDataAccessMapper;
import org.vortxyz.pagamento.service.dataaccess.credito.repository.CreditoJpaRepository;
import org.vortxyz.pagamento.service.domain.entity.Credito;
import org.vortxyz.pagamento.service.domain.ports.output.repository.CreditoRepository;

import java.util.Optional;

@Component
public class CreditoRepositoryImpl implements CreditoRepository {

    private final CreditoJpaRepository creditoJpaRepository;
    private final CreditoDataAccessMapper creditoDataAccessMapper;

    public CreditoRepositoryImpl(CreditoJpaRepository creditoJpaRepository, CreditoDataAccessMapper creditoDataAccessMapper) {
        this.creditoJpaRepository = creditoJpaRepository;
        this.creditoDataAccessMapper = creditoDataAccessMapper;
    }

    @Override
    public Credito save(Credito credito) {

        return creditoDataAccessMapper.creditoEntityToCredito(creditoJpaRepository.save(creditoDataAccessMapper.creditoToCreditoEntity(credito)));
    }

    @Override
    public Optional<Credito> findByClienteId(ClienteId clienteId) {

        return creditoJpaRepository.findByClienteId(clienteId.getValue()).map(creditoDataAccessMapper::creditoEntityToCredito);
    }
}
