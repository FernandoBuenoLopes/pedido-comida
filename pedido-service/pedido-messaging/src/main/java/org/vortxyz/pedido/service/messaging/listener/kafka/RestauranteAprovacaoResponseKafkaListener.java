package org.vortxyz.pedido.service.messaging.listener.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.vortxyz.kafka.consumer.KafkaConsumer;
import org.vortxyz.kafka.pedido.avro.model.RestauranteAprovacaoResponseAvroModel;
import org.vortxyz.pedido.domain.ports.input.message.listener.restauranteaprovacao.RestauranteAprovacaoMessageListener;
import org.vortxyz.pedido.service.messaging.mapper.PedidoMessagingDataMapper;

import java.util.List;

@Slf4j
@Component
public class RestauranteAprovacaoresponseKafkaListener implements KafkaConsumer<RestauranteAprovacaoResponseAvroModel> {

    private final RestauranteAprovacaoMessageListener restauranteAprovacaoMessageListener;
    private final PedidoMessagingDataMapper pedidoMessagingDataMapper;

    public RestauranteAprovacaoresponseKafkaListener(RestauranteAprovacaoMessageListener restauranteAprovacaoMessageListener, PedidoMessagingDataMapper pedidoMessagingDataMapper) {
        this.restauranteAprovacaoMessageListener = restauranteAprovacaoMessageListener;
        this.pedidoMessagingDataMapper = pedidoMessagingDataMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.restaurante-aprovacao-consumer-group-id}", topics = "restaurante-service.restaurante-aprovacao-response-topic-name")
    public void receive(@Payload List<RestauranteAprovacaoResponseAvroModel> mensagens,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<Long> chaves,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> particoes,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

    }
}
