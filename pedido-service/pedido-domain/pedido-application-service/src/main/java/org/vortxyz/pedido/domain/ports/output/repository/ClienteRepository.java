package org.vortxyz.pedido.domain.ports.output.repository;

import org.vortxyz.pedido.domain.entity.Cliente;

import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository {

    Optional<Cliente> encontrarClientePorId(UUID clienteId);
}
