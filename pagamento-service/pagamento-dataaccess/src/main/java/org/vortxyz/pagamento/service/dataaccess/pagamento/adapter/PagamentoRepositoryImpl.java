package org.vortxyz.pagamento.service.dataaccess.pagamento.adapter;

import org.springframework.stereotype.Component;
import org.vortxyz.pagamento.service.dataaccess.pagamento.mapper.PagamentoDataAccessMapper;
import org.vortxyz.pagamento.service.dataaccess.pagamento.repository.PagamentoJpaRepository;
import org.vortxyz.pagamento.service.domain.entity.Pagamento;
import org.vortxyz.pagamento.service.domain.ports.output.repository.PagamentoRepository;

import java.util.Optional;
import java.util.UUID;

@Component
public class PagamentoRepositoryImpl implements PagamentoRepository {

    private final PagamentoJpaRepository pagamentoJpaRepository;
    private final PagamentoDataAccessMapper pagamentoDataAccessMapper;

    public PagamentoRepositoryImpl(PagamentoJpaRepository pagamentoJpaRepository, PagamentoDataAccessMapper pagamentoDataAccessMapper) {
        this.pagamentoJpaRepository = pagamentoJpaRepository;
        this.pagamentoDataAccessMapper = pagamentoDataAccessMapper;
    }

    @Override
    public Pagamento save(Pagamento pagamento) {

        return pagamentoDataAccessMapper.pagamentoEntityToPagamento(pagamentoJpaRepository.save(pagamentoDataAccessMapper.pagamentoToPagamentoEntity(pagamento)));
    }

    @Override
    public Optional<Pagamento> findByPedidoId(UUID pedidoId) {

        return pagamentoJpaRepository.findByPedidoId(pedidoId).map(pagamentoDataAccessMapper::pagamentoEntityToPagamento);
    }
}
