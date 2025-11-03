package org.vortxyz.pedido.service.dataaccess.pedido.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoItemEntityId implements Serializable {

    private Long id;

    private PedidoEntity pedido;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PedidoItemEntityId that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getPedido(), that.getPedido());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPedido());
    }
}
