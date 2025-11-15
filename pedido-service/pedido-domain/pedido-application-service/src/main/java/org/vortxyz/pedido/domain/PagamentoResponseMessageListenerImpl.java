package org.vortxyz.pedido.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.vortxyz.pedido.domain.dto.message.PagamentoResponse;
import org.vortxyz.pedido.domain.ports.input.message.listener.pagamento.PagamentoResponseMessageListener;

import static org.vortxyz.pedido.domain.entity.Pedido.DELIMITADOR_MENSAGENS_FALHA;

@Slf4j
@Service
@Validated
public class PagamentoResponseMessageListenerImpl implements PagamentoResponseMessageListener {

    private final PedidoPagamentoSaga pedidoPagamentoSaga;

    public PagamentoResponseMessageListenerImpl(PedidoPagamentoSaga pedidoPagamentoSaga) {
        this.pedidoPagamentoSaga = pedidoPagamentoSaga;
    }

    @Override
    public void pagamentoCompleto(PagamentoResponse pagamentoResponse) {

        var pedidoPagoevent = pedidoPagamentoSaga.process(pagamentoResponse);
        log.info("Publicando PedidoPagoEvent para pedido com id: {}.", pagamentoResponse.getPagamentoId());
        pedidoPagoevent.disparar();
    }

    @Override
    public void pagamentoCancelado(PagamentoResponse pagamentoResponse) {

        pedidoPagamentoSaga.rollback(pagamentoResponse);
        log.info("Pedido com id: {} retornado com a(s) mensagem(s) de falha: {}.", pagamentoResponse.getPedidoId(), String.join(DELIMITADOR_MENSAGENS_FALHA, pagamentoResponse.getMensagensFalha()));
    }
}
