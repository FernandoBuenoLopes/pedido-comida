package org.vortxyz.pedido.domain.exception;

import org.vortxyz.domain.exception.DomainException;

public class PedidoDomainException extends DomainException {
    public PedidoDomainException(String message) {
        super(message);
    }

    public PedidoDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
