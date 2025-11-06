package org.vortxyz.pagamento.service.domain.entity;

import org.vortxyz.domain.entity.AggregateRoot;
import org.vortxyz.domain.valueobject.ClienteId;
import org.vortxyz.domain.valueobject.Dinheiro;
import org.vortxyz.domain.valueobject.PagamentoStatus;
import org.vortxyz.domain.valueobject.PedidoId;
import org.vortxyz.pagamento.service.domain.valueobject.PagamentoId;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static org.vortxyz.domain.DomainConstants.FUSO_HORARIO;

public class Pagamento extends AggregateRoot<PagamentoId> {

    private final PedidoId pedidoId;
    private final ClienteId clienteId;
    private final Dinheiro preco;

    private PagamentoStatus pagamentoStatus;
    private ZonedDateTime criadoEm;

    public void inicializarPagamento() {
        setId(new PagamentoId(UUID.randomUUID()));
        criadoEm = ZonedDateTime.now(ZoneId.of(FUSO_HORARIO));
    }

    public void validarPagamento(List<String> mensagens_falha) {
        if (preco == null || !preco.eMaiorQueZero()) {
            mensagens_falha.add("Preço total deve ser maior que zero.");
        }
    }

    public void atualizarStatus(PagamentoStatus pagamentoStatus) {
        this.pagamentoStatus = pagamentoStatus;
    }

    private Pagamento(Builder builder) {
        setId(builder.pagamentoId);
        pedidoId = builder.pedidoId;
        clienteId = builder.clienteId;
        preco = builder.preco;
        pagamentoStatus = builder.pagamentoStatus;
        criadoEm = builder.criadoEm;
    }

    public static Builder builder() {
        return new Builder();
    }


    public PedidoId getPedidoId() {
        return pedidoId;
    }

    public ClienteId getClienteId() {
        return clienteId;
    }

    public Dinheiro getPreco() {
        return preco;
    }

    public PagamentoStatus getPagamentoStatus() {
        return pagamentoStatus;
    }

    public ZonedDateTime getCriadoEm() {
        return criadoEm;
    }

    public static final class Builder {
        private PagamentoId pagamentoId;
        private PedidoId pedidoId;
        private ClienteId clienteId;
        private Dinheiro preco;
        private PagamentoStatus pagamentoStatus;
        private ZonedDateTime criadoEm;

        private Builder() {
        }

        public Builder id(PagamentoId val) {
            pagamentoId = val;
            return this;
        }

        public Builder pedidoId(PedidoId val) {
            pedidoId = val;
            return this;
        }

        public Builder clienteId(ClienteId val) {
            clienteId = val;
            return this;
        }

        public Builder preco(Dinheiro val) {
            preco = val;
            return this;
        }

        public Builder pagamentoStatus(PagamentoStatus val) {
            pagamentoStatus = val;
            return this;
        }

        public Builder criadoEm(ZonedDateTime val) {
            criadoEm = val;
            return this;
        }

        public Pagamento build() {
            return new Pagamento(this);
        }
    }
}

/*
* pedidoId
* clienteId
* preco
* pagamentoStatus
* criadoEm
*
* */