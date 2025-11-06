package org.vortxyz.pagamento.service.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public PagamentoDomainService pagamentoDomainService() {
        return new PagamentoDomainServiceImpl();
    }
}
