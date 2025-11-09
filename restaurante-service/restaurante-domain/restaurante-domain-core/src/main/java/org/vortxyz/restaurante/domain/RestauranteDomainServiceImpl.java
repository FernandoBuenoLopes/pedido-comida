package org.vortxyz.restaurante.domain;

import lombok.extern.slf4j.Slf4j;
import org.vortxyz.domain.event.publisher.DomainEventPublisher;
import org.vortxyz.domain.valueobject.PedidoAprovacaoStatus;
import org.vortxyz.restaurante.domain.entity.Restaurante;
import org.vortxyz.restaurante.domain.event.PedidoAprovacaoEvent;
import org.vortxyz.restaurante.domain.event.PedidoAprovadoEvent;
import org.vortxyz.restaurante.domain.event.PedidoRejeitadoEvent;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.vortxyz.domain.DomainConstants.FUSO_HORARIO;

@Slf4j
public class RestauranteDomainServiceImpl implements RestauranteDomainService {

    @Override
    public PedidoAprovacaoEvent validarPedido(Restaurante restaurante, List<String> mensagensFalha, DomainEventPublisher<PedidoAprovadoEvent> pedidoAprovadoEventDomainEventPublisher, DomainEventPublisher<PedidoRejeitadoEvent> pedidoRejeitadoEventDomainEventPublisher) {

        restaurante.validarPedido(mensagensFalha);
        log.info("Validando pedido com id: {}", restaurante.getPedidoDetalhe().getId().getValue());

        if(mensagensFalha.isEmpty()) {
            log.info("Pedido aprovado com id: {}.", restaurante.getPedidoDetalhe().getId().getValue());
            restaurante.criarPedidoAprovacao(PedidoAprovacaoStatus.APROVADO);
            return new PedidoAprovadoEvent(restaurante.getPedidoAprovacao(),
                    restaurante.getId(),
                    mensagensFalha,
                    ZonedDateTime.now(ZoneId.of(FUSO_HORARIO)),
                    pedidoAprovadoEventDomainEventPublisher);
        } else {
            log.info("Pedido rejeitado com id: {}.", restaurante.getPedidoDetalhe().getId().getValue());
            restaurante.criarPedidoAprovacao(PedidoAprovacaoStatus.REJEITADO);
            return new PedidoRejeitadoEvent(restaurante.getPedidoAprovacao(),
                    restaurante.getId(),
                    mensagensFalha,
                    ZonedDateTime.now(ZoneId.of(FUSO_HORARIO)),
                    pedidoRejeitadoEventDomainEventPublisher);
        }
    }
}
