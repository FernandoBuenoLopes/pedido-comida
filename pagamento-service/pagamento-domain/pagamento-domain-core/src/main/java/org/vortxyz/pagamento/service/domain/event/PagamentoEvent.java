package org.vortxyz.pagamento.service.domain.event;

import org.vortxyz.domain.event.DomainEvent;
import org.vortxyz.pagamento.service.domain.entity.Pagamento;

import java.time.ZonedDateTime;
import java.util.List;

public abstract class PagamentoEvent implements DomainEvent<Pagamento> {

    private final Pagamento pagamento;
    private final ZonedDateTime criadoEm;
    private final List<String> mensagensFalha;

    protected PagamentoEvent(Pagamento pagamento, ZonedDateTime criadoEm, List<String> mensagensFalha) {
        this.pagamento = pagamento;
        this.criadoEm = criadoEm;
        this.mensagensFalha = mensagensFalha;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public ZonedDateTime getCriadoEm() {
        return criadoEm;
    }

    public List<String> getMensagensFalha() {
        return mensagensFalha;
    }
}
