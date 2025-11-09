package org.vortxyz.restaurante.service.domain.mapper;

import org.springframework.stereotype.Component;
import org.vortxyz.domain.valueobject.Dinheiro;
import org.vortxyz.domain.valueobject.PedidoId;
import org.vortxyz.domain.valueobject.PedidoStatus;
import org.vortxyz.domain.valueobject.RestauranteId;
import org.vortxyz.restaurante.domain.entity.PedidoDetalhe;
import org.vortxyz.restaurante.domain.entity.Produto;
import org.vortxyz.restaurante.domain.entity.Restaurante;
import org.vortxyz.restaurante.service.domain.dto.RestauranteAprovacaoRequest;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RestauranteDataMapper {
    public Restaurante restauranteAprovacaoRequestToRestaurante(RestauranteAprovacaoRequest restauranteAprovacaoRequest) {

        return Restaurante.builder()
                .id(new RestauranteId(UUID.fromString(restauranteAprovacaoRequest.getRestauranteId())))
                .pedidoDetalhe(PedidoDetalhe.builder()
                        .id(new PedidoId(UUID.fromString(restauranteAprovacaoRequest.getPedidoId())))
                        .produtos(restauranteAprovacaoRequest.getProdutos().stream().map(
                                produto -> Produto.builder()
                                        .id(produto.getId())
                                        .quantidade(produto.getQuantidade())
                                        .build())
                                .collect(Collectors.toList()))
                        .quantiaTotal(new Dinheiro(restauranteAprovacaoRequest.getPreco()))
                        .pedidoStatus(PedidoStatus.valueOf(restauranteAprovacaoRequest.getRestaurantePedidoStatus().name()))
                        .build())
                .build();
    }
}
