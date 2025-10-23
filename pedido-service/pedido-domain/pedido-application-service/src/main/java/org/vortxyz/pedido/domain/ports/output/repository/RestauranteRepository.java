package org.vortxyz.pedido.domain.ports.output.repository;

import org.vortxyz.pedido.domain.entity.Restaurante;

import java.util.Optional;

public interface RestauranteRepository {

    Optional<Restaurante> encontrarRestauranteInformacao(Restaurante restaurante);


}
