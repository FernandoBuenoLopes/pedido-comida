package org.vortxyz.pedido.domain;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.vortxyz.pedido.domain.dto.create.PedidoCriarCommand;
import org.vortxyz.pedido.domain.dto.create.PedidoCriarResponse;
import org.vortxyz.pedido.domain.entity.Cliente;
import org.vortxyz.pedido.domain.entity.Pedido;
import org.vortxyz.pedido.domain.entity.Restaurante;
import org.vortxyz.pedido.domain.event.PedidoCriadoEvent;
import org.vortxyz.pedido.domain.exception.PedidoDomainException;
import org.vortxyz.pedido.domain.mapper.PedidoMapper;
import org.vortxyz.pedido.domain.ports.output.message.publisher.pagamento.PedidoCriadoPagamentoRequestgMessagePublisher;
import org.vortxyz.pedido.domain.ports.output.repository.ClienteRepository;
import org.vortxyz.pedido.domain.ports.output.repository.PedidoRepository;
import org.vortxyz.pedido.domain.ports.output.repository.RestauranteRepository;

import java.util.Optional;
import java.util.UUID;

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
