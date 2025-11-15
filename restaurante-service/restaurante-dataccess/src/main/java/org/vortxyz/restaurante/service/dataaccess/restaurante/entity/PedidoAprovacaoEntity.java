package org.vortxyz.restaurante.service.dataaccess.restaurante.entity;

import javax.persistence.*;
import lombok.*;
import org.vortxyz.domain.valueobject.PedidoAprovacaoStatus;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pedido_aprovacao", schema = "restaurante")
@Entity
public class PedidoAprovacaoEntity {

    @Id
    private UUID id;
    private UUID restauranteId;
    private UUID pedidoId;
    @Enumerated(EnumType.STRING)
    private PedidoAprovacaoStatus status;
}
