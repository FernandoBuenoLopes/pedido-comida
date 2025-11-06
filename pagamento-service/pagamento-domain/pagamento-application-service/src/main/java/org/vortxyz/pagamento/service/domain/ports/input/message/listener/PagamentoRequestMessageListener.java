package org.vortxyz.pagamento.service.domain.ports.input.message.listener;

import org.vortxyz.pagamento.service.domain.dto.PagamentoRequest;

public interface PagamentoRequestMessageListener {

    void completarPagamento(PagamentoRequest pagamentoRequest);

    void cancelarPagamento(PagamentoRequest pagamentoRequest);
}
