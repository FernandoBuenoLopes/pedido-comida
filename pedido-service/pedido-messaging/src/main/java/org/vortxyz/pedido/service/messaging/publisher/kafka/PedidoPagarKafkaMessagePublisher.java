package org.vortxyz.pedido.service.messaging.publisher.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.vortxyz.kafka.pedido.avro.model.RestauranteAprovacaoRequestAvroModel;
import org.vortxyz.kafka.producer.KafkaMessageHelper;
import org.vortxyz.kafka.producer.service.KafkaProducer;
import org.vortxyz.pedido.domain.config.PedidoServiceConfigData;
import org.vortxyz.pedido.domain.event.PedidoPagoEvent;
import org.vortxyz.pedido.domain.ports.output.message.publisher.restauranteaprovacao.PedidoPagoRestauranteRequestMessagePublisher;
import org.vortxyz.pedido.service.messaging.mapper.PedidoMessagingDataMapper;

@Slf4j
@Component
public class PedidoPagarKafkaMessagePublisher implements PedidoPagoRestauranteRequestMessagePublisher {

    private final PedidoMessagingDataMapper pedidoMessagingDataMapper;
    private final PedidoServiceConfigData pedidoServiceConfigData;
    private final KafkaProducer<String, RestauranteAprovacaoRequestAvroModel> kafkaProducer;
    private final KafkaMessageHelper kafkaMessageHelper;

    public PedidoPagarKafkaMessagePublisher(PedidoMessagingDataMapper pedidoMessagingDataMapper, PedidoServiceConfigData pedidoServiceConfigData, KafkaProducer<String, RestauranteAprovacaoRequestAvroModel> kafkaProducer, KafkaMessageHelper kafkaMessageHelper) {
        this.pedidoMessagingDataMapper = pedidoMessagingDataMapper;
        this.pedidoServiceConfigData = pedidoServiceConfigData;
        this.kafkaProducer = kafkaProducer;
        this.kafkaMessageHelper = kafkaMessageHelper;
    }

    @Override
    public void publish(PedidoPagoEvent domainEvent) {

        String pedidoId = domainEvent.getPedido().getId().getValue().toString();
        try {
            RestauranteAprovacaoRequestAvroModel pedidoAprovacaoRequestAvroModel = pedidoMessagingDataMapper.pedidoPagoEventToRestauranteAprovacaoRequestAvroModel(domainEvent);
            kafkaProducer.send(pedidoServiceConfigData.getRestauranteAprovacaoRequestTopicName(), pedidoId, pedidoAprovacaoRequestAvroModel, kafkaMessageHelper.obterKafkaCallback(
                    pedidoServiceConfigData.getRestauranteAprovacaoRequestTopicName(),
                    pedidoAprovacaoRequestAvroModel,
                    pedidoId,
                    "RestauranteAprovacaoRequestAvroModel")
            );
            log.info("RestauranteAprovacaoRequestAvroModel enviado ao Kafka para pedido com id: {}", pedidoId);
        } catch (Exception e) {
            log.error("Erro ao enviar mensagem do RestauranteAprovacaoRequestAvroModel ao Kafka com pedido id: \"{}\", erro: \"{}\".", pedidoId, e.getMessage());
        }
    }
}
