package org.vortxyz.restaurante.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.vortxyz.domain.valueobject.PedidoId;
import org.vortxyz.restaurante.domain.RestauranteDomainService;
import org.vortxyz.restaurante.domain.entity.Restaurante;
import org.vortxyz.restaurante.domain.event.PedidoAprovacaoEvent;
import org.vortxyz.restaurante.domain.exception.RestauranteNaoEncontradoException;
import org.vortxyz.restaurante.service.domain.dto.RestauranteAprovacaoRequest;
import org.vortxyz.restaurante.service.domain.mapper.RestauranteDataMapper;
import org.vortxyz.restaurante.service.domain.ports.output.message.publisher.PedidoAprovadoMessagePublisher;
import org.vortxyz.restaurante.service.domain.ports.output.message.publisher.PedidoRejeitadoMessagePublisher;
import org.vortxyz.restaurante.service.domain.ports.output.repository.PedidoAprovacaoRepository;
import org.vortxyz.restaurante.service.domain.ports.output.repository.RestauranteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class RestauranteAprovacaoRequestHelper {

    private final RestauranteDomainService restauranteDomainService;
    private final RestauranteDataMapper restauranteDataMapper;
    private final RestauranteRepository restauranteRepository;
    private final PedidoAprovacaoRepository pedidoAprovacaoRepository;
    private final PedidoAprovadoMessagePublisher pedidoAprovadoMessagePublisher;
    private final PedidoRejeitadoMessagePublisher pedidoRejeitadoMessagePublisher;


    public RestauranteAprovacaoRequestHelper(RestauranteDomainService restauranteDomainService,
                                             RestauranteDataMapper restauranteDataMapper,
                                             RestauranteRepository restauranteRepository,
                                             PedidoAprovacaoRepository pedidoAprovacaoRepository,
                                             PedidoAprovadoMessagePublisher pedidoAprovadoMessagePublisher,
                                             PedidoRejeitadoMessagePublisher pedidoRejeitadoMessagePublisher) {
        this.restauranteDomainService = restauranteDomainService;
        this.restauranteDataMapper = restauranteDataMapper;
        this.restauranteRepository = restauranteRepository;
        this.pedidoAprovacaoRepository = pedidoAprovacaoRepository;
        this.pedidoAprovadoMessagePublisher = pedidoAprovadoMessagePublisher;
        this.pedidoRejeitadoMessagePublisher = pedidoRejeitadoMessagePublisher;
    }

    @Transactional
    public PedidoAprovacaoEvent persistPedidoAprovacao(RestauranteAprovacaoRequest restauranteAprovacaoRequest) {
        log.info("Processando aprovação do restaurante para pedido com id: {}.", restauranteAprovacaoRequest.getPedidoId());
        List<String> mensagensFalha = new ArrayList<>();
        Restaurante restaurante = findRestaurante(restauranteAprovacaoRequest);
        restauranteDomainService.validarPedido(restaurante, mensagensFalha, pedidoAprovadoMessagePublisher, pedidoRejeitadoMessagePublisher);
        PedidoAprovacaoEvent pedidoAprovacaoEvent = restauranteDomainService.validarPedido(
                restaurante,
                mensagensFalha,
                pedidoAprovadoMessagePublisher,
                pedidoRejeitadoMessagePublisher);
        pedidoAprovacaoRepository.save(restaurante.getPedidoAprovacao());
        return pedidoAprovacaoEvent;
    }

    private Restaurante findRestaurante(RestauranteAprovacaoRequest restauranteAprovacaoRequest) {
        Restaurante restaurante = restauranteDataMapper.restauranteAprovacaoRequestToRestaurante(restauranteAprovacaoRequest);
        Optional<Restaurante> restauranteResult = restauranteRepository.findRestauranteInformation(restaurante);
        if (restauranteResult.isEmpty()) {
            log.error("Restaurante com id: {} não encontrado.", restaurante.getId().getValue());
            throw new RestauranteNaoEncontradoException("Restaurante com id: " + restaurante.getId().getValue() + " não encontrado.");
        }
        Restaurante restauranteEntity = restauranteResult.get();
        restaurante.setAtivo(restauranteEntity.isAtivo());
        restaurante.getPedidoDetalhe().getProdutos().forEach(produto ->
                restauranteEntity.getPedidoDetalhe().getProdutos().forEach(p -> {
                    if(p.getId().equals(produto.getId())) {
                        produto.atualizarComNomePrecoDisponibilidadeConfirmados(p.getNome(), p.getPreco(), p.isDisponivel());
                    }
                }));
        restaurante.getPedidoDetalhe().setId(new PedidoId(UUID.fromString(restauranteAprovacaoRequest.getPedidoId())));
        return restaurante;
    }
}
