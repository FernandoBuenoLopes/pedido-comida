package org.vortxyz.pedido.domain.exception;

import org.vortxyz.domain.exception.DomainException;

public class PedidoNaoEncontradoException extends DomainException {
    public PedidoNaoEncontradoException(String message) {
        super(message);
    }

    public PedidoNaoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }
}
