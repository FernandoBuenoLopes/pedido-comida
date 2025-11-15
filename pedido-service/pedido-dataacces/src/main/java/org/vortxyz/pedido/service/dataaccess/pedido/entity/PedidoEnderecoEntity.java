package org.vortxyz.pedido.service.dataaccess.pedido.entity;

import javax.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@Table(name = "pedido_endereco")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoEnderecoEntity {

    @Id
    private UUID id;

    @OneToOne
    @JoinColumn(name = "PEDIDO_ID")
    private PedidoEntity pedido;

    private String rua;

    private String cep;

    private String cidade;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PedidoEnderecoEntity that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
