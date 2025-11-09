package org.vortxyz.restaurante.service.domain.ports.output.repository;

import org.vortxyz.restaurante.domain.entity.PedidoAprovacao;

public interface PedidoAprovacaoRepository {

    PedidoAprovacao save(PedidoAprovacao pedidoAprovacao);
}
