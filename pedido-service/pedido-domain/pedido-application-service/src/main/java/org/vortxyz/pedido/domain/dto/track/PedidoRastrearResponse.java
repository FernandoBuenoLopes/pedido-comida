package org.vortxyz.pedido.domain.dto.track;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.vortxyz.domain.valueobject.PedidoStatus;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class PedidoRastrearResponse {
    @NotNull
    private final UUID pedidoRastreamentoId;
    @NotNull
    private final PedidoStatus pedidoStatus;
    private final List<String> mensagensFalha;
}
