package org.vortxyz.pagamento.service.domain.entity;

import org.vortxyz.domain.entity.BaseEntity;
import org.vortxyz.domain.valueobject.ClienteId;
import org.vortxyz.domain.valueobject.Dinheiro;
import org.vortxyz.pagamento.service.domain.valueobject.CreditoHistoricoId;
import org.vortxyz.pagamento.service.domain.valueobject.TipoTransacao;

public class CreditoHistorico extends BaseEntity<CreditoHistoricoId> {

    private final ClienteId clienteId;
    private final Dinheiro quantia;
    private final TipoTransacao tipoTransacao;

    public CreditoHistorico(ClienteId clienteId, Dinheiro quantia, TipoTransacao tipoTransacao) {
        this.clienteId = clienteId;
        this.quantia = quantia;
        this.tipoTransacao = tipoTransacao;
    }

    private CreditoHistorico(Builder builder) {
        setId(builder.id);
        clienteId = builder.clienteId;
        quantia = builder.quantia;
        tipoTransacao = builder.tipoTransacao;
    }

    public static Builder builder() {
        return new Builder();
    }


    public ClienteId getClienteId() {
        return clienteId;
    }

    public Dinheiro getQuantia() {
        return quantia;
    }

    public TipoTransacao getTipoTransacao() {
        return tipoTransacao;
    }

    public static final class Builder {
        private CreditoHistoricoId id;
        private ClienteId clienteId;
        private Dinheiro quantia;
        private TipoTransacao tipoTransacao;

        private Builder() {
        }

        public Builder id(CreditoHistoricoId val) {
            id = val;
            return this;
        }

        public Builder clienteId(ClienteId val) {
            clienteId = val;
            return this;
        }

        public Builder quantia(Dinheiro val) {
            quantia = val;
            return this;
        }

        public Builder tipoTransacao(TipoTransacao val) {
            tipoTransacao = val;
            return this;
        }

        public CreditoHistorico build() {
            return new CreditoHistorico(this);
        }
    }
}
