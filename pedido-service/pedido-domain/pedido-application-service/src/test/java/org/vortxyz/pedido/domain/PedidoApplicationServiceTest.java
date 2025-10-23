package org.vortxyz.pedido.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.vortxyz.domain.valueobject.*;
import org.vortxyz.pedido.domain.dto.create.PedidoCriarCommand;
import org.vortxyz.pedido.domain.dto.create.PedidoCriarResponse;
import org.vortxyz.pedido.domain.dto.create.PedidoEndereco;
import org.vortxyz.pedido.domain.dto.create.PedidoItem;
import org.vortxyz.pedido.domain.entity.Cliente;
import org.vortxyz.pedido.domain.entity.Pedido;
import org.vortxyz.pedido.domain.entity.Produto;
import org.vortxyz.pedido.domain.entity.Restaurante;
import org.vortxyz.pedido.domain.exception.PedidoDomainException;
import org.vortxyz.pedido.domain.mapper.PedidoMapper;
import org.vortxyz.pedido.domain.ports.input.service.PedidoApplicationService;
import org.vortxyz.pedido.domain.ports.output.repository.ClienteRepository;
import org.vortxyz.pedido.domain.ports.output.repository.PedidoRepository;
import org.vortxyz.pedido.domain.ports.output.repository.RestauranteRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = PedidoTestConfiguration.class)
public class PedidoApplicationServiceTest {

    @Autowired
    private PedidoApplicationService pedidoApplicationService;

    @Autowired
    private PedidoMapper pedidoMapper;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    private PedidoCriarCommand pedidoCriarCommand;
    private PedidoCriarCommand pedidoCriarCommandPrecoErrado;
    private PedidoCriarCommand pedidoCriarCommandPrecoProdutoErrado;
    private final UUID CLIENTE_ID = UUID.fromString("e67067e0-97d6-4a33-a184-a44fd0366bb1");
    private final UUID RESTAURANTE_ID = UUID.fromString("ef797632-32e5-4d5b-9851-246fb85c531c");
    private final UUID PRODUTO_ID = UUID.fromString("2eaf17a0-0e96-4de0-9d61-a7b96b9e02a8");
    private final UUID PEDIDO_ID = UUID.fromString("92544d2a-b7af-4cb8-9cc1-91852d3e557f");
    private final BigDecimal PRECO_PRODUTO = new BigDecimal("50.00");
    private final BigDecimal PRECO_PRODUTO_3 = new BigDecimal("150.00");
    private final BigDecimal PRECO = new BigDecimal("200.00");

