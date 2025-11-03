package org.vortxyz.pedido.service.dataaccess.pedido.entity;

import jakarta.persistence.*;
import lombok.*;
import org.vortxyz.domain.valueobject.PedidoStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Table(name = "pedidos")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoEntity {

    @Id
    private UUID id;

    private UUID clienteId;

    private UUID restauranteId;

    private UUID rastreamentoId;

    private BigDecimal preco;

    @Enumerated(EnumType.STRING)
    private PedidoStatus pedidoStatus;

    private String mensagensFalha;

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private PedidoEnderecoEntity endereco;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<PedidoItemEntity> itens;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PedidoEntity that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
