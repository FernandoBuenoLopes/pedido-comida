package org.vortxyz.pedido.domain.dto.create;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.vortxyz.domain.valueobject.PedidoStatus;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class PedidoCriarResponse {
    @NotNull
    private final UUID pedidoRastreamentoId;
    @NotNull
    private final PedidoStatus pedidoStatus;
    @NotNull
    private final String mensagem;
}
