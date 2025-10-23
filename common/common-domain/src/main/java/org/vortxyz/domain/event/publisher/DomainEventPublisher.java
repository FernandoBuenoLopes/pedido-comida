package org.vortxyz.domain.event.publisher;

import org.vortxyz.domain.event.DomainEvent;

public interface DomainEventPublisher <T extends DomainEvent> {

    void publish(T domainEvent);
}
