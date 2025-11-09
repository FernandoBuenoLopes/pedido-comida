package org.vortxyz.restaurante.service.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.vortxyz.restaurante.domain.RestauranteDomainService;
import org.vortxyz.restaurante.domain.RestauranteDomainServiceImpl;

@Configuration
public class BeanConfiguration {

    @Bean
    public RestauranteDomainService restauranteDomainService() {

        return new RestauranteDomainServiceImpl();
    }
}
