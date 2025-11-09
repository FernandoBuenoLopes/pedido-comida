package org.vortxyz.restaurante.domain.entity;

import org.vortxyz.domain.entity.BaseEntity;
import org.vortxyz.domain.valueobject.PedidoAprovacaoStatus;
import org.vortxyz.domain.valueobject.PedidoId;
import org.vortxyz.domain.valueobject.RestauranteId;
import org.vortxyz.restaurante.domain.valueobject.PedidoAprovacaoId;

public class PedidoAprovacao extends BaseEntity<PedidoAprovacaoId> {

    private final RestauranteId restauranteId;
    private final PedidoId pedidoId;
    private final PedidoAprovacaoStatus pedidoAprovacaoStatus;

    private PedidoAprovacao(Builder builder) {
        setId(builder.pedidoAprovacaoId);
        restauranteId = builder.restauranteId;
        pedidoId = builder.pedidoId;
        pedidoAprovacaoStatus = builder.pedidoAprovacaoStatus;
    }

    public static Builder builder() {
        return new Builder();
    }


    public RestauranteId getRestauranteId() {
        return restauranteId;
    }

    public PedidoId getPedidoId() {
        return pedidoId;
    }

    public PedidoAprovacaoStatus getPedidoAprovacaoStatus() {
        return pedidoAprovacaoStatus;
    }

    public static final class Builder {
        private PedidoAprovacaoId pedidoAprovacaoId;
        private RestauranteId restauranteId;
        private PedidoId pedidoId;
        private PedidoAprovacaoStatus pedidoAprovacaoStatus;

        private Builder() {
        }

        public Builder pedidoAprovacaoId(PedidoAprovacaoId val) {
            pedidoAprovacaoId = val;
            return this;
        }

        public Builder restauranteId(RestauranteId val) {
            restauranteId = val;
            return this;
        }

        public Builder pedidoId(PedidoId val) {
            pedidoId = val;
            return this;
        }

        public Builder pedidoAprovacaoStatus(PedidoAprovacaoStatus val) {
            pedidoAprovacaoStatus = val;
            return this;
        }

        public PedidoAprovacao build() {
            return new PedidoAprovacao(this);
        }
    }
}
