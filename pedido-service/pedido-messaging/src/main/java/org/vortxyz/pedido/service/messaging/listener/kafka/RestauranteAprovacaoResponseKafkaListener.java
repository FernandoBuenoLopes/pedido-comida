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

import static org.vortxyz.pedido.domain.entity.Pedido.DELIMITADOR_MENSAGENS_FALHA;

@Slf4j
@Component
public class RestauranteAprovacaoResponseKafkaListener implements KafkaConsumer<RestauranteAprovacaoResponseAvroModel> {

    private final RestauranteAprovacaoMessageListener restauranteAprovacaoMessageListener;
    private final PedidoMessagingDataMapper pedidoMessagingDataMapper;

    public RestauranteAprovacaoResponseKafkaListener(RestauranteAprovacaoMessageListener restauranteAprovacaoMessageListener, PedidoMessagingDataMapper pedidoMessagingDataMapper) {
        this.restauranteAprovacaoMessageListener = restauranteAprovacaoMessageListener;
        this.pedidoMessagingDataMapper = pedidoMessagingDataMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.restaurante-aprovacao-consumer-group-id}", topics = "pedido-service.restaurante-aprovacao-response-topic-name")
    public void receive(@Payload List<RestauranteAprovacaoResponseAvroModel> mensagens,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> chaves,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> particoes,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        mensagens.forEach(restauranteAprovacaoResponseAvroModel -> {
            switch (restauranteAprovacaoResponseAvroModel.getPedidoAprovacaoStatus()) {
                case APROVADO -> {
                    log.info("Processando pedido aprovado com id: {}.", restauranteAprovacaoResponseAvroModel.getPedidoId());
                    restauranteAprovacaoMessageListener.pedidoAprovado(pedidoMessagingDataMapper.restauranteAprovacaoResponseAvroModelToRestauranteAprovacaoResponse(restauranteAprovacaoResponseAvroModel));
                }
                case REJEITADO -> {
                    log.info("Processando pedido rejeitado com id: {}, com as mensagens de falha: {}.", restauranteAprovacaoResponseAvroModel.getPedidoId(), String.join(DELIMITADOR_MENSAGENS_FALHA, restauranteAprovacaoResponseAvroModel.getMensagensFalha()));
                    restauranteAprovacaoMessageListener.pedidoRejeitado(pedidoMessagingDataMapper.restauranteAprovacaoResponseAvroModelToRestauranteAprovacaoResponse(restauranteAprovacaoResponseAvroModel));
                }
            }
        });
    }
}
