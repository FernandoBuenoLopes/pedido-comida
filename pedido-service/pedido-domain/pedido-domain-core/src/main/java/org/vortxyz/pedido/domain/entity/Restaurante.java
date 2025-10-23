package org.vortxyz.pedido.domain.entity;

import org.vortxyz.domain.entity.AggregateRoot;
import org.vortxyz.domain.valueobject.RestauranteId;

import java.util.List;

public class Restaurante extends AggregateRoot<RestauranteId> {
    private final List<Produto> produtos;
    private boolean ativo;

    private Restaurante(Builder builder) {
        super.setId(builder.restauranteId);
        produtos = builder.produtos;
        ativo = builder.ativo;
    }

    public static Builder builder() {
        return new Builder();
    }


    public List<Produto> getProdutos() {
        return produtos;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public static final class Builder {
        private RestauranteId restauranteId;
        private List<Produto> produtos;
        private boolean ativo;

        private Builder() {
        }

        public Builder restauranteId(RestauranteId val) {
            restauranteId = val;
            return this;
        }

        public Builder produtos(List<Produto> val) {
            produtos = val;
            return this;
        }

        public Builder ativo(boolean val) {
            ativo = val;
            return this;
        }

        public Restaurante build() {
            return new Restaurante(this);
        }
    }
}
