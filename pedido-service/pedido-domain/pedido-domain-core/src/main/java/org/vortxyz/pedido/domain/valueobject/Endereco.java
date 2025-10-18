package org.vortxyz.pedido.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

public class Endereco {
    private final UUID id;
    private final String rua;
    private final String cep;
    private final String cidade;

    public Endereco(UUID id, String rua, String cep, String cidade) {
        this.id = id;
        this.rua = rua;
        this.cep = cep;
        this.cidade = cidade;
    }

    public UUID getId() {
        return id;
    }

    public String getRua() {
        return rua;
    }

    public String getCep() {
        return cep;
    }

    public String getCidade() {
        return cidade;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Endereco endereco)) return false;
        return Objects.equals(getRua(), endereco.getRua()) && Objects.equals(getCep(), endereco.getCep()) && Objects.equals(getCidade(), endereco.getCidade());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRua(), getCep(), getCidade());
    }
}
