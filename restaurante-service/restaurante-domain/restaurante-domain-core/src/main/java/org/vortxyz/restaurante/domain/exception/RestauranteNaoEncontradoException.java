package org.vortxyz.restaurante.domain.exception;

import org.vortxyz.domain.exception.DomainException;

public class RestauranteNaoEncontradoException extends DomainException {

    public RestauranteNaoEncontradoException(String message) {
        super(message);
    }

    public RestauranteNaoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }
}
