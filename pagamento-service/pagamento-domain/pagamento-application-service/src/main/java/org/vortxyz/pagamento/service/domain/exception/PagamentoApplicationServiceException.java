package org.vortxyz.pagamento.service.domain.exception;

import org.vortxyz.domain.exception.DomainException;

public class PagamentoApplicationServiceException extends DomainException {

    public PagamentoApplicationServiceException(String message) {
        super(message);
    }

    public PagamentoApplicationServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
