package org.vortxyz.restaurante.service.domain.ports.input.message.listener;

import org.vortxyz.restaurante.service.domain.dto.RestauranteAprovacaoRequest;

public interface RestauranteAprovacaoRequestMessageListener {

    void pedidoAprovar(RestauranteAprovacaoRequest restauranteAprovacaoRequest);
}
