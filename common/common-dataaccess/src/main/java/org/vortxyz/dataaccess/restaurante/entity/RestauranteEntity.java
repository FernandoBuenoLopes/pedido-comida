package org.vortxyz.dataaccess.restaurante.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
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

    private Boolean produtoDisponivel;

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
