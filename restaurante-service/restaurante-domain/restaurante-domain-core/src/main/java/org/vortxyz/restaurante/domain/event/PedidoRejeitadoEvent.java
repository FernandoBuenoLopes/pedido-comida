package org.vortxyz.restaurante.domain.event;

import org.vortxyz.domain.event.publisher.DomainEventPublisher;
import org.vortxyz.domain.valueobject.RestauranteId;
import org.vortxyz.restaurante.domain.entity.PedidoAprovacao;

import java.time.ZonedDateTime;
import java.util.List;

public class PedidoRejeitadoEvent extends PedidoAprovacaoEvent {

    private final DomainEventPublisher<PedidoRejeitadoEvent> pedidoRejeitadoEventDomainEventPublisher;

    public PedidoRejeitadoEvent(PedidoAprovacao pedidoAprovacao, RestauranteId restauranteId, List<String> mensagensFalha, ZonedDateTime criadoEm, DomainEventPublisher<PedidoRejeitadoEvent> pedidoRejeitadoEventDomainEventPublisher) {
        super(pedidoAprovacao, restauranteId, mensagensFalha, criadoEm);
        this.pedidoRejeitadoEventDomainEventPublisher = pedidoRejeitadoEventDomainEventPublisher;
    }

    @Override
    public void disparar() {
        pedidoRejeitadoEventDomainEventPublisher.publish(this);
    }
}
