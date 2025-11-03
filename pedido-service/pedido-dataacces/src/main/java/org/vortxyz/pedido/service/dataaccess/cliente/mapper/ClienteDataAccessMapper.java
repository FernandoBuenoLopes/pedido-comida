package org.vortxyz.pedido.service.dataaccess.cliente.mapper;

import org.springframework.stereotype.Component;
import org.vortxyz.domain.valueobject.ClienteId;
import org.vortxyz.pedido.domain.entity.Cliente;
import org.vortxyz.pedido.service.dataaccess.cliente.entity.ClienteEntity;

@Component
public class ClienteDataAccessMapper {

    public Cliente clienteEntityToCliente(ClienteEntity clienteEntity) {
        return new Cliente(new ClienteId(clienteEntity.getId()));
    }

    public ClienteEntity clienteToClienteEntity(Cliente cliente) {
        return ClienteEntity.builder()
                .id(cliente.getId().getValue())
                .build();
    }
}
