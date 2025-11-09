package org.vortxyz.restaurante.service.messaging.mapper;

import org.springframework.stereotype.Component;
import org.vortxyz.domain.valueobject.ProdutoId;
import org.vortxyz.domain.valueobject.RestaurantePedidoStatus;
import org.vortxyz.kafka.pedido.avro.model.PedidoAprovacaoStatus;
import org.vortxyz.kafka.pedido.avro.model.RestauranteAprovacaoRequestAvroModel;
import org.vortxyz.kafka.pedido.avro.model.RestauranteAprovacaoResponseAvroModel;
import org.vortxyz.restaurante.domain.entity.Produto;
import org.vortxyz.restaurante.domain.event.PedidoAprovadoEvent;
import org.vortxyz.restaurante.domain.event.PedidoRejeitadoEvent;
import org.vortxyz.restaurante.service.domain.dto.RestauranteAprovacaoRequest;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RestauranteMessagingDataMapper {

    public RestauranteAprovacaoResponseAvroModel pedidoAprovadoEventToRestauranteAprovacaoResponseAvroModel(PedidoAprovadoEvent pedidoAprovadoEvent) {

        return RestauranteAprovacaoResponseAvroModel.builder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setPedidoId(pedidoAprovadoEvent.getPedidoAprovacao().getPedidoId().getValue().toString())
                .setRestauranteId(pedidoAprovadoEvent.getRestauranteId().getValue().toString())
                .setCriadoEm(pedidoAprovadoEvent.getCriadoEm().toInstant())
                .setPedidoAprovacaoStatus(PedidoAprovacaoStatus.valueOf(pedidoAprovadoEvent.getPedidoAprovacao().getPedidoAprovacaoStatus().name()))
                .setMensagensFalha(pedidoAprovadoEvent.getMensagensFalha())
                .build();
    }

    public RestauranteAprovacaoResponseAvroModel pedidoRejeitadoEventToRestauranteAprovacaoResponseAvroModel(PedidoRejeitadoEvent pedidoRejeitadoEvent) {

        return RestauranteAprovacaoResponseAvroModel.builder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setPedidoId(pedidoRejeitadoEvent.getPedidoAprovacao().getPedidoId().getValue().toString())
                .setRestauranteId(pedidoRejeitadoEvent.getRestauranteId().getValue().toString())
                .setCriadoEm(pedidoRejeitadoEvent.getCriadoEm().toInstant())
                .setPedidoAprovacaoStatus(PedidoAprovacaoStatus.valueOf(pedidoRejeitadoEvent.getPedidoAprovacao().getPedidoAprovacaoStatus().name()))
                .setMensagensFalha(pedidoRejeitadoEvent.getMensagensFalha())
                .build();
    }

    public RestauranteAprovacaoRequest restauranteAprovacaoRequestAvroModelToRestauranteAprovacaoRequest(RestauranteAprovacaoRequestAvroModel restauranteAprovacaoRequestAvroModel) {


        // TEM QUE SER O REQUEST AVRO MODEL
        return RestauranteAprovacaoRequest.builder()
                .id(restauranteAprovacaoRequestAvroModel.getId())
                .sagaId(restauranteAprovacaoRequestAvroModel.getSagaId())
                .restauranteId(restauranteAprovacaoRequestAvroModel.getRestauranteId())
                .pedidoId(restauranteAprovacaoRequestAvroModel.getPedidoId())
                .restaurantePedidoStatus(RestaurantePedidoStatus.valueOf(restauranteAprovacaoRequestAvroModel.getRestaurantePedidoStatus().name()))
                .produtos(restauranteAprovacaoRequestAvroModel.getProdutos().stream()
                        .map(produtoAvroModel -> {
                            return Produto.builder()
                                    .id(new ProdutoId(UUID.fromString(produtoAvroModel.getId())))
                                    .quantidade(produtoAvroModel.getQuantidade())
                                    .build();
                        })
                        .collect(Collectors.toList()))
                .preco(restauranteAprovacaoRequestAvroModel.getPreco())
                .criadoEm(restauranteAprovacaoRequestAvroModel.getCriadoEm())
                .build();
    }
}
