package org.vortxyz.pedido.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.vortxyz.pedido.domain.dto.message.PagamentoResponse;
import org.vortxyz.pedido.domain.ports.input.message.listener.pagamento.PagamentoResponseMessageListener;

@Slf4j
@Service
@Validated
public class PagamentoResponseMessageListenerImpl implements PagamentoResponseMessageListener {
    @Override
    public void pagamentoCompleto(PagamentoResponse pagamentoResponse) {

    }

    @Override
    public void pagamentoCancelado(PagamentoResponse pagamentoResponse) {

    }
}
