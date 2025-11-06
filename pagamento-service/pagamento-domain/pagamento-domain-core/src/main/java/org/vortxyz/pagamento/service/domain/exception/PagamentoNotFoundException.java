package org.vortxyz.pagamento.service.domain.exception;

import org.vortxyz.domain.exception.DomainException;

public class PagamentoNotFoundException extends DomainException {

    public PagamentoNotFoundException(String message) {
        super(message);
    }

    public PagamentoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
