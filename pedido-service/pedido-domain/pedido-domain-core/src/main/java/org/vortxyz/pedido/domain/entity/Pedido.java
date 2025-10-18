package org.vortxyz.pedido.domain.entity;

import org.vortxyz.domain.entity.AggregateRoot;
import org.vortxyz.domain.valueobject.*;
import org.vortxyz.pedido.domain.valueobject.Endereco;
import org.vortxyz.pedido.domain.valueobject.RastreamentoId;

import java.util.List;

public class Pedido extends AggregateRoot<PedidoId> {
    private final ClienteId clienteId;
    private final RestauranteId restauranteId;
    private final Endereco enderecoEntrega;
    private final Dinheiro preco;
    private final List<PedidoItem> itens;

    private RastreamentoId rastreamentoId;
    private PedidoStatus pedidoStatus;
    private List<String> falhaMensagens;

    private Pedido(Builder builder) {
        super.setId(builder.pedidoId);
        clienteId = builder.clienteId;
        restauranteId = builder.restauranteId;
        enderecoEntrega = builder.enderecoEntrega;
        preco = builder.preco;
        itens = builder.itens;
        rastreamentoId = builder.rastreamentoId;
        pedidoStatus = builder.pedidoStatus;
        falhaMensagens = builder.falhaMensagens;
    }

    public static Builder builder() {
        return new Builder();
    }

    public ClienteId getClienteId() {
        return clienteId;
    }

    public RestauranteId getRestauranteId() {
        return restauranteId;
    }

    public Endereco getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public Dinheiro getPreco() {
        return preco;
    }

    public List<PedidoItem> getItens() {
        return itens;
    }

    public RastreamentoId getRastreamentoId() {
        return rastreamentoId;
    }

    public PedidoStatus getPedidoStatus() {
        return pedidoStatus;
    }

    public List<String> getFalhaMensagens() {
        return falhaMensagens;
    }

    public static final class Builder {
        private PedidoId pedidoId;
        private ClienteId clienteId;
        private RestauranteId restauranteId;
        private Endereco enderecoEntrega;
        private Dinheiro preco;
        private List<PedidoItem> itens;
        private RastreamentoId rastreamentoId;
        private PedidoStatus pedidoStatus;
        private List<String> falhaMensagens;

        private Builder() {
        }

        public Builder pedidoId(PedidoId val) {
            pedidoId = val;
            return this;
        }

        public Builder clienteId(ClienteId val) {
            clienteId = val;
            return this;
        }

        public Builder restauranteId(RestauranteId val) {
            restauranteId = val;
            return this;
        }

        public Builder enderecoEntrega(Endereco val) {
            enderecoEntrega = val;
            return this;
        }

        public Builder preco(Dinheiro val) {
            preco = val;
            return this;
        }

        public Builder itens(List<PedidoItem> val) {
            itens = val;
            return this;
        }

        public Builder rastreamentoId(RastreamentoId val) {
            rastreamentoId = val;
            return this;
        }

        public Builder pedidoStatus(PedidoStatus val) {
            pedidoStatus = val;
            return this;
        }

        public Builder falhaMensagens(List<String> val) {
            falhaMensagens = val;
            return this;
        }

        public Pedido build() {
            return new Pedido(this);
        }
    }
}
