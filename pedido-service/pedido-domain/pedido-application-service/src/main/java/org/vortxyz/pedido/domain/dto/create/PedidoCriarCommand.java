package org.vortxyz.pedido.domain.dto.create;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.vortxyz.domain.valueobject.Dinheiro;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class PedidoCriarCommand {
    @NotNull
    private final UUID clienteId;
    @NotNull
    private final UUID restauranteId;
    @NotNull
    private final List<PedidoItem> itens;
    @NotNull
    private final Dinheiro preco;
    @NotNull
    private final PedidoEndereco endereco;
}
