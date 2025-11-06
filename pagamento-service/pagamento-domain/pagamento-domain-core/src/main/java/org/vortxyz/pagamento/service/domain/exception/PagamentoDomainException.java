package org.vortxyz.pagamento.service.domain.exception;

import org.vortxyz.domain.exception.DomainException;

public class PagamentoDomainException extends DomainException {
    public PagamentoDomainException(String message) {
        super(message);
    }

    public PagamentoDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
