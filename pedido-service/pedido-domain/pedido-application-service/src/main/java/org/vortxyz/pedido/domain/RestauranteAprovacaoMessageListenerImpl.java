package org.vortxyz.pedido.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.vortxyz.pedido.domain.dto.message.RestauranteAprovacaoResponse;
import org.vortxyz.pedido.domain.ports.input.message.listener.restauranteaprovacao.RestauranteAprovacaoMessageListener;

@Slf4j
@Service
@Validated
public class RestauranteAprovacaoMessageListenerImpl implements RestauranteAprovacaoMessageListener {
    @Override
    public void pedidoAprovado(RestauranteAprovacaoResponse restauranteAprovacaoResponse) {

    }

    @Override
    public void pedidoRejeitado(RestauranteAprovacaoResponse restauranteAprovacaoResponse) {

    }
}
