package org.vortxyz.restaurante.service.dataaccess.restaurante.mapper;

import org.springframework.stereotype.Component;
import org.vortxyz.dataaccess.restaurante.entity.RestauranteEntity;
import org.vortxyz.dataaccess.restaurante.exception.RestauranteDataAccessException;
import org.vortxyz.domain.valueobject.Dinheiro;
import org.vortxyz.domain.valueobject.PedidoId;
import org.vortxyz.domain.valueobject.ProdutoId;
import org.vortxyz.domain.valueobject.RestauranteId;
import org.vortxyz.restaurante.domain.entity.PedidoAprovacao;
import org.vortxyz.restaurante.domain.entity.PedidoDetalhe;
import org.vortxyz.restaurante.domain.entity.Produto;
import org.vortxyz.restaurante.domain.entity.Restaurante;
import org.vortxyz.restaurante.domain.valueobject.PedidoAprovacaoId;
import org.vortxyz.restaurante.service.dataaccess.restaurante.entity.PedidoAprovacaoEntity;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RestauranteDataAccessMapper {

    public List<UUID> restauranteToRestauranteProdutos(Restaurante restaurante) {

        return restaurante.getPedidoDetalhe().getProdutos().stream()
                .map(produto -> produto.getId().getValue())
                .collect(Collectors.toList());
    }

    public Restaurante restauranteEntityToRestaurante(List<RestauranteEntity> restauranteEntities) {

        RestauranteEntity restauranteEntity = restauranteEntities.stream().findFirst().orElseThrow(() -> new RestauranteDataAccessException("Restaurante não encontrado."));
        List<Produto> restauranteProdutos = restauranteEntities.stream().map(
                entity -> Produto.builder()
                        .id(new ProdutoId(entity.getProdutoId()))
                        .nome(entity.getProdutoNome())
                        .preco(new Dinheiro(entity.getProdutoPreco()))
                        .disponivel(entity.getProdutoDisponivel())
                        .build())
                .collect(Collectors.toList());
        return Restaurante.builder()
                .id(new RestauranteId(restauranteEntity.getRestauranteId()))
                .pedidoDetalhe(PedidoDetalhe.builder()
                        .produtos(restauranteProdutos)
                        .build())
                .ativo(restauranteEntity.getRestauranteAtivo())
                .build();
    }

    public PedidoAprovacaoEntity pedidoAprovacaoToPedidoAprovacaoEntity(PedidoAprovacao pedidoAprovacao) {

        return PedidoAprovacaoEntity.builder()
                .id(pedidoAprovacao.getId().getValue())
                .restauranteId(pedidoAprovacao.getRestauranteId().getValue())
                .pedidoId(pedidoAprovacao.getPedidoId().getValue())
                .status(pedidoAprovacao.getPedidoAprovacaoStatus())
                .build();
    }

    public PedidoAprovacao pedidoAprovacaoEntityToProdutoAprovacao(PedidoAprovacaoEntity pedidoAprovacaoEntity) {

        return PedidoAprovacao.builder()
                .pedidoAprovacaoId(new PedidoAprovacaoId(pedidoAprovacaoEntity.getId()))
                .pedidoId(new PedidoId(pedidoAprovacaoEntity.getPedidoId()))
                .restauranteId(new RestauranteId(pedidoAprovacaoEntity.getRestauranteId()))
                .pedidoAprovacaoStatus(pedidoAprovacaoEntity.getStatus())
                .build();
    }


}
