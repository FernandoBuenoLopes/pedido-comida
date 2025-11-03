package org.vortxyz.pedido.service.dataaccess.pedido.mapper;

import org.springframework.stereotype.Component;
import org.vortxyz.domain.valueobject.*;
import org.vortxyz.pedido.domain.entity.Pedido;
import org.vortxyz.pedido.domain.entity.PedidoItem;
import org.vortxyz.pedido.domain.entity.Produto;
import org.vortxyz.pedido.domain.valueobject.EnderecoEntrega;
import org.vortxyz.pedido.domain.valueobject.PedidoItemId;
import org.vortxyz.pedido.domain.valueobject.RastreamentoId;
import org.vortxyz.pedido.service.dataaccess.pedido.entity.PedidoEnderecoEntity;
import org.vortxyz.pedido.service.dataaccess.pedido.entity.PedidoEntity;
import org.vortxyz.pedido.service.dataaccess.pedido.entity.PedidoItemEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.vortxyz.pedido.domain.entity.Pedido.DELIMITADOR_MENSAGENS_FALHA;

@Component
public class PedidoDataAccessMapper {

    public PedidoEntity pedidoToPedidoEntity(Pedido pedido) {

        PedidoEntity pedidoEntity = PedidoEntity.builder()
                .id(pedido.getId().getValue())
                .clienteId(pedido.getClienteId().getValue())
                .restauranteId(pedido.getRestauranteId().getValue())
                .rastreamentoId(pedido.getRastreamentoId().getValue())
                .preco(pedido.getPreco().getQuantia())
                .pedidoStatus(pedido.getPedidoStatus())
                .mensagensFalha(pedido.getMensagensFalha() != null ? String.join(DELIMITADOR_MENSAGENS_FALHA, pedido.getMensagensFalha()) : "")
                .endereco(enderecoEntregaToEnderecoEntity(pedido.getEnderecoEntrega()))
                .itens(pedidoItensToPedidoItensEntity(pedido.getItens()))
                .build();
        pedidoEntity.getEndereco().setPedido(pedidoEntity);
        pedidoEntity.getItens().forEach(pedidoItemEntity -> pedidoItemEntity.setPedido(pedidoEntity));
        return pedidoEntity;
    }

    public Pedido pedidoEntityToPedido(PedidoEntity pedidoEntity) {
        return Pedido.builder()
                .pedidoId(new PedidoId(pedidoEntity.getId()))
                .clienteId(new ClienteId((pedidoEntity.getClienteId())))
                .restauranteId(new RestauranteId(pedidoEntity.getRestauranteId()))
                .enderecoEntrega(enderecoEntityToEnderecoEntrega(pedidoEntity.getEndereco()))
                .preco(new Dinheiro(pedidoEntity.getPreco()))
                .itens(pedidoItensEntityToPedidoItens(pedidoEntity.getItens()))
                .rastreamentoId(new RastreamentoId(pedidoEntity.getRastreamentoId()))
                .pedidoStatus(pedidoEntity.getPedidoStatus())
                .mensagensFalha(pedidoEntity.getMensagensFalha().isEmpty() ? new ArrayList<>() :
                        new ArrayList<>(Arrays.asList(pedidoEntity.getMensagensFalha().split(DELIMITADOR_MENSAGENS_FALHA))))
                .build();
    }

    private PedidoEnderecoEntity enderecoEntregaToEnderecoEntity(EnderecoEntrega enderecoEntrega) {
        return PedidoEnderecoEntity.builder()
                .id(enderecoEntrega.getId())
                .rua(enderecoEntrega.getRua())
                .cep(enderecoEntrega.getCep())
                .cidade(enderecoEntrega.getCidade())
                .build();
    }

    private List<PedidoItemEntity> pedidoItensToPedidoItensEntity(List<PedidoItem> itens) {
        return itens.stream()
                .map(pedidoItem -> PedidoItemEntity.builder()
                        .id(pedidoItem.getId().getValue())
                        .produtoId(pedidoItem.getProduto().getId().getValue())
                        .preco(pedidoItem.getPreco().getQuantia())
                        .quantidade(pedidoItem.getQuantidade())
                        .subTotal(pedidoItem.getSubtotal().getQuantia())
                        .build())
                .collect(Collectors.toList());
    }

    private EnderecoEntrega enderecoEntityToEnderecoEntrega(PedidoEnderecoEntity pedidoEnderecoEntity) {
        return new EnderecoEntrega(
          pedidoEnderecoEntity.getId(),
          pedidoEnderecoEntity.getRua(),
          pedidoEnderecoEntity.getCep(),
          pedidoEnderecoEntity.getCidade()
        );
    }

    private List<PedidoItem> pedidoItensEntityToPedidoItens(List<PedidoItemEntity> itens) {
        return itens.stream()
                .map(pedidoItemEntity -> PedidoItem.builder()
                        .id(new PedidoItemId(pedidoItemEntity.getId()))
                        .produto(new Produto(new ProdutoId(pedidoItemEntity.getProdutoId())))
                        .preco(new Dinheiro(pedidoItemEntity.getPreco()))
                        .quantidade(pedidoItemEntity.getQuantidade())
                        .subtotal(new Dinheiro(pedidoItemEntity.getSubTotal()))
                        .build())
                .collect(Collectors.toList());
    }
}
