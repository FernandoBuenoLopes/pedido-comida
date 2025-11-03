package org.vortxyz.pedido.service.dataaccess.restaurante.mapper;

import org.springframework.stereotype.Component;
import org.vortxyz.domain.valueobject.Dinheiro;
import org.vortxyz.domain.valueobject.ProdutoId;
import org.vortxyz.domain.valueobject.RestauranteId;
import org.vortxyz.pedido.domain.entity.Produto;
import org.vortxyz.pedido.domain.entity.Restaurante;
import org.vortxyz.pedido.service.dataaccess.restaurante.entity.RestauranteEntity;
import org.vortxyz.pedido.service.dataaccess.restaurante.exception.RestauranteDataAccessException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RestauranteDataAccesMapper {

    public List<UUID> restauranteToRestauranteProdutos(Restaurante restaurante) {
        return restaurante.getProdutos().stream().map(produto -> produto.getId().getValue()).collect(Collectors.toList());
    }

    public Restaurante restauranteEntityToRestaurante(List<RestauranteEntity> restauranteEntities) {
        RestauranteEntity restauranteEntity = restauranteEntities.stream().findFirst()
                .orElseThrow(() -> new RestauranteDataAccessException("Restaurante não encontrado."));

        return Restaurante.builder()
                .restauranteId(new RestauranteId(restauranteEntity.getRestauranteId()))
                .ativo(restauranteEntity.getRestauranteAtivo())
                .produtos(restauranteEntities.stream()
                        .map(restaurante ->
                            new Produto(
                                new ProdutoId(restaurante.getProdutoId()),
                                restaurante.getProdutoNome(),
                                new Dinheiro(restaurante.getProdutoPreco())
                            )
                        ).collect(Collectors.toList()))
                .build();
    }
}
