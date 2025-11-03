package org.vortxyz.pedido.domain.mapper;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.vortxyz.domain.valueobject.ClienteId;
import org.vortxyz.domain.valueobject.Dinheiro;
import org.vortxyz.domain.valueobject.ProdutoId;
import org.vortxyz.domain.valueobject.RestauranteId;
import org.vortxyz.pedido.domain.dto.create.PedidoCriarCommand;
import org.vortxyz.pedido.domain.dto.create.PedidoCriarResponse;
import org.vortxyz.pedido.domain.dto.create.PedidoEndereco;
import org.vortxyz.pedido.domain.dto.track.PedidoRastrearResponse;
import org.vortxyz.pedido.domain.entity.Pedido;
import org.vortxyz.pedido.domain.entity.PedidoItem;
import org.vortxyz.pedido.domain.entity.Produto;
import org.vortxyz.pedido.domain.entity.Restaurante;
import org.vortxyz.pedido.domain.valueobject.EnderecoEntrega;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PedidoMapper {

    public Restaurante pedidoCriarCommandToRestaurante(PedidoCriarCommand pedidoCriarCommand) {
        return Restaurante.builder()
                .restauranteId(new RestauranteId(pedidoCriarCommand.getRestauranteId()))
                .produtos(pedidoCriarCommand.getItens().stream().map(pedidoItem ->
                        new Produto(new ProdutoId(pedidoItem.getProdutoId())))
                        .collect(Collectors.toList()))
                .build();
    }

    public Pedido pedidoCriarCommandToPedido(PedidoCriarCommand pedidoCriarCommand) {
        return Pedido.builder()
                .clienteId(new ClienteId(pedidoCriarCommand.getClienteId()))
                .restauranteId(new RestauranteId(pedidoCriarCommand.getRestauranteId()))
                .enderecoEntrega(pedidoEnderecoToEndereco(pedidoCriarCommand.getEndereco()))
                .preco(new Dinheiro(pedidoCriarCommand.getPreco().getQuantia()))
                .itens(pedidoItensToPedidoItemEntities(pedidoCriarCommand.getItens()))
                .build();
    }

    private EnderecoEntrega pedidoEnderecoToEndereco(@NotNull PedidoEndereco pedidoEndereco) {
        return new EnderecoEntrega(
                UUID.randomUUID(),
                pedidoEndereco.getRua(),
                pedidoEndereco.getCep(),
                pedidoEndereco.getCidade()
        );
    }

    private List<PedidoItem> pedidoItensToPedidoItemEntities(@NotNull List<org.vortxyz.pedido.domain.dto.create.PedidoItem> pedidoItens) {
        return pedidoItens.stream().map(pedidoItem ->
            PedidoItem.builder()
                    .produto(new Produto(new ProdutoId(pedidoItem.getProdutoId())))
                    .preco(new Dinheiro(pedidoItem.getPreco()))
                    .quantidade(pedidoItem.getQuantidade())
                    .subtotal(new Dinheiro(pedidoItem.getSubTotal()))
                    .build()
        ).collect(Collectors.toList());
    }

    public PedidoCriarResponse pedidoToPedidoCriarResponse(Pedido pedidoSalvo, String mensagem) {
        return PedidoCriarResponse.builder()
                .pedidoRastreamentoId(pedidoSalvo.getRastreamentoId().getValue())
                .pedidoStatus(pedidoSalvo.getPedidoStatus())
                .mensagem(mensagem)
                .build();
    }

    public PedidoRastrearResponse pedidoToPedidoRastrearResponse(Pedido pedido) {
        return PedidoRastrearResponse.builder()
                .pedidoRastreamentoId(pedido.getRastreamentoId().getValue())
                .pedidoStatus(pedido.getPedidoStatus())
                .build();
    }
}
