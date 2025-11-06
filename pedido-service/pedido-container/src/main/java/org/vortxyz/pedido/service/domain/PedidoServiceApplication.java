package org.vortxyz.pedido.service.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "org.vortxyz")
@EntityScan(basePackages = "org.vortxyz.pedido.service.dataaccess")
@EnableJpaRepositories(basePackages = "org.vortxyz.pedido.service.dataaccess")
public class PedidoServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PedidoServiceApplication.class, args);
    }
}
