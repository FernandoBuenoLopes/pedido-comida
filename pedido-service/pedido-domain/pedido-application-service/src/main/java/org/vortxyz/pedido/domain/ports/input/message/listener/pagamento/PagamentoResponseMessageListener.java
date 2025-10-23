package org.vortxyz.pedido.domain.ports.input.message.listener.pagamento;

import org.vortxyz.pedido.domain.dto.message.PagamentoResponse;

public interface PagamentoResponseMessageListener {

    void pagamentoCompleto(PagamentoResponse pagamentoResponse);

    void pagamentoCancelado(PagamentoResponse pagamentoResponse);
}
