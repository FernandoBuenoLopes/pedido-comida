package org.vortxyz.restaurante.service.dataaccess.restaurante.adapter;

import org.springframework.stereotype.Component;
import org.vortxyz.restaurante.domain.entity.PedidoAprovacao;
import org.vortxyz.restaurante.service.dataaccess.restaurante.mapper.RestauranteDataAccessMapper;
import org.vortxyz.restaurante.service.dataaccess.restaurante.repository.PedidoAprovacaoJpaRepository;
import org.vortxyz.restaurante.service.domain.ports.output.repository.PedidoAprovacaoRepository;

@Component
public class PedidoAprovacaoRepositoryImpl implements PedidoAprovacaoRepository {

    private final PedidoAprovacaoJpaRepository pedidoAprovacaoJpaRepository;
    private final RestauranteDataAccessMapper restauranteDataAccessMapper;

    public PedidoAprovacaoRepositoryImpl(PedidoAprovacaoJpaRepository pedidoAprovacaoJpaRepository, RestauranteDataAccessMapper restauranteDataAccessMapper) {
        this.pedidoAprovacaoJpaRepository = pedidoAprovacaoJpaRepository;
        this.restauranteDataAccessMapper = restauranteDataAccessMapper;
    }

    @Override
    public PedidoAprovacao save(PedidoAprovacao pedidoAprovacao) {
        return restauranteDataAccessMapper
                .pedidoAprovacaoEntityToProdutoAprovacao(pedidoAprovacaoJpaRepository
                        .save(restauranteDataAccessMapper.pedidoAprovacaoToPedidoAprovacaoEntity(pedidoAprovacao)));
    }
}
