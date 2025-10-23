package org.vortxyz.pedido.domain;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.vortxyz.pedido.domain.dto.create.PedidoCriarCommand;
import org.vortxyz.pedido.domain.dto.create.PedidoCriarResponse;
import org.vortxyz.pedido.domain.entity.Cliente;
import org.vortxyz.pedido.domain.entity.Pedido;
import org.vortxyz.pedido.domain.entity.Restaurante;
import org.vortxyz.pedido.domain.event.PedidoCriadoEvent;
import org.vortxyz.pedido.domain.exception.PedidoDomainException;
import org.vortxyz.pedido.domain.mapper.PedidoMapper;
import org.vortxyz.pedido.domain.ports.output.repository.ClienteRepository;
import org.vortxyz.pedido.domain.ports.output.repository.PedidoRepository;
import org.vortxyz.pedido.domain.ports.output.repository.RestauranteRepository;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class PedidoCriarCommandHandler {

    private final PedidoDomainService pedidoDomainService;
    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final RestauranteRepository restauranteRepository;
    private final PedidoMapper pedidoMapper;

    public PedidoCriarCommandHandler(PedidoDomainService pedidoDomainService, PedidoRepository pedidoRepository, ClienteRepository clienteRepository, RestauranteRepository restauranteRepository, PedidoMapper pedidoMapper) {
        this.pedidoDomainService = pedidoDomainService;
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.restauranteRepository = restauranteRepository;
        this.pedidoMapper = pedidoMapper;
    }

    @Transactional
    public PedidoCriarResponse pedidoCriar(PedidoCriarCommand pedidoCriarCommand){
        conferirCliente(pedidoCriarCommand.getClienteId());
        Restaurante restauranteEncontrado = conferirRestaurante(pedidoCriarCommand);
        Pedido pedido = pedidoMapper.pedidoCriarCommandToPedido(pedidoCriarCommand);
        PedidoCriadoEvent pedidoCriadoEvent = pedidoDomainService.validarEInicializarPedido(pedido, restauranteEncontrado);
        Pedido pedidoSalvo = pedidoSalvar(pedido);
        log.info("Pedido criado com id: {}", pedidoSalvo.getId().getValue());
        return pedidoMapper.pedidoToPedidoCriarResponse(pedidoSalvo);
    }

    private void conferirCliente(@NotNull UUID clienteId) {
        Optional<Cliente> clienteEncontrado = clienteRepository.encontrarClientePorId(clienteId);
        if (clienteEncontrado.isEmpty()) {
            log.warn("Cliente com id: {} não encontrado", clienteId);
            throw new PedidoDomainException("Cliente com id: " + clienteId + " não encontrado.");
        }
    }

    private Restaurante conferirRestaurante(PedidoCriarCommand pedidoCriarCommand) {
        Restaurante restaurante = pedidoMapper.pedidoCriarCommandToRestaurante(pedidoCriarCommand);
        Optional<Restaurante> restauranteOptional = restauranteRepository.encontrarRestauranteInformacao(restaurante);
        if (restauranteOptional.isEmpty()) {
            log.warn("Restaurante com id: {} não encontrado", pedidoCriarCommand.getRestauranteId());
            throw new PedidoDomainException("Restaurante com id: " + pedidoCriarCommand.getRestauranteId() + " não encontrado.");
        }
        return restauranteOptional.get();
    }

    private Pedido pedidoSalvar(Pedido pedido) {
        Pedido pedidoResultante = pedidoRepository.salvar(pedido);
        if (pedidoResultante == null) {
            log.error("Não foi possível salvar o pedido.");
            throw new PedidoDomainException("Não foi possível salvar o pedido.");
        }
        log.info("Pedido salvo com id: {}", pedidoResultante.getId().getValue());
        return pedidoResultante;
    }
}
