package org.vortxyz.pedido.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.vortxyz.pedido.domain.dto.create.PedidoCriarCommand;
import org.vortxyz.pedido.domain.dto.create.PedidoCriarResponse;
import org.vortxyz.pedido.domain.dto.track.PedidoRastrearQuery;
import org.vortxyz.pedido.domain.dto.track.PedidoRastrearResponse;
import org.vortxyz.pedido.domain.ports.input.service.PedidoApplicationService;

@Slf4j
@Validated
@Service
class PedidoApplicationServiceImpl implements PedidoApplicationService {

    private final PedidoCriarCommandHandler pedidoCriarCommandHandler;
    private final PedidoRastrearCommandHandler pedidoRastrearCommandHandler;

    PedidoApplicationServiceImpl(PedidoCriarCommandHandler pedidoCriarCommandHandler, PedidoRastrearCommandHandler pedidoRastrearCommandHandler) {
        this.pedidoCriarCommandHandler = pedidoCriarCommandHandler;
        this.pedidoRastrearCommandHandler = pedidoRastrearCommandHandler;
    }

    @Override
    public PedidoCriarResponse pedidoCriar(PedidoCriarCommand pedidoCriarCommand) {
        return pedidoCriarCommandHandler.pedidoCriar(pedidoCriarCommand);
    }

    @Override
    public PedidoRastrearResponse pedidoRastrear(PedidoRastrearQuery pedidoRastrearQuery) {
        return pedidoRastrearCommandHandler.pedidoRastrear(pedidoRastrearQuery);
    }
}
