package org.vortxyz.pedido.domain.ports.output.repository;

import org.vortxyz.pedido.domain.entity.Pedido;
import org.vortxyz.pedido.domain.valueobject.RastreamentoId;

import java.util.Optional;

public interface PedidoRepository {

    Pedido salvar(Pedido pedido);

    Optional<Pedido> encontrarPorRastreamentoId(RastreamentoId rastreamentoId);
}
