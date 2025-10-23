package org.vortxyz.pedido.domain;

import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.vortxyz.pedido.domain.ports.output.message.publisher.pagamento.PedidoCanceladoPagamentoRequestMessagePublisher;
import org.vortxyz.pedido.domain.ports.output.message.publisher.pagamento.PedidoCriadoPagamentoRequestgMessagePublisher;
import org.vortxyz.pedido.domain.ports.output.message.publisher.restauranteaprovacao.PedidoPagoRestauranteRequestMessagePublisher;
import org.vortxyz.pedido.domain.ports.output.repository.ClienteRepository;
import org.vortxyz.pedido.domain.ports.output.repository.PedidoRepository;
import org.vortxyz.pedido.domain.ports.output.repository.RestauranteRepository;

@SpringBootApplication(scanBasePackages = "org.vortxyz")
public class PedidoTestConfiguration {

    @Bean
    public PedidoCriadoPagamentoRequestgMessagePublisher pedidoCriadoPagamentoRequestgMessagePublisher() {
        return Mockito.mock(PedidoCriadoPagamentoRequestgMessagePublisher.class);
    }

    @Bean
    public PedidoCanceladoPagamentoRequestMessagePublisher pedidoCanceladoPagamentoRequestMessagePublisher() {
        return Mockito.mock(PedidoCanceladoPagamentoRequestMessagePublisher.class);
    }

    @Bean
    public PedidoPagoRestauranteRequestMessagePublisher pedidoPagoRestauranteRequestMessagePublisher() {
        return Mockito.mock(PedidoPagoRestauranteRequestMessagePublisher.class);
    }

    @Bean
    public PedidoRepository pedidoRepository() {
        return Mockito.mock(PedidoRepository.class);
    }

    @Bean
    public ClienteRepository clienteRepository() {
        return Mockito.mock(ClienteRepository.class);
    }

    @Bean
    public RestauranteRepository restauranteRepository() {
        return Mockito.mock(RestauranteRepository.class);
    }

    @Bean
    public PedidoDomainService pedidoDomainService() {
        return new PedidoDomainServiceImpl();
    }
}
