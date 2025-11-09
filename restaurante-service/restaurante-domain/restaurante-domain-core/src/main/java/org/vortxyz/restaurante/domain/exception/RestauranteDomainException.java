package org.vortxyz.restaurante.domain.exception;

import org.vortxyz.domain.exception.DomainException;

public class RestauranteDomainException extends DomainException {

    public RestauranteDomainException(String message) {
        super(message);
    }

    public RestauranteDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
