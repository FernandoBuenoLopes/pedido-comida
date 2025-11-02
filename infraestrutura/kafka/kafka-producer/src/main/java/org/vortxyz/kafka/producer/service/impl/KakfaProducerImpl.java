package org.vortxyz.kafka.producer.service.impl;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.vortxyz.kafka.producer.exception.KafkaProducerException;
import org.vortxyz.kafka.producer.service.KafkaProducer;

import java.io.Serializable;

@Slf4j
@Component
public class KakfaProducerImpl<K extends Serializable, V extends SpecificRecordBase> implements KafkaProducer<K, V> {

    private final KafkaTemplate<K, V> kafkaTemplate;

    public KakfaProducerImpl(KafkaTemplate<K, V> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(String topicoNome, K chave, V mensagem, ListenableFutureCallback<SendResult<K, V>> callback) {
        log.info("Enviando mensagem: \"{}\" ao tópico: {}", mensagem, topicoNome);
        kafkaTemplate.send(topicoNome, chave, mensagem);
        try {
            ListenableFuture<SendResult<K, V>> kafkaResultFuture = kafkaTemplate.send(topicoNome, chave, mensagem);
            kafkaResultFuture.addCallback(callback);
        } catch (KafkaException e) {
            log.error("Erro no Kafka Producer con chave: \"{}\", mensagem: \"{}\" e exceção: \"{}\"", chave, mensagem, e.getMessage());
            throw new KafkaProducerException("Erro no Kafka Producer con chave: \"" + chave + "\", mensagem: \"" + mensagem + "\" e exceção: \"" + e.getMessage() + "\"");
        }
    }

    @PreDestroy
    public void close() {
        if (kafkaTemplate != null) {
            log.info("Fechando o Kafka Producer.");
            kafkaTemplate.destroy();
        }
    }
}
