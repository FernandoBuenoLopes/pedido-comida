package org.vortxyz.domain.event;

public interface DomainEvent<T> {
    void disparar();
}
