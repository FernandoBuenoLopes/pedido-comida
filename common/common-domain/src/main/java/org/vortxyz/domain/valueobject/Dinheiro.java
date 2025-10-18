package org.vortxyz.domain.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Dinheiro  {
    private final BigDecimal quantia;

    public Dinheiro(BigDecimal quantia) {
        this.quantia = quantia;
    }

    public boolean eMaiorQueZero() {
        return this.quantia != null && this.quantia.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean eMaiorQue(Dinheiro dinheiro) {
        return this.quantia != null && this.quantia.compareTo(dinheiro.getQuantia()) > 0;
    }

    public Dinheiro somar(Dinheiro dinheiro) {
        return new Dinheiro(definirEscala(this.quantia.add(dinheiro.getQuantia())));
    }

    public Dinheiro subtrair(Dinheiro dinheiro) {
        return new Dinheiro(definirEscala((this.quantia.subtract(dinheiro.getQuantia()))));
    }

    public Dinheiro multiplicar(int multiplicador) {
        return new Dinheiro(definirEscala((this.quantia.multiply((new BigDecimal(multiplicador))))));
    }

    public BigDecimal getQuantia() {
        return quantia;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Dinheiro)) return false;
        Dinheiro dinheiro = (Dinheiro) o;
        return Objects.equals(getQuantia(), dinheiro.getQuantia());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getQuantia());
    }

    private BigDecimal definirEscala(BigDecimal entrada) {
        return entrada.setScale(2, RoundingMode.HALF_EVEN);
    }
}
