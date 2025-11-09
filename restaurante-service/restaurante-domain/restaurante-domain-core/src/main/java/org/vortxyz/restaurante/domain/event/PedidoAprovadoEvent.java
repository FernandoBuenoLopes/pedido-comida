package org.vortxyz.restaurante.domain.event;

import org.vortxyz.domain.event.publisher.DomainEventPublisher;
import org.vortxyz.domain.valueobject.RestauranteId;
import org.vortxyz.restaurante.domain.entity.PedidoAprovacao;

import java.time.ZonedDateTime;
import java.util.List;

public class PedidoAprovadoEvent extends PedidoAprovacaoEvent {

    private final DomainEventPublisher<PedidoAprovadoEvent> pedidoAprovadoEventDomainEventPublisher;

    public PedidoAprovadoEvent(PedidoAprovacao pedidoAprovacao, RestauranteId restauranteId, List<String> mensagensFalha, ZonedDateTime criadoEm, DomainEventPublisher<PedidoAprovadoEvent> pedidoAprovadoEventDomainEventPublisher) {
        super(pedidoAprovacao, restauranteId, mensagensFalha, criadoEm);
        this.pedidoAprovadoEventDomainEventPublisher = pedidoAprovadoEventDomainEventPublisher;
    }

    @Override
    public void disparar() {

        pedidoAprovadoEventDomainEventPublisher.publish(this);
    }
}
