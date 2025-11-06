package org.vortxyz.pagamento.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.vortxyz.pagamento.service.domain.dto.PagamentoRequest;
import org.vortxyz.pagamento.service.domain.event.PagamentoEvent;
import org.vortxyz.pagamento.service.domain.ports.input.message.listener.PagamentoRequestMessageListener;

@Slf4j
@Service
public class PagamentoRequestMessageListenerImpl implements PagamentoRequestMessageListener {

    private final PagamentoRequestHelper pagamentoRequestHelper;

    public PagamentoRequestMessageListenerImpl(PagamentoRequestHelper pagamentoRequestHelper) {
        this.pagamentoRequestHelper = pagamentoRequestHelper;
    }


    @Override
    public void completarPagamento(PagamentoRequest pagamentoRequest) {
        PagamentoEvent pagamentoEvent = pagamentoRequestHelper.persistirPagamento(pagamentoRequest);
        dispararEvento(pagamentoEvent);
    }

    @Override
    public void cancelarPagamento(PagamentoRequest pagamentoRequest) {
        PagamentoEvent pagamentoEvent = pagamentoRequestHelper.persistirPagamentoCancelar(pagamentoRequest);
        dispararEvento(pagamentoEvent);
    }

    private void dispararEvento(PagamentoEvent pagamentoEvent) {
        log.info("Publicando evento de pagamento com id de pagamento: {} e id de pedido: {}.", pagamentoEvent.getPagamento().getId().getValue(), pagamentoEvent.getPagamento().getPedidoId().getValue());
        pagamentoEvent.disparar();
    }
}
