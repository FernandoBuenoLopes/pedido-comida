package org.vortxyz.restaurante.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.vortxyz.restaurante.domain.event.PedidoAprovacaoEvent;
import org.vortxyz.restaurante.service.domain.dto.RestauranteAprovacaoRequest;
import org.vortxyz.restaurante.service.domain.ports.input.message.listener.RestauranteAprovacaoRequestMessageListener;

@Slf4j
@Service
public class RestauranteAprovacaoRequestMessageListenerImpl implements RestauranteAprovacaoRequestMessageListener {

    private final RestauranteAprovacaoRequestHelper restauranteAprovacaoRequestHelper;

    public RestauranteAprovacaoRequestMessageListenerImpl(RestauranteAprovacaoRequestHelper restauranteAprovacaoRequestHelper) {
        this.restauranteAprovacaoRequestHelper = restauranteAprovacaoRequestHelper;
    }

    @Override
    public void pedidoAprovar(RestauranteAprovacaoRequest restauranteAprovacaoRequest) {
        PedidoAprovacaoEvent pedidoAprovacaoEvent = restauranteAprovacaoRequestHelper.persistPedidoAprovacao(restauranteAprovacaoRequest);
        pedidoAprovacaoEvent.disparar();
    }
}