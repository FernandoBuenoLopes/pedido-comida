package org.vortxyz.dataaccess.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vortxyz.dataaccess.restaurante.entity.RestauranteEntity;
import org.vortxyz.dataaccess.restaurante.entity.RestauranteEntityId;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RestauranteJpaRepository extends JpaRepository<RestauranteEntity, RestauranteEntityId> {

    Optional<List<RestauranteEntity>> findByRestauranteIdAndProdutoIdIn(UUID restauranteId, List<UUID> produtosId);
}
