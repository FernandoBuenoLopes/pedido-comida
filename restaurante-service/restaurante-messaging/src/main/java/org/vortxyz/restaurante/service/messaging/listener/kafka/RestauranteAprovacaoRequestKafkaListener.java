package org.vortxyz.restaurante.service.messaging.listener.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.vortxyz.kafka.consumer.KafkaConsumer;
import org.vortxyz.kafka.pedido.avro.model.RestauranteAprovacaoRequestAvroModel;
import org.vortxyz.restaurante.service.domain.ports.input.message.listener.RestauranteAprovacaoRequestMessageListener;
import org.vortxyz.restaurante.service.messaging.mapper.RestauranteMessagingDataMapper;

import java.util.List;

@Slf4j
@Component
public class RestauranteAprovacaoRequestKafkaListener implements KafkaConsumer<RestauranteAprovacaoRequestAvroModel> {

    private final RestauranteAprovacaoRequestMessageListener restauranteAprovacaoRequestMessageListener;
    private final RestauranteMessagingDataMapper restauranteMessagingDataMapper;

    public RestauranteAprovacaoRequestKafkaListener(RestauranteAprovacaoRequestMessageListener restauranteAprovacaoRequestMessageListener, RestauranteMessagingDataMapper restauranteMessagingDataMapper) {
        this.restauranteAprovacaoRequestMessageListener = restauranteAprovacaoRequestMessageListener;
        this.restauranteMessagingDataMapper = restauranteMessagingDataMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.restaurante-aprovacao-consumer-group-id}", topics = "${restaurante-service.restaurante-aprovacao-request-topic-name}")
    public void receive(@Payload List<RestauranteAprovacaoRequestAvroModel> mensagens,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> chaves,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> particoes,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        log.info("{} PedidoAprovacaoRequests recebidas com chaves: {}, partições: {}, e offsets: {}, enviando para aprovação do restaurante.",
                mensagens.size(),
                chaves.toString(),
                particoes.toString(),
                offsets.toString());

        mensagens.forEach(restauranteAprovacaoRequestAvroModel -> {
            log.info("Processando aprovação do pedido em: {}.", System.nanoTime());
            restauranteAprovacaoRequestMessageListener.pedidoAprovar(restauranteMessagingDataMapper.restauranteAprovacaoRequestAvroModelToRestauranteAprovacaoRequest(restauranteAprovacaoRequestAvroModel));
        });
    }
}
