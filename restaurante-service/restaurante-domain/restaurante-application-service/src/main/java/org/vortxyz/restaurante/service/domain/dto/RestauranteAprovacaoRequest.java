package org.vortxyz.restaurante.service.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.vortxyz.domain.valueobject.RestaurantePedidoStatus;
import org.vortxyz.restaurante.domain.entity.Produto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class RestauranteAprovacaoRequest {

    private String id;
    private String sagaId;
    private String restauranteId;
    private String pedidoId;
    private RestaurantePedidoStatus restaurantePedidoStatus;
    private List<Produto> produtos;
    private BigDecimal preco;
    private Instant criadoEm;
}
