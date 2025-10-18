package org.vortxyz.pedido.domain.entity;

import org.vortxyz.domain.entity.BaseEntity;
import org.vortxyz.domain.valueobject.Dinheiro;
import org.vortxyz.domain.valueobject.ProdutoId;

import java.util.Objects;

public class Produto extends BaseEntity<ProdutoId> {
    private String nome;
    private Dinheiro preco;

    public Produto(ProdutoId produtoId, String nome, Dinheiro preco) {
        super.setId(produtoId);
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
