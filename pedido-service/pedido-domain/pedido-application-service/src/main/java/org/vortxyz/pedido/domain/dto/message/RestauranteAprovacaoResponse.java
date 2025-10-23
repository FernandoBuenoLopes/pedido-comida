package org.vortxyz.pedido.domain.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.vortxyz.domain.valueobject.PedidoAprovacaoStatus;

import java.time.Instant;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class RestauranteAprovacaoResponse {
    private String id;
    private String sagaId;
    private String pedidoId;
    private String restauranteId;
    private Instant criadoEm;
    private PedidoAprovacaoStatus pedidoAprovacaoStatus;
    private List<String> mensagensFalha;
}
