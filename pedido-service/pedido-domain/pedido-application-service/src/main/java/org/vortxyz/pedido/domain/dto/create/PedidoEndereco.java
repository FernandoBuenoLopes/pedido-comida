package org.vortxyz.pedido.domain.dto.create;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PedidoEndereco {
    @NotNull
    @Max(value = 50)
    private final String rua;
    @NotNull
    @Max(value = 10)
    private final String cep;
    @NotNull
    @Max(value = 50)
    private final String cidade;
}
