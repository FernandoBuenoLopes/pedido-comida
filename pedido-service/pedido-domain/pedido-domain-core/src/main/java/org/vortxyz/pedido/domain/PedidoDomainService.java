package org.vortxyz.pedido.domain;

import org.vortxyz.domain.event.publisher.DomainEventPublisher;
import org.vortxyz.pedido.domain.entity.Pedido;
import org.vortxyz.pedido.domain.entity.Restaurante;
import org.vortxyz.pedido.domain.event.PedidoCanceladoEvent;
import org.vortxyz.pedido.domain.event.PedidoCriadoEvent;
import org.vortxyz.pedido.domain.event.PedidoPagoEvent;

import java.util.List;

public interface PedidoDomainService {

    PedidoCriadoEvent validarEInicializarPedido(Pedido pedido, Restaurante restaurante, DomainEventPublisher<PedidoCriadoEvent> pedidoCriadoEventDomainEventPublisher);

    PedidoPagoEvent pagarPedido(Pedido pedido, DomainEventPublisher<PedidoPagoEvent> pedidoPagoEventDomainEventPublisher);

    void aprovarPedido(Pedido pedido);

    PedidoCanceladoEvent cancelarPagamentoDoPedido(Pedido pedido, List<String> mensagensFalha, DomainEventPublisher<PedidoCanceladoEvent> pedidoCanceladoEventDomainEventPublisher);

    void cancelarPedido(Pedido pedido, List<String> mensagensFalha);
}
