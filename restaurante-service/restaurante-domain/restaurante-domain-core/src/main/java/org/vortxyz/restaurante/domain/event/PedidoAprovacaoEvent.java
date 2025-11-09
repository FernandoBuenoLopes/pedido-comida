package org.vortxyz.restaurante.domain.event;

import org.vortxyz.domain.event.DomainEvent;
import org.vortxyz.domain.valueobject.RestauranteId;
import org.vortxyz.restaurante.domain.entity.PedidoAprovacao;

import java.time.ZonedDateTime;
import java.util.List;

public abstract class PedidoAprovacaoEvent implements DomainEvent<PedidoAprovacao> {

    private final PedidoAprovacao pedidoAprovacao;
    private final RestauranteId restauranteId;
    private final List<String> mensagensFalha;
    private final ZonedDateTime criadoEm;

    public PedidoAprovacaoEvent(PedidoAprovacao pedidoAprovacao, RestauranteId restauranteId, List<String> mensagensFalha, ZonedDateTime criadoEm) {
        this.pedidoAprovacao = pedidoAprovacao;
        this.restauranteId = restauranteId;
        this.mensagensFalha = mensagensFalha;
        this.criadoEm = criadoEm;
    }

    public PedidoAprovacao getPedidoAprovacao() {
        return pedidoAprovacao;
    }

    public RestauranteId getRestauranteId() {
        return restauranteId;
    }

    public List<String> getMensagensFalha() {
        return mensagensFalha;
    }

    public ZonedDateTime getCriadoEm() {
        return criadoEm;
    }
}
