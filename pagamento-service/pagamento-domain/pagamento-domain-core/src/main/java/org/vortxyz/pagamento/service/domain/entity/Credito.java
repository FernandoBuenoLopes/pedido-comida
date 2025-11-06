package org.vortxyz.pagamento.service.domain.entity;

import org.vortxyz.domain.entity.BaseEntity;
import org.vortxyz.domain.valueobject.ClienteId;
import org.vortxyz.domain.valueobject.Dinheiro;
import org.vortxyz.pagamento.service.domain.valueobject.CreditoId;

public class Credito extends BaseEntity<CreditoId> {

    private final ClienteId clienteId;

    private final Dinheiro creditoQuantiaTotal;

    private Credito(Builder builder) {
        setId(builder.creditoId);
        clienteId = builder.clienteId;
        creditoQuantiaTotal = builder.creditoQuantiaTotal;
    }

    public void adicionarCredito(Dinheiro quantia) {
        this.creditoQuantiaTotal.somar(quantia);
    }

    public void subtrairCredito(Dinheiro quantia) {
        this.creditoQuantiaTotal.subtrair(quantia);
    }

    public static Builder builder() {
        return new Builder();
    }


    public ClienteId getClienteId() {
        return clienteId;
    }

    public Dinheiro getCreditoQuantiaTotal() {
        return creditoQuantiaTotal;
    }

    public static final class Builder {
        private CreditoId creditoId;
        private ClienteId clienteId;
        private Dinheiro creditoQuantiaTotal;

        private Builder() {
        }

        public Builder id(CreditoId val) {
            creditoId = val;
            return this;
        }

        public Builder clienteId(ClienteId val) {
            clienteId = val;
            return this;
        }

        public Builder creditoQuantiaTotal(Dinheiro val) {
            creditoQuantiaTotal = val;
            return this;
        }

        public Credito build() {
            return new Credito(this);
        }
    }
}
