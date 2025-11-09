package org.vortxyz.restaurante.domain.entity;

import org.vortxyz.domain.entity.AggregateRoot;
import org.vortxyz.domain.valueobject.Dinheiro;
import org.vortxyz.domain.valueobject.PedidoAprovacaoStatus;
import org.vortxyz.domain.valueobject.PedidoStatus;
import org.vortxyz.domain.valueobject.RestauranteId;
import org.vortxyz.restaurante.domain.valueobject.PedidoAprovacaoId;

import java.util.List;
import java.util.UUID;

public class Restaurante extends AggregateRoot<RestauranteId> {

    private PedidoAprovacao pedidoAprovacao;
    private boolean ativo;
    private final PedidoDetalhe pedidoDetalhe;

    public void validarPedido(List<String> mensagensFalha) {
        if (pedidoDetalhe.getPedidoStatus() != PedidoStatus.PAGO) {
            mensagensFalha.add("Pagamento não completo para pedido com id: " + pedidoDetalhe.getId() + ".");
        }
        Dinheiro quantiaTotal = pedidoDetalhe.getProdutos().stream().map(produto -> {
            if (!produto.isDisponivel()) {
                mensagensFalha.add("Produto com id: " + produto.getId().getValue() + " não está disponível.");
            }
            return produto.getPreco().multiplicar(produto.getQuantidade());
        }).reduce(Dinheiro.ZERO, Dinheiro::somar);

        if (!quantiaTotal.equals(pedidoDetalhe.getQuantiaTotal())) {
            mensagensFalha.add("Valor total do pedido comn id: " + pedidoDetalhe.getId() + "não é igual o valor da soma dos produtos.");
        }
    }

    public void criarPedidoAprovacao(PedidoAprovacaoStatus pedidoAprovacaoStatus) {
        this.pedidoAprovacao = PedidoAprovacao.builder()
                .pedidoAprovacaoId(new PedidoAprovacaoId(UUID.randomUUID()))
                .restauranteId(this.getId())
                .pedidoId(this.pedidoDetalhe.getId())
                .pedidoAprovacaoStatus(pedidoAprovacaoStatus)
                .build();
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    private Restaurante(Builder builder) {
        setId(builder.id);
        pedidoAprovacao = builder.pedidoAprovacao;
        ativo = builder.ativo;
        pedidoDetalhe = builder.pedidoDetalhe;
    }

    public static Builder builder() {
        return new Builder();
    }


    public PedidoAprovacao getPedidoAprovacao() {
        return pedidoAprovacao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public PedidoDetalhe getPedidoDetalhe() {
        return pedidoDetalhe;
    }

    public static final class Builder {
        private RestauranteId id;
        private PedidoAprovacao pedidoAprovacao;
        private boolean ativo;
        private PedidoDetalhe pedidoDetalhe;

        private Builder() {
        }

        public Builder id(RestauranteId val) {
            id = val;
            return this;
        }

        public Builder pedidoAprovacao(PedidoAprovacao val) {
            pedidoAprovacao = val;
            return this;
        }

        public Builder ativo(boolean val) {
            ativo = val;
            return this;
        }

        public Builder pedidoDetalhe(PedidoDetalhe val) {
            pedidoDetalhe = val;
            return this;
        }

        public Restaurante build() {
            return new Restaurante(this);
        }
    }
}
