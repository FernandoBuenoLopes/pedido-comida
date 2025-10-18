package org.vortxyz.pedido.domain.entity;

import org.vortxyz.domain.entity.BaseEntity;
import org.vortxyz.domain.valueobject.Dinheiro;
import org.vortxyz.domain.valueobject.PedidoId;
import org.vortxyz.pedido.domain.valueobject.PedidoItemId;

public class PedidoItem extends BaseEntity<PedidoItemId> {
    private PedidoId pedidoId;
    private final Produto produto;
    private final int quantidade;
    private final Dinheiro preco;
    private final Dinheiro subtotal;

    private PedidoItem(Builder builder) {
        super.setId(builder.pedidoItemId);
        produto = builder.produto;
        quantidade = builder.quantidade;
        preco = builder.preco;
        subtotal = builder.subtotal;
    }

    public static Builder builder() {
        return new Builder();
    }


    public PedidoId getPedidoId() {
        return pedidoId;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public Dinheiro getPreco() {
        return preco;
    }

    public Dinheiro getSubtotal() {
        return subtotal;
    }


    public static final class Builder {
        private PedidoItemId pedidoItemId;
        private Produto produto;
        private int quantidade;
        private Dinheiro preco;
        private Dinheiro subtotal;

        private Builder() {
        }

        public Builder id(PedidoItemId val) {
            pedidoItemId = val;
            return this;
        }

        public Builder produto(Produto val) {
            produto = val;
            return this;
        }

        public Builder quantidade(int val) {
            quantidade = val;
            return this;
        }

        public Builder preco(Dinheiro val) {
            preco = val;
            return this;
        }

        public Builder subtotal(Dinheiro val) {
            subtotal = val;
            return this;
        }

        public PedidoItem build() {
            return new PedidoItem(this);
        }
    }
}
