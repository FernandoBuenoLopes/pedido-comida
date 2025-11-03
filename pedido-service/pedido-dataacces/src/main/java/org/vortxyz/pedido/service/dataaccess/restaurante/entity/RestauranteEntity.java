package org.vortxyz.pedido.service.dataaccess.restaurante.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Table(name = "pedido_restaurante_m_view", schema = "restaurante")
@IdClass(RestauranteEntityId.class)
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestauranteEntity {

    @Id
    private UUID restauranteId;

    @Id
    private UUID produtoId;

    private String restauranteNome;

    private Boolean restauranteAtivo;

    private String produtoNome;

    private BigDecimal produtoPreco;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RestauranteEntity that)) return false;
        return Objects.equals(getRestauranteId(), that.getRestauranteId()) && Objects.equals(getProdutoId(), that.getProdutoId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRestauranteId(), getProdutoId());
    }
}
