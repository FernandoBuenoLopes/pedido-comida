package org.vortxyz.pedido.domain.dto.track;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class PedidoRastrearQuery {
    @NotNull
    private final UUID pedidoRastreamentoId;
}
