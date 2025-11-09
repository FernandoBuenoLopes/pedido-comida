package org.vortxyz.pedido.service.messaging.mapper;

import org.springframework.stereotype.Component;
import org.vortxyz.domain.valueobject.PagamentoStatus;
import org.vortxyz.domain.valueobject.PedidoAprovacaoStatus;
import org.vortxyz.kafka.pedido.avro.model.*;
import org.vortxyz.pedido.domain.dto.message.PagamentoResponse;
import org.vortxyz.pedido.domain.dto.message.RestauranteAprovacaoResponse;
import org.vortxyz.pedido.domain.entity.Pedido;
import org.vortxyz.pedido.domain.event.PedidoCanceladoEvent;
import org.vortxyz.pedido.domain.event.PedidoCriadoEvent;
import org.vortxyz.pedido.domain.event.PedidoPagoEvent;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PedidoMessagingDataMapper {

    public PagamentoRequestAvroModel pedidoCriadoEventToPagamentoRequestAvroModel(PedidoCriadoEvent pedidoCriadoEvent) {

        Pedido pedido = pedidoCriadoEvent.getPedido();

        return PagamentoRequestAvroModel.builder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setClienteId(pedido.getClienteId().getValue().toString())
                .setPedidoId(pedido.getId().getValue().toString())
                .setPreco(pedido.getPreco().getQuantia())
                .setCriadoEm(pedidoCriadoEvent.getCriadoEm().toInstant())
                .setPedidoPagamentoStatus(PedidoPagamentoStatus.PENDENTE)
                .build();
    }

    public PagamentoRequestAvroModel pedidoCanceladoEventToPagamentoRequestAvroModel(PedidoCanceladoEvent pedidoCanceladoEvent) {

        Pedido pedido = pedidoCanceladoEvent.getPedido();

        return PagamentoRequestAvroModel.builder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setClienteId(pedido.getClienteId().getValue().toString())
                .setPedidoId(pedido.getId().getValue().toString())
                .setPreco(pedido.getPreco().getQuantia())
                .setCriadoEm(pedidoCanceladoEvent.getCriadoEm().toInstant())
                .setPedidoPagamentoStatus(PedidoPagamentoStatus.CANCELADO)
                .build();
    }


    public RestauranteAprovacaoRequestAvroModel pedidoPagoEventToRestauranteAprovacaoRequestAvroModel(PedidoPagoEvent pedidoPagoEvent) {

        Pedido pedido = pedidoPagoEvent.getPedido();

        return RestauranteAprovacaoRequestAvroModel.builder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setPedidoId(pedido.getId().getValue().toString())
                .setRestauranteId(pedido.getRestauranteId().getValue().toString())
                .setProdutos(pedido.getItens().stream().map(pedidoItem -> Produto.builder()
                        .setId(pedidoItem.getId().getValue().toString())
                        .setQuantidade(pedidoItem.getQuantidade())
                        .build()).collect(Collectors.toList()))
                .setPreco(pedido.getPreco().getQuantia())
                .setCriadoEm(pedidoPagoEvent.getCriadoEm().toInstant())
                .setRestaurantePedidoStatus(RestaurantePedidoStatus.PAGO)
                .build();
    }

    public PagamentoResponse pagamentoResponseAvroModelToPagamentoResponse(PagamentoResponseAvroModel pagamentoResponseAvroModel) {
        return PagamentoResponse.builder()
                .id(pagamentoResponseAvroModel.getId())
                .sagaId(pagamentoResponseAvroModel.getSagaId())
                .pagamentoId(pagamentoResponseAvroModel.getPagamentoId())
                .pedidoId(pagamentoResponseAvroModel.getPedidoId())
                .clienteId(pagamentoResponseAvroModel.getClienteId())
                .preco(pagamentoResponseAvroModel.getPreco())
                .pagamentoStatus(PagamentoStatus.valueOf(pagamentoResponseAvroModel.getPagamentoStatus().name()))
                .mensagensFalha(pagamentoResponseAvroModel.getMensagensFalha())
                .criadoEm(pagamentoResponseAvroModel.getCriadoEm())
                .build();
    }

    public RestauranteAprovacaoResponse restauranteAprovacaoResponseAvroModelToRestauranteAprovacaoResponse(RestauranteAprovacaoResponseAvroModel restauranteAprovacaoResponseAvroModel) {
        return RestauranteAprovacaoResponse.builder()
                .id(restauranteAprovacaoResponseAvroModel.getId())
                .sagaId(restauranteAprovacaoResponseAvroModel.getSagaId())
                .pedidoId(restauranteAprovacaoResponseAvroModel.getPedidoId())
                .restauranteId(restauranteAprovacaoResponseAvroModel.getRestauranteId())
                .pedidoAprovacaoStatus(PedidoAprovacaoStatus.valueOf(restauranteAprovacaoResponseAvroModel.getPedidoAprovacaoStatus().name()))
                .mensagensFalha(restauranteAprovacaoResponseAvroModel.getMensagensFalha())
                .criadoEm(restauranteAprovacaoResponseAvroModel.getCriadoEm())
                .build();
    }
}
