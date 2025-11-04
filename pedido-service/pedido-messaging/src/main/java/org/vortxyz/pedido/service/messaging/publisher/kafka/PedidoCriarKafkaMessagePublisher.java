package org.vortxyz.pedido.service.messaging.publisher.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.vortxyz.kafka.pedido.avro.model.PagamentoRequestAvroModel;
import org.vortxyz.kafka.producer.service.KafkaProducer;
import org.vortxyz.pedido.domain.config.PedidoServiceConfigData;
import org.vortxyz.pedido.domain.event.PedidoCriadoEvent;
import org.vortxyz.pedido.domain.ports.output.message.publisher.pagamento.PedidoCriadoPagamentoRequestgMessagePublisher;
import org.vortxyz.pedido.service.messaging.mapper.PedidoMessagingDataMapper;

@Slf4j
@Component
public class PedidoCriarKafkaMessagePublisher implements PedidoCriadoPagamentoRequestgMessagePublisher {

    private final PedidoMessagingDataMapper pedidoMessagingDataMapper;
    private final PedidoServiceConfigData pedidoServiceConfigData;
    private final KafkaProducer<String, PagamentoRequestAvroModel> kafkaProducer;
    private final PedidoKafkaMessageHelper pedidoKafkaMessageHelper;

    public PedidoCriarKafkaMessagePublisher(PedidoMessagingDataMapper pedidoMessagingDataMapper, PedidoServiceConfigData pedidoServiceConfigData, KafkaProducer<String, PagamentoRequestAvroModel> kafkaProducer, PedidoKafkaMessageHelper pedidoKafkaMessageHelper) {
        this.pedidoMessagingDataMapper = pedidoMessagingDataMapper;
        this.pedidoServiceConfigData = pedidoServiceConfigData;
        this.kafkaProducer = kafkaProducer;
        this.pedidoKafkaMessageHelper = pedidoKafkaMessageHelper;
    }

    @Override
    public void publish(PedidoCriadoEvent pedidoCriadoEvent) {

        String pedidoId = pedidoCriadoEvent.getPedido().getId().getValue().toString();
        log.info("Recebido PedidoCriadoEvent para pedido id: {}", pedidoId);
        try {
            PagamentoRequestAvroModel pagamentoRequestAvroModel = pedidoMessagingDataMapper.pedidoCriadoEventToPagamentoRequestAvroModel(pedidoCriadoEvent);
            kafkaProducer.send(pedidoServiceConfigData.getPagamentoRequestTopicName(), pedidoId, pagamentoRequestAvroModel,
                    pedidoKafkaMessageHelper.obterKafkaCallback(pedidoServiceConfigData.getPagamentoResponseTopicName(), pagamentoRequestAvroModel, pedidoId, "PagamentoRequestAvroModel"));
            log.info("PagamentoRequestAvroModel enviado ao Kafka para pedido com id: \"{}\"", pagamentoRequestAvroModel.getPedidoId());

        } catch (Exception e){
            log.error("Erro ao enviar mensagem do PagamentoRequestAvroModel ao Kafka com pedido id: \"{}\", erro: \"{}\".", pedidoId, e.getMessage());
        }
    }
}
