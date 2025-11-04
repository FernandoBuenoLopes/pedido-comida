package org.vortxyz.pedido.service.messaging.publisher.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.vortxyz.kafka.pedido.avro.model.PagamentoRequestAvroModel;

@Slf4j
@Component
public class PedidoKafkaMessageHelper {

    public <T> ListenableFutureCallback<SendResult<String, T>> obterKafkaCallback(String responseTopicName, T requestAvroModel, String pedidoId, String requestAvroModelName) {
        return new ListenableFutureCallback<SendResult<String, T>>() {
            @Override
            public void onFailure(Throwable ex) {

                log.error("Erro ao enviar mensagem do {}: \"{}\" ao tópico: {}.", requestAvroModelName, requestAvroModel.toString(), responseTopicName, ex);
            }

            @Override
            public void onSuccess(SendResult<String, T> result) {

                RecordMetadata recordMetadata = result.getRecordMetadata();
                log.info("Recebida mensgem bem-sucedida do Kafka para pedido com id: \"{}\", tópico: \"{}\", partição: \"{}\", offset: \"{}\", timestamp: \"{}\".",
                        pedidoId, recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset(), recordMetadata.timestamp());
            }
        };
    }
}
