package org.vortxyz.pedido.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.vortxyz.domain.valueobject.PedidoId;
import org.vortxyz.pedido.domain.entity.Pedido;
import org.vortxyz.pedido.domain.exception.PedidoNaoEncontradoException;
import org.vortxyz.pedido.domain.ports.output.repository.PedidoRepository;

import java.util.UUID;

@Slf4j
@Component
public class PedidoSagaHelper {

    private final PedidoRepository pedidoRepository;

    public PedidoSagaHelper(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    Pedido findPedido(String pedidoId) {
        return pedidoRepository.findById(new PedidoId(UUID.fromString(pedidoId))).orElseThrow(() -> new PedidoNaoEncontradoException("Pedido não encontrado"));
    }

    void savePedido(Pedido pedido) {
        pedidoRepository.save(pedido);
    }
}
