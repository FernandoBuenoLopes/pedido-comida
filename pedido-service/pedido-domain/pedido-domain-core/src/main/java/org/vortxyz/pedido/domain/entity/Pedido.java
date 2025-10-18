package org.vortxyz.pedido.domain.entity;

import org.vortxyz.domain.entity.AggregateRoot;
import org.vortxyz.domain.valueobject.*;
import org.vortxyz.pedido.domain.exception.PedidoDomainException;
import org.vortxyz.pedido.domain.valueobject.Endereco;
import org.vortxyz.pedido.domain.valueobject.PedidoItemId;
import org.vortxyz.pedido.domain.valueobject.RastreamentoId;

import java.util.List;
import java.util.UUID;

public class Pedido extends AggregateRoot<PedidoId> {
    private final ClienteId clienteId;
    private final RestauranteId restauranteId;
    private final Endereco enderecoEntrega;
    private final Dinheiro preco;
    private final List<PedidoItem> itens;

    private RastreamentoId rastreamentoId;
    private PedidoStatus pedidoStatus;
    private List<String> mensagensFalha;

    public void inicializarPedido() {
        setId(new PedidoId(UUID.randomUUID()));
        rastreamentoId = new RastreamentoId(UUID.randomUUID());
        pedidoStatus = PedidoStatus.PENDENTE;
        inicializarPedidoItens();
    }

    public void validarPedido() {
        validarInicioPedido();
        validarPrecoTotal();
        validarPrecoItens();
    }

    public void pagar() {
        if (pedidoStatus == null || pedidoStatus != PedidoStatus.PENDENTE) {
            throw new PedidoDomainException("Status do pedido inválido para pagamento.");
        }
        pedidoStatus = PedidoStatus.PAGO;
    }

    public void aprovar() {
        if (pedidoStatus == null || pedidoStatus != PedidoStatus.PAGO) {
            throw new PedidoDomainException("Status do pedido inválido para aprovação.");
        }
        pedidoStatus = PedidoStatus.APROVADO;
    }

    public void iniciarCancelamento(List<String> mensagensFalha) {
        if (pedidoStatus == null || pedidoStatus != PedidoStatus.PAGO) {
            throw new PedidoDomainException("Status do pedido inválido para iniciar cancelamento.");
        }
        pedidoStatus = PedidoStatus.CANCELANDO;
        atualizarMensagensFalha(mensagensFalha);
    }

    public void cancelar(List<String> mensagensFalha) {
        if (pedidoStatus == null ||
                !(pedidoStatus == PedidoStatus.CANCELANDO ||
                pedidoStatus == PedidoStatus.PENDENTE)) {
            throw new PedidoDomainException("Status do pedido inválido para concluir cancelamento.");
        }
        pedidoStatus = PedidoStatus.CANCELADO;
        atualizarMensagensFalha(mensagensFalha);
    }

    private void atualizarMensagensFalha(List<String> mensagensFalha) {
        if (mensagensFalha != null) {
            if (this.mensagensFalha != null) {
                this.mensagensFalha.addAll(mensagensFalha.stream().filter(mensagem -> !mensagem.isEmpty()).toList());
            } else {
                this.mensagensFalha = mensagensFalha;
            }
        }
    }

    private void validarInicioPedido() {
        if (pedidoStatus != null || getId() != null) {
            throw new PedidoDomainException("Pedido com status de inicialização inválido.");
        }
    }

    private void validarPrecoTotal() {
        if (preco == null || !preco.eMaiorQueZero()) {
            throw new PedidoDomainException("Preço total inválido.");
        }
    }

    private void validarPrecoItens() {
        Dinheiro pedidoItensTotal = itens.stream().map(pedidoItem -> {
            validarPrecoItem(pedidoItem);
            return pedidoItem.getSubtotal();
        }).reduce(Dinheiro.ZERO, Dinheiro::somar);

        if (!preco.equals(pedidoItensTotal)) {
            throw new PedidoDomainException("Preço total: R$" + preco.getQuantia()
                    + " não é igual ao preço total dos itens do pedido: R$" + pedidoItensTotal.getQuantia() + ".");
        }
    }

    private void validarPrecoItem(PedidoItem pedidoItem) {
        if (!pedidoItem.isPrecoValido()) {
            throw new PedidoDomainException("Preço do item no pedido: R$" + pedidoItem.getPreco().getQuantia()
                    + " inválido para o produto: " + pedidoItem.getProduto().getNome());
        }
    }

    private void inicializarPedidoItens() {
        long itemId = 1;
        for (PedidoItem pedidoItem : itens) {
            pedidoItem.inicializarPedidoItem(super.getId(), new PedidoItemId(itemId++));
        }
    }

    private Pedido(Builder builder) {
        super.setId(builder.pedidoId);
        clienteId = builder.clienteId;
        restauranteId = builder.restauranteId;
        enderecoEntrega = builder.enderecoEntrega;
        preco = builder.preco;
        itens = builder.itens;
        rastreamentoId = builder.rastreamentoId;
        pedidoStatus = builder.pedidoStatus;
        mensagensFalha = builder.mensagensFalha;
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

    public List<String> getMensagensFalha() {
        return mensagensFalha;
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
        private List<String> mensagensFalha;

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

        public Builder mensagensFalha(List<String> val) {
            mensagensFalha = val;
            return this;
        }

        public Pedido build() {
            return new Pedido(this);
        }
    }
}
