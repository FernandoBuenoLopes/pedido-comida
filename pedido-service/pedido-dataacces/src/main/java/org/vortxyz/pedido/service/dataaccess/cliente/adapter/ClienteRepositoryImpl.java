package org.vortxyz.pedido.service.dataaccess.cliente.adapter;

import org.springframework.stereotype.Component;
import org.vortxyz.pedido.domain.entity.Cliente;
import org.vortxyz.pedido.domain.ports.output.repository.ClienteRepository;
import org.vortxyz.pedido.service.dataaccess.cliente.mapper.ClienteDataAccessMapper;
import org.vortxyz.pedido.service.dataaccess.cliente.repository.ClienteJpaRepository;

import java.util.Optional;
import java.util.UUID;

@Component
public class ClienteRepositoryImpl implements ClienteRepository {

    private final ClienteJpaRepository clienteJpaRepository;
    private final ClienteDataAccessMapper clienteDataAccessMapper;

    public ClienteRepositoryImpl(ClienteJpaRepository clienteJpaRepository, ClienteDataAccessMapper clienteDataAccessMapper) {
        this.clienteJpaRepository = clienteJpaRepository;
        this.clienteDataAccessMapper = clienteDataAccessMapper;
    }

    @Override
    public Optional<Cliente> encontrarClientePorId(UUID clienteId) {
        return clienteJpaRepository.findById(clienteId).map(clienteDataAccessMapper::clienteEntityToCliente);
    }
}
