package org.vortxyz.pagamento.service.messaging.publisher.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.vortxyz.kafka.pedido.avro.model.PagamentoResponseAvroModel;
import org.vortxyz.kafka.producer.KafkaMessageHelper;
import org.vortxyz.kafka.producer.service.KafkaProducer;
import org.vortxyz.pagamento.service.domain.config.PagamentoServiceConfigData;
import org.vortxyz.pagamento.service.domain.event.PagamentoFalhoEvent;
import org.vortxyz.pagamento.service.domain.ports.output.message.publisher.PagamentoFalhoMessagePublisher;
import org.vortxyz.pagamento.service.messaging.mapper.PagamentoMessagingDataMapper;

import java.util.*;

@Slf4j
@Component
public class PagamentoFalhoKafkaMessagePublisher implements PagamentoFalhoMessagePublisher {

    private final PagamentoMessagingDataMapper pagamentoMessagingDataMapper;
    private final KafkaProducer<String, PagamentoResponseAvroModel> kafkaProducer;
    private final PagamentoServiceConfigData pagamentoServiceConfigData;
    private final KafkaMessageHelper kafkaMessageHelper;

    public PagamentoFalhoKafkaMessagePublisher(PagamentoMessagingDataMapper pagamentoMessagingDataMapper, KafkaProducer<String, PagamentoResponseAvroModel> kafkaProducer, PagamentoServiceConfigData pagamentoServiceConfigData, KafkaMessageHelper kafkaMessageHelper) {
        this.pagamentoMessagingDataMapper = pagamentoMessagingDataMapper;
        this.kafkaProducer = kafkaProducer;
        this.pagamentoServiceConfigData = pagamentoServiceConfigData;
        this.kafkaMessageHelper = kafkaMessageHelper;
    }

    @Override
    public void publish(PagamentoFalhoEvent pagamentoFalhoEvent) {

        String pedidoId = pagamentoFalhoEvent.getPagamento().getPedidoId().getValue().toString();
        log.info("Recebido PagamentoFalhoEvent para pedido com id: {}.", pedidoId);
        try {
            PagamentoResponseAvroModel pagamentoResponseAvroModel = pagamentoMessagingDataMapper.pagamentoFalhoEventToPagamentoResponseAvroModel(pagamentoFalhoEvent);
            kafkaProducer.send(pagamentoServiceConfigData.getPagamentoResponseTopicName(), pedidoId, pagamentoResponseAvroModel,
                    kafkaMessageHelper.obterKafkaCallback(pagamentoServiceConfigData.getPagamentoResponseTopicName(), pagamentoResponseAvroModel, pedidoId, "PagamentoResponseAvroModel"));
            log.info("PagamentoResponseAvroModel enviado ao kafka para pedido com id: {}.", pedidoId);
        } catch(Exception e) {
            log.error("Erro ao enviar mensagem do PagamentoResponseAvroModel ao kafka para pedido com id: {}, erro: \"{}\"", pedidoId, e.getMessage());
        }
    }
}
