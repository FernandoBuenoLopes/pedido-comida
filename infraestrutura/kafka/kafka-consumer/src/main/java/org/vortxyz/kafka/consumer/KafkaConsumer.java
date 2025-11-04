package org.vortxyz.kafka.consumer;

import org.apache.avro.specific.SpecificRecordBase;

import java.util.List;

public interface KafkaConsumer<T extends SpecificRecordBase> {
    void receive(List<T> mensagens, List<String> chaves, List<Integer> particoes, List<Long> offsets);
}
