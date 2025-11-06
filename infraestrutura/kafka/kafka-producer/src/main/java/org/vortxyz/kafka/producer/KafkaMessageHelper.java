package org.vortxyz.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
public class KafkaMessageHelper {

    public <T> ListenableFutureCallback<SendResult<String, T>> obterKafkaCallback(String responseTopicName, T avroModel, String pedidoId, String avroModelName) {

        return new ListenableFutureCallback<SendResult<String, T>>() {
            @Override
            public void onFailure(Throwable ex) {

                log.error("Erro ao enviar mensagem do {}: \"{}\" ao tópico: {}.", avroModelName, avroModel.toString(), responseTopicName, ex);
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
