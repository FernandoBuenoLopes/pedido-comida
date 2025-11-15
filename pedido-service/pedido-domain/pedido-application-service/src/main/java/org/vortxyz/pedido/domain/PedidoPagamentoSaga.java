package org.vortxyz.pedido.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.vortxyz.domain.event.EmptyEvent;
import org.vortxyz.pedido.domain.dto.message.PagamentoResponse;
import org.vortxyz.pedido.domain.entity.Pedido;
import org.vortxyz.pedido.domain.event.PedidoPagoEvent;
import org.vortxyz.pedido.domain.ports.output.message.publisher.restauranteaprovacao.PedidoPagoRestauranteRequestMessagePublisher;
import org.vortxyz.saga.SagaStep;

import java.util.UUID;

@Slf4j
@Component
public class PedidoPagamentoSaga implements SagaStep<PagamentoResponse, PedidoPagoEvent, EmptyEvent> {

    private final PedidoDomainService pedidoDomainService;
    private final PedidoPagoRestauranteRequestMessagePublisher pedidoPagoRestauranteRequestMessagePublisher;
    private final PedidoSagaHelper pedidoSagaHelper;

    public PedidoPagamentoSaga(PedidoDomainService pedidoDomainService, PedidoPagoRestauranteRequestMessagePublisher pedidoPagoRestauranteRequestMessagePublisher, PedidoSagaHelper pedidoSagaHelper) {
        this.pedidoDomainService = pedidoDomainService;
        this.pedidoPagoRestauranteRequestMessagePublisher = pedidoPagoRestauranteRequestMessagePublisher;
        this.pedidoSagaHelper = pedidoSagaHelper;
    }

    @Override
    @Transactional
    public PedidoPagoEvent process(PagamentoResponse pagamentoResponse) {

        log.info("Completando pagamento para pedido com id: {}.", pagamentoResponse.getPagamentoId());
        Pedido pedido = pedidoSagaHelper.findPedido(pagamentoResponse.getPagamentoId());
        PedidoPagoEvent pedidoPagoEvent = pedidoDomainService.pagarPedido(pedido, pedidoPagoRestauranteRequestMessagePublisher);
        pedidoSagaHelper.savePedido(pedido);
        log.info("Pedido com id: {} está pago.", pedido.getId().getValue());
        return pedidoPagoEvent;
    }

    @Override
    @Transactional
    public EmptyEvent rollback(PagamentoResponse pagamentoResponse) {

        log.info("Cancelando pedido com id: {}.", pagamentoResponse.getPedidoId());
        Pedido pedido = pedidoSagaHelper.findPedido(pagamentoResponse.getPedidoId());
        pedidoDomainService.cancelarPedido(pedido, pagamentoResponse.getMensagensFalha());
        pedidoSagaHelper.savePedido(pedido);
        log.info("Pedido com id: {} está cancelado.", pedido.getId().getValue());
        return EmptyEvent.INSTANCE;
    }
}
