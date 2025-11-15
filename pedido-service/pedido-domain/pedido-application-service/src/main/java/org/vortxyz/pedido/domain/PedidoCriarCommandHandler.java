package org.vortxyz.pedido.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.vortxyz.pedido.domain.dto.create.PedidoCriarCommand;
import org.vortxyz.pedido.domain.dto.create.PedidoCriarResponse;
import org.vortxyz.pedido.domain.event.PedidoCriadoEvent;
import org.vortxyz.pedido.domain.mapper.PedidoMapper;
import org.vortxyz.pedido.domain.ports.output.message.publisher.pagamento.PedidoCriadoPagamentoRequestgMessagePublisher;

@Slf4j
@Component
public class PedidoCriarCommandHandler {

    private final PedidoCriarHelper pedidoCriarHelper;
    private final PedidoMapper pedidoMapper;
    private final PedidoCriadoPagamentoRequestgMessagePublisher pedidoCriadoPagamentoRequestgMessagePublisher;

    public PedidoCriarCommandHandler(PedidoCriarHelper pedidoCriarHelper,
                                     PedidoMapper pedidoMapper,
                                     PedidoCriadoPagamentoRequestgMessagePublisher pedidoCriadoPagamentoRequestgMessagePublisher) {
        this.pedidoCriarHelper = pedidoCriarHelper;
        this.pedidoMapper = pedidoMapper;
        this.pedidoCriadoPagamentoRequestgMessagePublisher = pedidoCriadoPagamentoRequestgMessagePublisher;
    }

    public PedidoCriarResponse pedidoCriar(PedidoCriarCommand pedidoCriarCommand){
        PedidoCriadoEvent pedidoCriadoEvent = pedidoCriarHelper.persistirPedido(pedidoCriarCommand);
        pedidoCriadoPagamentoRequestgMessagePublisher.publish(pedidoCriadoEvent);
        return pedidoMapper.pedidoToPedidoCriarResponse(pedidoCriadoEvent.getPedido(), "Pedido criado com sucesso.");
    }
}
