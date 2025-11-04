package org.vortxyz.pedido.domain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "pedido-service")
public class PedidoServiceConfigData {
    private String pagamentoRequestTopicName;
    private String pagamentoResponseTopicName;
    private String restauranteAprovacaoRequestTopicName;
    private String restauranteAprovacaoResponseTopicName;
}
