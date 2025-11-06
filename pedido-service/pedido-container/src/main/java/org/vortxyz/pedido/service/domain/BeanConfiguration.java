package org.vortxyz.pedido.service.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.vortxyz.pedido.domain.PedidoDomainService;
import org.vortxyz.pedido.domain.PedidoDomainServiceImpl;

@Configuration
public class BeanConfiguration {

    @Bean
    public PedidoDomainService pedidoDomainService() {
        return new PedidoDomainServiceImpl();
    }
}
