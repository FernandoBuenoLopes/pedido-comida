package org.vortxyz.restaurante.service.domain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "restaurante-service")
public class RestauranteServiceConfigData {

    private String restauranteAprovacaoRequestTopicName;
    private String restauranteAprovacaoResponseTopicName;
}
