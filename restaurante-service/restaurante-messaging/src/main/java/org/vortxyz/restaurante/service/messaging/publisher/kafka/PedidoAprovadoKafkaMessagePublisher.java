package org.vortxyz.restaurante.service.messaging.publisher.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.vortxyz.kafka.pedido.avro.model.RestauranteAprovacaoResponseAvroModel;
import org.vortxyz.kafka.producer.KafkaMessageHelper;
import org.vortxyz.kafka.producer.service.KafkaProducer;
import org.vortxyz.restaurante.domain.event.PedidoAprovadoEvent;
import org.vortxyz.restaurante.service.domain.config.RestauranteServiceConfigData;
import org.vortxyz.restaurante.service.domain.ports.output.message.publisher.PedidoAprovadoMessagePublisher;
import org.vortxyz.restaurante.service.messaging.mapper.RestauranteMessagingDataMapper;

@Slf4j
@Component
public class PedidoAprovadoKafkaMessagePublisher implements PedidoAprovadoMessagePublisher {

    private final RestauranteMessagingDataMapper restauranteMessagingDataMapper;
    private final KafkaProducer<String, RestauranteAprovacaoResponseAvroModel> kafkaProducer;
    private final RestauranteServiceConfigData restauranteServiceConfigData;
    private final KafkaMessageHelper kafkaMessageHelper;

    public PedidoAprovadoKafkaMessagePublisher(RestauranteMessagingDataMapper restauranteMessagingDataMapper,
                                               KafkaProducer<String, RestauranteAprovacaoResponseAvroModel> kafkaProducer,
                                               RestauranteServiceConfigData restauranteServiceConfigData,
                                               KafkaMessageHelper kafkaMessageHelper) {
        this.restauranteMessagingDataMapper = restauranteMessagingDataMapper;
        this.kafkaProducer = kafkaProducer;
        this.restauranteServiceConfigData = restauranteServiceConfigData;
        this.kafkaMessageHelper = kafkaMessageHelper;
    }

    @Override
    public void publish(PedidoAprovadoEvent pedidoAprovadoEvent) {

        String pedidoId = pedidoAprovadoEvent.getPedidoAprovacao().getPedidoId().getValue().toString();
        log.info("Recebido PedidoAprovadoEvent para pedido com id: {}.", pedidoId);

        try {
            RestauranteAprovacaoResponseAvroModel restauranteAprovacaoResponseAvroModel = restauranteMessagingDataMapper
                    .pedidoAprovadoEventToRestauranteAprovacaoResponseAvroModel(pedidoAprovadoEvent);
            kafkaProducer.send(restauranteServiceConfigData.getRestauranteAprovacaoResponseTopicName(),
                    pedidoId,
                    restauranteAprovacaoResponseAvroModel,
                    kafkaMessageHelper.obterKafkaCallback(
                            restauranteServiceConfigData.getRestauranteAprovacaoResponseTopicName(),
                            restauranteAprovacaoResponseAvroModel,
                            pedidoId,
                            "RestauranteAprovacaoResponseAvroModel"));
            log.info("RestauranteAprovacaoResponseAvroModel enviado ao kafka em: {}.", System.nanoTime());

        } catch (Exception e) {
            log.error("Erro ao enviar mensagem do RestauranteAprovacaoResponseAvroModel ao kafka para pedido com id: {}, erro: {}.", pedidoId, e.getMessage());
        }
    }
}
