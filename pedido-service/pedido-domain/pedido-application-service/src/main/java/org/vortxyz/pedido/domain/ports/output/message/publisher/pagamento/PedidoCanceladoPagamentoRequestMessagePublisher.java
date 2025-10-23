package org.vortxyz.pedido.domain.ports.output.message.publisher.pagamento;

import org.vortxyz.domain.event.publisher.DomainEventPublisher;
import org.vortxyz.pedido.domain.event.PedidoCanceladoEvent;

public interface PedidoCanceladoPagamentoRequestMessagePublisher extends DomainEventPublisher<PedidoCanceladoEvent> {
}
