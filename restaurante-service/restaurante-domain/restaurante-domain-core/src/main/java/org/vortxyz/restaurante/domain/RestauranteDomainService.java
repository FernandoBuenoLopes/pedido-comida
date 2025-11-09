package org.vortxyz.restaurante.domain;

import org.vortxyz.domain.event.publisher.DomainEventPublisher;
import org.vortxyz.restaurante.domain.entity.Restaurante;
import org.vortxyz.restaurante.domain.event.PedidoAprovacaoEvent;
import org.vortxyz.restaurante.domain.event.PedidoAprovadoEvent;
import org.vortxyz.restaurante.domain.event.PedidoRejeitadoEvent;

import java.util.List;

public interface RestauranteDomainService {

    PedidoAprovacaoEvent validarPedido(Restaurante restaurante, List<String> mensagensFalha,
                                       DomainEventPublisher<PedidoAprovadoEvent> pedidoAprovadoEventDomainEventPublisher,
                                       DomainEventPublisher<PedidoRejeitadoEvent> pedidoRejeitadoEventDomainEventPublisher);
}
