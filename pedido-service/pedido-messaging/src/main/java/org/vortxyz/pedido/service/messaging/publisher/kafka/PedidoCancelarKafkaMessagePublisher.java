package org.vortxyz.pedido.service.messaging.publisher.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.vortxyz.kafka.pedido.avro.model.PagamentoRequestAvroModel;
import org.vortxyz.kafka.producer.KafkaMessageHelper;
import org.vortxyz.kafka.producer.service.KafkaProducer;
import org.vortxyz.pedido.domain.config.PedidoServiceConfigData;
import org.vortxyz.pedido.domain.event.PedidoCanceladoEvent;
import org.vortxyz.pedido.domain.ports.output.message.publisher.pagamento.PedidoCanceladoPagamentoRequestMessagePublisher;
import org.vortxyz.pedido.service.messaging.mapper.PedidoMessagingDataMapper;

@Slf4j
@Component
public class PedidoCancelarKafkaMessagePublisher implements PedidoCanceladoPagamentoRequestMessagePublisher {

    private final PedidoMessagingDataMapper pedidoMessagingDataMapper;
    private final PedidoServiceConfigData pedidoServiceConfigData;
    private final KafkaProducer<String, PagamentoRequestAvroModel> kafkaProducer;
    private final KafkaMessageHelper kafkaMessageHelper;

    public PedidoCancelarKafkaMessagePublisher(PedidoMessagingDataMapper pedidoMessagingDataMapper, PedidoServiceConfigData pedidoServiceConfigData, KafkaProducer<String, PagamentoRequestAvroModel> kafkaProducer, KafkaMessageHelper kafkaMessageHelper) {
        this.pedidoMessagingDataMapper = pedidoMessagingDataMapper;
        this.pedidoServiceConfigData = pedidoServiceConfigData;
        this.kafkaProducer = kafkaProducer;
        this.kafkaMessageHelper = kafkaMessageHelper;
    }

    @Override
    public void publish(PedidoCanceladoEvent pedidoCanceladoEvent) {

        String pedidoId = pedidoCanceladoEvent.getPedido().getId().getValue().toString();
        log.info("Recebido PedidoCanceladoEvent para pedido id: {}", pedidoId);
        try {
            PagamentoRequestAvroModel pagamentoRequestAvroModel = pedidoMessagingDataMapper.pedidoCanceladoEventToPagamentoRequestAvroModel(pedidoCanceladoEvent);
            kafkaProducer.send(pedidoServiceConfigData.getPagamentoRequestTopicName(), pedidoId, pagamentoRequestAvroModel,
                    kafkaMessageHelper.obterKafkaCallback(pedidoServiceConfigData.getPagamentoResponseTopicName(), pagamentoRequestAvroModel, pedidoId, "PagamentoRequestAvroModel"));
            log.info("PagamentoRequestAvroModel enviado ao Kafka para pedido com id: \"{}\"", pagamentoRequestAvroModel.getPedidoId());

        } catch (Exception e){
            log.error("Erro ao enviar mensagem do PagamentoRequestAvroModel ao Kafka com pedido id: \"{}\", erro: \"{}\".", pedidoId, e.getMessage());
        }
    }
}
