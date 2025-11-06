package org.vortxyz.pagamento.service.dataaccess.creditohistorico.adapter;

import org.springframework.stereotype.Component;
import org.vortxyz.domain.valueobject.ClienteId;
import org.vortxyz.pagamento.service.dataaccess.creditohistorico.mapper.CreditoHistoricoDataMapper;
import org.vortxyz.pagamento.service.dataaccess.creditohistorico.repository.CreditoHistoricoJpaRepository;
import org.vortxyz.pagamento.service.domain.entity.CreditoHistorico;
import org.vortxyz.pagamento.service.domain.ports.output.repository.CreditoHistoricoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CreditoHistoricoRepositoryImpl implements CreditoHistoricoRepository {

    private final CreditoHistoricoJpaRepository creditoHistoricoJpaRepository;
    private final CreditoHistoricoDataMapper creditoHistoricoDataMapper;

    public CreditoHistoricoRepositoryImpl(CreditoHistoricoJpaRepository creditoHistoricoJpaRepository, CreditoHistoricoDataMapper creditoHistoricoDataMapper) {
        this.creditoHistoricoJpaRepository = creditoHistoricoJpaRepository;
        this.creditoHistoricoDataMapper = creditoHistoricoDataMapper;
    }

    @Override
    public CreditoHistorico save(CreditoHistorico creditoHistorico) {
        return creditoHistoricoDataMapper.creditoHistoricoEntityToCreditoHistorico(creditoHistoricoJpaRepository.save(creditoHistoricoDataMapper.CreditoHistoricoToCreditoHistoricoEntity(creditoHistorico)));
    }

    @Override
    public Optional<List<CreditoHistorico>> findByClienteId(ClienteId clienteId) {
        return creditoHistoricoJpaRepository.findByClienteId(clienteId.getValue()).map(creditoHistoricoEntities ->
                creditoHistoricoEntities.stream().map(creditoHistoricoDataMapper::creditoHistoricoEntityToCreditoHistorico).collect(Collectors.toList()));

    }
}
