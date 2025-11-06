package org.vortxyz.pagamento.service.domain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "pagamento-service")
public class PagamentoServiceConfigData {

    private String pagamentoRequestTopicName;
    private String pagamentoResponseTopicName;
}
