package org.vortxyz.pagamento.service.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "org.vortxyz.pagamento.service.dataaccess")
@EntityScan(basePackages = "org.vortxyz.pagamento.service.dataaccess")
@SpringBootApplication(scanBasePackages = "org.vortxyz")
public class PagamentoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PagamentoServiceApplication.class, args);
    }
}
