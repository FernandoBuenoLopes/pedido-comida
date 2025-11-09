package org.vortxyz.pedido.service.dataaccess.restaurante.adapter;

import org.springframework.stereotype.Component;
import org.vortxyz.dataaccess.restaurante.entity.RestauranteEntity;
import org.vortxyz.dataaccess.restaurante.repository.RestauranteJpaRepository;
import org.vortxyz.pedido.domain.entity.Restaurante;
import org.vortxyz.pedido.domain.ports.output.repository.RestauranteRepository;
import org.vortxyz.pedido.service.dataaccess.restaurante.mapper.RestauranteDataAccesMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class RestauranteRepositoryImpl implements RestauranteRepository {

    private final RestauranteJpaRepository restauranteJpaRepository;
    private final RestauranteDataAccesMapper restauranteDataAccesMapper;

    public RestauranteRepositoryImpl(RestauranteJpaRepository restauranteJpaRepository, RestauranteDataAccesMapper restauranteDataAccesMapper) {
        this.restauranteJpaRepository = restauranteJpaRepository;
        this.restauranteDataAccesMapper = restauranteDataAccesMapper;
    }

    @Override
    public Optional<Restaurante> encontrarRestauranteInformacao(Restaurante restaurante) {

        List<UUID> restauranteProdutos = restauranteDataAccesMapper.restauranteToRestauranteProdutos(restaurante);
        Optional<List<RestauranteEntity>> restauranteEntities = restauranteJpaRepository.findByRestauranteIdAndProdutoIdIn(restaurante.getId().getValue(), restauranteProdutos);
        return restauranteEntities.map(restauranteDataAccesMapper::restauranteEntityToRestaurante);
    }
}
