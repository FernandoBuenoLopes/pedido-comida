package org.vortxyz.restaurante.domain.entity;

import org.vortxyz.domain.entity.BaseEntity;
import org.vortxyz.domain.valueobject.Dinheiro;
import org.vortxyz.domain.valueobject.PedidoId;
import org.vortxyz.domain.valueobject.PedidoStatus;

import java.util.List;

public class PedidoDetalhe extends BaseEntity<PedidoId> {

    private PedidoStatus pedidoStatus;
    private Dinheiro quantiaTotal;
    private final List<Produto> produtos;

    private PedidoDetalhe(Builder builder) {
        setId(builder.id);
        pedidoStatus = builder.pedidoStatus;
        quantiaTotal = builder.quantiaTotal;
        produtos = builder.produtos;
    }

    public static Builder builder() {
        return new Builder();
    }


    public PedidoStatus getPedidoStatus() {
        return pedidoStatus;
    }

    public Dinheiro getQuantiaTotal() {
        return quantiaTotal;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public static final class Builder {
        private PedidoId id;
        private PedidoStatus pedidoStatus;
        private Dinheiro quantiaTotal;
        private List<Produto> produtos;

        private Builder() {
        }

        public Builder id(PedidoId val) {
            id = val;
            return this;
        }

        public Builder pedidoStatus(PedidoStatus val) {
            pedidoStatus = val;
            return this;
        }

        public Builder quantiaTotal(Dinheiro val) {
            quantiaTotal = val;
            return this;
        }

        public Builder produtos(List<Produto> val) {
            produtos = val;
            return this;
        }

        public PedidoDetalhe build() {
            return new PedidoDetalhe(this);
        }
    }
}
