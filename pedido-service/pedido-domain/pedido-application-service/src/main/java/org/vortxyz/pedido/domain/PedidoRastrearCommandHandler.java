package org.vortxyz.pedido.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.vortxyz.pedido.domain.dto.track.PedidoRastrearQuery;
import org.vortxyz.pedido.domain.dto.track.PedidoRastrearResponse;
import org.vortxyz.pedido.domain.entity.Pedido;
import org.vortxyz.pedido.domain.exception.PedidoNaoEncontradoException;
import org.vortxyz.pedido.domain.mapper.PedidoMapper;
import org.vortxyz.pedido.domain.ports.output.repository.PedidoRepository;
import org.vortxyz.pedido.domain.valueobject.RastreamentoId;

import java.util.Optional;

@Slf4j
@Component
public class PedidoRastrearCommandHandler {

    private final PedidoMapper pedidoMapper;
    private final PedidoRepository pedidoRepository;

    public PedidoRastrearCommandHandler(PedidoMapper pedidoMapper, PedidoRepository pedidoRepository) {
        this.pedidoMapper = pedidoMapper;
        this.pedidoRepository = pedidoRepository;
    }

    @Transactional(readOnly = true)
    public PedidoRastrearResponse pedidoRastrear(PedidoRastrearQuery pedidoRastrearQuery){
        Optional<Pedido> pedidoResult = pedidoRepository.encontrarPorRastreamentoId(new RastreamentoId(pedidoRastrearQuery.getPedidoRastreamentoId()));
        if (pedidoResult.isEmpty()) {
            log.warn("Pedido com id: {} não encontrado.", pedidoRastrearQuery.getPedidoRastreamentoId());
            throw new PedidoNaoEncontradoException("Pedido com id: " + pedidoRastrearQuery.getPedidoRastreamentoId() + " não encontrado.");
        }
        return pedidoMapper.pedidoToPedidoRastrearResponse(pedidoResult.get());
    }
}
