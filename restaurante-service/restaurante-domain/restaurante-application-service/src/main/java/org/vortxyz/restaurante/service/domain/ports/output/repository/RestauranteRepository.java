package org.vortxyz.restaurante.service.domain.ports.output.repository;

import org.vortxyz.restaurante.domain.entity.Restaurante;

import java.util.Optional;

public interface RestauranteRepository {

    Optional<Restaurante> findRestauranteInformation(Restaurante restaurante);
}
