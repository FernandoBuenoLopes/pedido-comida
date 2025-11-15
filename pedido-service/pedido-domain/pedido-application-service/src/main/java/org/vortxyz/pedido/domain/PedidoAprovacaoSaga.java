package org.vortxyz.pedido.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.vortxyz.domain.event.EmptyEvent;
import org.vortxyz.pedido.domain.dto.message.RestauranteAprovacaoResponse;
import org.vortxyz.pedido.domain.entity.Pedido;
import org.vortxyz.pedido.domain.event.PedidoCanceladoEvent;
import org.vortxyz.pedido.domain.ports.output.message.publisher.pagamento.PedidoCanceladoPagamentoRequestMessagePublisher;
import org.vortxyz.saga.SagaStep;

import java.util.UUID;

@Slf4j
@Component
public class PedidoAprovacaoSaga implements SagaStep<RestauranteAprovacaoResponse, EmptyEvent, PedidoCanceladoEvent> {

    private final PedidoDomainService pedidoDomainService;
    private final PedidoCanceladoPagamentoRequestMessagePublisher pedidoCanceladoPagamentoRequestMessagePublisher;
    private final PedidoSagaHelper pedidoSagaHelper;

    public PedidoAprovacaoSaga(PedidoDomainService pedidoDomainService, PedidoCanceladoPagamentoRequestMessagePublisher pedidoCanceladoPagamentoRequestMessagePublisher, PedidoSagaHelper pedidoSagaHelper) {
        this.pedidoDomainService = pedidoDomainService;
        this.pedidoCanceladoPagamentoRequestMessagePublisher = pedidoCanceladoPagamentoRequestMessagePublisher;
        this.pedidoSagaHelper = pedidoSagaHelper;
    }

    @Override
    @Transactional
    public EmptyEvent process(RestauranteAprovacaoResponse restauranteAprovacaoResponse) {

        log.info("Completando aprovação para pedido com id: {}.", restauranteAprovacaoResponse);
        Pedido pedido = pedidoSagaHelper.findPedido(restauranteAprovacaoResponse.getPedidoId());
        pedidoDomainService.aprovarPedido(pedido);
        pedidoSagaHelper.savePedido(pedido);
        log.info("Pedido com id: {} está aprovado.", pedido.getId().getValue());
        return EmptyEvent.INSTANCE;
    }

    @Override
    @Transactional
    public PedidoCanceladoEvent rollback(RestauranteAprovacaoResponse restauranteAprovacaoResponse) {

        log.info("Cancelando pedido com id: {}.", restauranteAprovacaoResponse);
        Pedido pedido = pedidoSagaHelper.findPedido(restauranteAprovacaoResponse.getPedidoId());
        var pedidoCanceladoEvent = pedidoDomainService.cancelarPagamentoDoPedido(pedido, restauranteAprovacaoResponse.getMensagensFalha(), pedidoCanceladoPagamentoRequestMessagePublisher);
        pedidoSagaHelper.savePedido(pedido);
        log.info("Pedido com id: {} está cancelado.", pedido.getId().getValue());
        return pedidoCanceladoEvent;
    }
}
