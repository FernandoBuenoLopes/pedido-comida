package org.vortxyz.pedido.service.dataaccess.pedido.adapter;

import org.springframework.stereotype.Component;
import org.vortxyz.domain.valueobject.PedidoId;
import org.vortxyz.pedido.domain.entity.Pedido;
import org.vortxyz.pedido.domain.ports.output.repository.PedidoRepository;
import org.vortxyz.pedido.domain.valueobject.RastreamentoId;
import org.vortxyz.pedido.service.dataaccess.pedido.mapper.PedidoDataAccessMapper;
import org.vortxyz.pedido.service.dataaccess.pedido.repository.PedidoJpaRepository;

import java.util.Optional;
import java.util.UUID;

@Component
public class PedidoRepositoryImpl implements PedidoRepository {

    private final PedidoJpaRepository pedidoJpaRepository;
    private final PedidoDataAccessMapper pedidoDataAccessMapper;

    public PedidoRepositoryImpl(PedidoJpaRepository pedidoJpaRepository, PedidoDataAccessMapper pedidoDataAccessMapper) {
        this.pedidoJpaRepository = pedidoJpaRepository;
        this.pedidoDataAccessMapper = pedidoDataAccessMapper;
    }

    @Override
    public Pedido save(Pedido pedido) {
        return pedidoDataAccessMapper.pedidoEntityToPedido(pedidoJpaRepository.save(pedidoDataAccessMapper.pedidoToPedidoEntity(pedido)));
    }

    @Override
    public Optional<Pedido> findById(PedidoId id) {
        return pedidoJpaRepository.findById(id.getValue()).map(pedidoDataAccessMapper::pedidoEntityToPedido);
    }

    @Override
    public Optional<Pedido> findByRastreamentoId(RastreamentoId rastreamentoId) {
        return pedidoJpaRepository.findByRastreamentoId(rastreamentoId.getValue()).map(pedidoDataAccessMapper::pedidoEntityToPedido);
    }
}
