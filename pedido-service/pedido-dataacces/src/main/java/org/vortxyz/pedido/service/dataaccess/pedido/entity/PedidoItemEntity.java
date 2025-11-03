package org.vortxyz.pedido.service.dataaccess.pedido.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Table(name = "pedido_items")
@IdClass(PedidoItemEntityId.class)
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoItemEntity {

    @Id
    private Long id;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PEDIDO_ID")
    private PedidoEntity pedido;

    private UUID produtoId;
    private BigDecimal preco;
    private Integer quantidade;
    private BigDecimal subTotal;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PedidoItemEntity that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getPedido(), that.getPedido());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPedido());
    }
}
