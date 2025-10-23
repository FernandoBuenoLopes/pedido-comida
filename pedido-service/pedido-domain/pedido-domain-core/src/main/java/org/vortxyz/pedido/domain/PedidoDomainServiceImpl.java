package org.vortxyz.pedido.domain;

import lombok.extern.slf4j.Slf4j;
import org.vortxyz.pedido.domain.entity.Pedido;
import org.vortxyz.pedido.domain.entity.Produto;
import org.vortxyz.pedido.domain.entity.Restaurante;
import org.vortxyz.pedido.domain.event.PedidoCanceladoEvent;
import org.vortxyz.pedido.domain.event.PedidoCriadoEvent;
import org.vortxyz.pedido.domain.event.PedidoPagoEvent;
import org.vortxyz.pedido.domain.exception.PedidoDomainException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
public class PedidoDomainServiceImpl implements PedidoDomainService {

    private static final String FUSO_HORARIO = "UTC";

    @Override
    public PedidoCriadoEvent validarEInicializarPedido(Pedido pedido, Restaurante restaurante) {
        validarRestaurante(restaurante);
        preencherInformacaoDoProduto(pedido, restaurante);
        pedido.validarPedido();
        pedido.inicializarPedido();
        log.info("Pedido com id: {} está inicializado.", pedido.getId().getValue());
        return new PedidoCriadoEvent(pedido, ZonedDateTime.now(ZoneId.of(FUSO_HORARIO)));
    }

    @Override
    public PedidoPagoEvent pagarPedido(Pedido pedido) {
        pedido.pagar();
        log.info("Pedido com id: {} está pago.", pedido.getId().getValue());
        return new PedidoPagoEvent(pedido, ZonedDateTime.now(ZoneId.of(FUSO_HORARIO)));
    }

    @Override
    public void aprovarPedido(Pedido pedido) {
        pedido.aprovar();
        log.info("Pedido com id: {} está aprovado.", pedido.getId().getValue());
    }

    @Override
    public PedidoCanceladoEvent cancelarPagamentoDoPedido(Pedido pedido, List<String> mensagensFalha) {
        pedido.iniciarCancelamento(mensagensFalha);
        log.info("Pagamento sendo cancelado para o pedido com id: {}.", pedido.getId().getValue());
        return new PedidoCanceladoEvent(pedido, ZonedDateTime.now(ZoneId.of(FUSO_HORARIO)));
    }

    @Override
    public void cancelarPedido(Pedido pedido, List<String> mensagensFalha) {
        pedido.cancelar(mensagensFalha);
        log.info("Pedido com id: {} está cancelado.", pedido.getId().getValue());
    }

    private void validarRestaurante(Restaurante restaurante) {
        if (!restaurante.isAtivo()) {
            throw new PedidoDomainException("Restaurante com id: " + restaurante.getId().getValue()
                    + " não está ativo no momento.");
        }
    }

    private void preencherInformacaoDoProduto(Pedido pedido, Restaurante restaurante) {
        pedido.getItens().forEach(pedidoItem -> restaurante.getProdutos().forEach(produto -> {
            Produto produtoAtual = pedidoItem.getProduto();
            if (produtoAtual.equals(produto)) {
                produtoAtual.atualizarComNomeEPrecoConfirmados(produto.getNome(), produto.getPreco());
            }
        }));
    }
}
