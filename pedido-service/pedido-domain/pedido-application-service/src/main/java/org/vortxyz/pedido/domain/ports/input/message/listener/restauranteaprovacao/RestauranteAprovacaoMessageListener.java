package org.vortxyz.pedido.domain.ports.input.message.listener.restauranteaprovacao;

import org.vortxyz.pedido.domain.dto.message.RestauranteAprovacaoResponse;

public interface RestauranteAprovacaoMessageListener {

    void pedidoAprovado(RestauranteAprovacaoResponse restauranteAprovacaoResponse);

    void pedidoRejeitado(RestauranteAprovacaoResponse restauranteAprovacaoResponse);
}
