package org.vortxyz.pedido.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.vortxyz.pedido.domain.dto.message.RestauranteAprovacaoResponse;
import org.vortxyz.pedido.domain.ports.input.message.listener.restauranteaprovacao.RestauranteAprovacaoMessageListener;

import static org.vortxyz.pedido.domain.entity.Pedido.DELIMITADOR_MENSAGENS_FALHA;

@Slf4j
@Service
@Validated
public class RestauranteAprovacaoMessageListenerImpl implements RestauranteAprovacaoMessageListener {

    private final PedidoAprovacaoSaga pedidoAprovacaoSaga;

    public RestauranteAprovacaoMessageListenerImpl(PedidoAprovacaoSaga pedidoAprovacaoSaga) {
        this.pedidoAprovacaoSaga = pedidoAprovacaoSaga;
    }

    @Override
    public void pedidoAprovado(RestauranteAprovacaoResponse restauranteAprovacaoResponse) {

        pedidoAprovacaoSaga.process(restauranteAprovacaoResponse);
        log.info("Pedido com id: {} foi aprovado.", restauranteAprovacaoResponse.getPedidoId());
    }

    @Override
    public void pedidoRejeitado(RestauranteAprovacaoResponse restauranteAprovacaoResponse) {

        var pedidoCanceladoEvent = pedidoAprovacaoSaga.rollback(restauranteAprovacaoResponse);
        log.info("Pedido com id: {} foi cancelado com a(s) mensagem(s) de erro: {}", restauranteAprovacaoResponse.getPedidoId(), String.join(DELIMITADOR_MENSAGENS_FALHA, restauranteAprovacaoResponse.getMensagensFalha()));
        pedidoCanceladoEvent.disparar();
    }
}
