package org.vortxyz.pedido.service.dataaccess.cliente.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Table(name = "pedido_cliente_m_view", schema = "cliente")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteEntity {

    @Id
    private UUID id;
}