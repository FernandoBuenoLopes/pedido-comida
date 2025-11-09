package org.vortxyz.restaurante.service.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"org.vortxyz.restaurante.service.dataaccess", "org.vortxyz.dataaccess"})
@EnableJpaRepositories(basePackages = {"org.vortxyz.restaurante.service.dataaccess", "org.vortxyz.dataaccess"})
@SpringBootApplication(scanBasePackages = "org.vortxyz")
public class RestauranteServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(RestauranteServiceApplication.class, args);
    }
}
