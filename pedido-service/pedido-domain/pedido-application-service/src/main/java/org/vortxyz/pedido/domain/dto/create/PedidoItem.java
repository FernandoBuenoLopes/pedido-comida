package org.vortxyz.pedido.domain.dto.create;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class PedidoItem {
    @NotNull
    private final UUID produtoId;
    @NotNull
    private final Integer quantidade;
    @NotNull
    private final BigDecimal preco;
    @NotNull
    private final BigDecimal subTotal;
}
