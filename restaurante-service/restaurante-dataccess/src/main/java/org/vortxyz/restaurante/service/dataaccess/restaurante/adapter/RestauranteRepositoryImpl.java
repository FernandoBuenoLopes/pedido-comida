package org.vortxyz.restaurante.service.dataaccess.restaurante.adapter;

import org.springframework.stereotype.Component;
import org.vortxyz.dataaccess.restaurante.entity.RestauranteEntity;
import org.vortxyz.dataaccess.restaurante.repository.RestauranteJpaRepository;
import org.vortxyz.restaurante.domain.entity.Restaurante;
import org.vortxyz.restaurante.service.dataaccess.restaurante.mapper.RestauranteDataAccessMapper;
import org.vortxyz.restaurante.service.domain.ports.output.repository.RestauranteRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class RestauranteRepositoryImpl implements RestauranteRepository {

    private final RestauranteJpaRepository restauranteJpaRepository;
    private final RestauranteDataAccessMapper restauranteDataAccessMapper;

    public RestauranteRepositoryImpl(RestauranteJpaRepository restauranteJpaRepository, RestauranteDataAccessMapper restauranteDataAccessMapper) {
        this.restauranteJpaRepository = restauranteJpaRepository;
        this.restauranteDataAccessMapper = restauranteDataAccessMapper;
    }

    @Override
    public Optional<Restaurante> findRestauranteInformation(Restaurante restaurante) {
        List<UUID> restauranteProdutos = restauranteDataAccessMapper.restauranteToRestauranteProdutos(restaurante);
        Optional<List<RestauranteEntity>> restauranteEntities = restauranteJpaRepository
                .findByRestauranteIdAndProdutoIdIn(restaurante.getId().getValue(), restauranteProdutos);
        return restauranteEntities.map(restauranteDataAccessMapper::restauranteEntityToRestaurante);
    }
}