    @BeforeAll
    public void iniciar() {
        pedidoCriarCommand = PedidoCriarCommand.builder()
                .clienteId(CLIENTE_ID)
                .restauranteId(RESTAURANTE_ID)
                .endereco(PedidoEndereco.builder()
                        .rua("Rua do Mock, 123")
                        .cep("01234-567")
                        .cidade("Sampa")
                        .build())
                .preco(new Dinheiro(PRECO))
                .itens(List.of(PedidoItem.builder()
                                .produtoId(PRODUTO_ID)
                                .quantidade(1)
                                .preco(PRECO_PRODUTO)
                                .subTotal(PRECO_PRODUTO)
                                .build(),
                        PedidoItem.builder()
                                .produtoId(PRODUTO_ID)
                                .quantidade(3)
                                .preco(PRECO_PRODUTO)
                                .subTotal(PRECO_PRODUTO_3)
                                .build()))
                .build();

        pedidoCriarCommandPrecoErrado = PedidoCriarCommand.builder()
                .clienteId(CLIENTE_ID)
                .restauranteId(RESTAURANTE_ID)
                .endereco(PedidoEndereco.builder()
                        .rua("Rua do Mock, 123")
                        .cep("01234-56")
                        .cidade("Sampa")
                        .build())
                .preco(new Dinheiro(PRECO.add(PRECO_PRODUTO)))
                .itens(List.of(PedidoItem.builder()
                                .produtoId(PRODUTO_ID)
                                .quantidade(1)
                                .preco(PRECO_PRODUTO)
                                .subTotal(PRECO_PRODUTO)
                                .build(),
                        PedidoItem.builder()
                                .produtoId(PRODUTO_ID)
                                .quantidade(3)
                                .preco(PRECO_PRODUTO)
                                .subTotal(PRECO_PRODUTO_3)
                                .build()))
                .build();

        pedidoCriarCommandPrecoProdutoErrado = PedidoCriarCommand.builder()
                .clienteId(CLIENTE_ID)
                .restauranteId(RESTAURANTE_ID)
                .endereco(PedidoEndereco.builder()
                        .rua("Rua do Mock, 123")
                        .cep("01234-56")
                        .cidade("Sampa")
                        .build())
                .preco(new Dinheiro(PRECO.add(new BigDecimal("10.00"))))
                .itens(List.of(PedidoItem.builder()
                                .produtoId(PRODUTO_ID)
                                .quantidade(1)
                                .preco(PRECO_PRODUTO.add(new BigDecimal("10.00")))
                                .subTotal(PRECO_PRODUTO.add(new BigDecimal("10.00")))
                                .build(),
                        PedidoItem.builder()
                                .produtoId(PRODUTO_ID)
                                .quantidade(3)
                                .preco(PRECO_PRODUTO)
                                .subTotal(PRECO_PRODUTO_3)
                                .build()))
                .build();

        Cliente cliente = new Cliente();
        cliente.setId(new ClienteId(CLIENTE_ID));

        Restaurante restaurante = Restaurante.builder()
                .restauranteId(new RestauranteId(pedidoCriarCommand.getRestauranteId()))
                .produtos(List.of(new Produto(new ProdutoId(PRODUTO_ID), "produto_1", new Dinheiro(PRECO_PRODUTO)),
                        new Produto(new ProdutoId(PRODUTO_ID), "produto_2", new Dinheiro(PRECO_PRODUTO))))
                .ativo(true)
                .build();

        Pedido pedido = pedidoMapper.pedidoCriarCommandToPedido(pedidoCriarCommand);
        pedido.setId(new PedidoId(PEDIDO_ID));

        when(clienteRepository.encontrarClientePorId(CLIENTE_ID)).thenReturn(Optional.of(cliente));
        when(restauranteRepository.encontrarRestauranteInformacao(pedidoMapper.pedidoCriarCommandToRestaurante(pedidoCriarCommand))).thenReturn(Optional.of(restaurante));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);
    }

    @Test
    public void testPedidoCriar() {
        PedidoCriarResponse pedidoCriarResponse = pedidoApplicationService.pedidoCriar(pedidoCriarCommand);
        assertEquals(PedidoStatus.PENDENTE, pedidoCriarResponse.getPedidoStatus());
        assertEquals("Pedido criado com sucesso.", pedidoCriarResponse.getMensagem());
        assertNotNull(pedidoCriarResponse.getPedidoRastreamentoId());
    }

    @Test
    public void testPedidoCriarComPrecoTotalErrado() {
        PedidoDomainException excecao = assertThrows(PedidoDomainException.class, () -> pedidoApplicationService.pedidoCriar(pedidoCriarCommandPrecoErrado));
        assertEquals("Preço total: R$250.00 não é igual ao preço total dos itens do pedido: R$200.00.", excecao.getMessage());
    }

    @Test
    public void testPedidoCriarComPrecoProdutoErrado() {
        PedidoDomainException excecao = assertThrows(PedidoDomainException.class, () -> pedidoApplicationService.pedidoCriar(pedidoCriarCommandPrecoProdutoErrado));
        assertEquals("Preço do item no pedido: R$60.00 inválido para o produto: produto_2", excecao.getMessage());
    }

    @Test
    public void testPedidoCriarComRestauranteInativo() {
        Restaurante restauranteInativo = Restaurante.builder()
                .restauranteId(new RestauranteId(pedidoCriarCommand.getRestauranteId()))
                .produtos(List.of(new Produto(new ProdutoId(PRODUTO_ID), "produto_1", new Dinheiro(PRECO_PRODUTO)),
                        new Produto(new ProdutoId(PRODUTO_ID), "produto_2", new Dinheiro(PRECO_PRODUTO))))
                .ativo(false)
                .build();
        when(restauranteRepository.encontrarRestauranteInformacao(pedidoMapper.pedidoCriarCommandToRestaurante(pedidoCriarCommand))).thenReturn(Optional.of(restauranteInativo));
        PedidoDomainException excecao = assertThrows(PedidoDomainException.class, () -> pedidoApplicationService.pedidoCriar(pedidoCriarCommand));
        assertEquals("Restaurante com id: " + restauranteInativo.getId().getValue() + " não está ativo no momento.", excecao.getMessage());

    }
}
