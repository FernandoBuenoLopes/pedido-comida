package org.vortxyz.pagamento.service.messaging.publisher.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.vortxyz.kafka.pedido.avro.model.PagamentoResponseAvroModel;
import org.vortxyz.kafka.producer.KafkaMessageHelper;
import org.vortxyz.kafka.producer.service.KafkaProducer;
import org.vortxyz.pagamento.service.domain.config.PagamentoServiceConfigData;
import org.vortxyz.pagamento.service.domain.event.PagamentoCanceladoEvent;
import org.vortxyz.pagamento.service.domain.ports.output.message.publisher.PagamentoCanceladoMessagePublisher;
import org.vortxyz.pagamento.service.messaging.mapper.PagamentoMessagingDataMapper;

import java.util.*;

@Slf4j
@Component
public class PagamentoCanceladoKafkaMessagePublisher implements PagamentoCanceladoMessagePublisher {

    private final PagamentoMessagingDataMapper pagamentoMessagingDataMapper;
    private final KafkaProducer<String, PagamentoResponseAvroModel> kafkaProducer;
    private final PagamentoServiceConfigData pagamentoServiceConfigData;
    private final KafkaMessageHelper kafkaMessageHelper;

    public PagamentoCanceladoKafkaMessagePublisher(PagamentoMessagingDataMapper pagamentoMessagingDataMapper, KafkaProducer<String, PagamentoResponseAvroModel> kafkaProducer, PagamentoServiceConfigData pagamentoServiceConfigData, KafkaMessageHelper kafkaMessageHelper) {
        this.pagamentoMessagingDataMapper = pagamentoMessagingDataMapper;
        this.kafkaProducer = kafkaProducer;
        this.pagamentoServiceConfigData = pagamentoServiceConfigData;
        this.kafkaMessageHelper = kafkaMessageHelper;
    }

    @Override
    public void publish(PagamentoCanceladoEvent pagamentoCanceladoEvent) {

        String pedidoId = pagamentoCanceladoEvent.getPagamento().getPedidoId().getValue().toString();
        log.info("Recebido PagamentoCanceladoEvent para pedido com id: {}.", pedidoId);
        try {
            PagamentoResponseAvroModel pagamentoResponseAvroModel = pagamentoMessagingDataMapper.pagamentoCanceladoEventToPagamentoResponseAvroModel(pagamentoCanceladoEvent);
            kafkaProducer.send(pagamentoServiceConfigData.getPagamentoResponseTopicName(), pedidoId, pagamentoResponseAvroModel,
                    kafkaMessageHelper.obterKafkaCallback(pagamentoServiceConfigData.getPagamentoResponseTopicName(), pagamentoResponseAvroModel, pedidoId, "PagamentoResponseAvroModel"));
            log.info("PagamentoResponseAvroModel enviado ao kafka para pedido com id: {}.", pedidoId);
        } catch(Exception e) {
            log.error("Erro ao enviar mensagem do PagamentoResponseAvroModel ao kafka para pedido com id: {}, erro: \"{}\"", pedidoId, e.getMessage());
        }
    }
}
