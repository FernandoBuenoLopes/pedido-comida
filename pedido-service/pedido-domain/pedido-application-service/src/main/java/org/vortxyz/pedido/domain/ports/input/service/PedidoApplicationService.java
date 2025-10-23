package org.vortxyz.pedido.domain.ports.input.service;

import jakarta.validation.Valid;
import org.vortxyz.pedido.domain.dto.create.PedidoCriarCommand;
import org.vortxyz.pedido.domain.dto.create.PedidoCriarResponse;
import org.vortxyz.pedido.domain.dto.track.PedidoRastrearQuery;
import org.vortxyz.pedido.domain.dto.track.PedidoRastrearResponse;

public interface PedidoApplicationService {

    PedidoCriarResponse pedidoCriar(@Valid PedidoCriarCommand pedidoCriarCommand);

    PedidoRastrearResponse pedidoRastrear(@Valid PedidoRastrearQuery pedidoRastrearQuery);

}
