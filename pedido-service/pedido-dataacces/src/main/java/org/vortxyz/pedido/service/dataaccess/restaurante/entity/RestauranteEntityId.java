package org.vortxyz.pedido.service.dataaccess.restaurante.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestauranteEntityId implements Serializable {

    private UUID restauranteId;
    private UUID produtoId;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RestauranteEntityId that)) return false;
        return Objects.equals(getRestauranteId(), that.getRestauranteId()) && Objects.equals(getProdutoId(), that.getProdutoId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRestauranteId(), getProdutoId());
    }
}
