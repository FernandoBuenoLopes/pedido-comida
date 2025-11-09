package org.vortxyz.restaurante.domain.entity;

import org.vortxyz.domain.entity.BaseEntity;
import org.vortxyz.domain.valueobject.Dinheiro;
import org.vortxyz.domain.valueobject.ProdutoId;

public class Produto extends BaseEntity<ProdutoId> {

    private String nome;
    private Dinheiro preco;
    private final int quantidade;
    private boolean disponivel;

    public void atualizarComNomePrecoDisponibilidadeConfirmados(String nome, Dinheiro preco, boolean disponivel) {
        this.nome = nome;
        this.preco = preco;
        this.disponivel = disponivel;
    }

    private Produto(Builder builder) {
        setId(builder.id);
        nome = builder.nome;
        preco = builder.preco;
        quantidade = builder.quantidade;
        disponivel = builder.disponivel;
    }

    public static Builder builder() {
        return new Builder();
    }


    public String getNome() {
        return nome;
    }

    public Dinheiro getPreco() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public static final class Builder {
        private ProdutoId id;
        private String nome;
        private Dinheiro preco;
        private int quantidade;
        private boolean disponivel;

        private Builder() {
        }

        public Builder id(ProdutoId val) {
            id = val;
            return this;
        }

        public Builder nome(String val) {
            nome = val;
            return this;
        }

        public Builder preco(Dinheiro val) {
            preco = val;
            return this;
        }

        public Builder quantidade(int val) {
            quantidade = val;
            return this;
        }

        public Builder disponivel(boolean val) {
            disponivel = val;
            return this;
        }

        public Produto build() {
            return new Produto(this);
        }
    }
}
