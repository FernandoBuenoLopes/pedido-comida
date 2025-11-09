package org.vortxyz.restaurante.service.domain.exception;

import org.vortxyz.domain.exception.DomainException;

public class RestauranteApplicationServiceException extends DomainException {

    public RestauranteApplicationServiceException(String message) {
        super(message);
    }

    public RestauranteApplicationServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
