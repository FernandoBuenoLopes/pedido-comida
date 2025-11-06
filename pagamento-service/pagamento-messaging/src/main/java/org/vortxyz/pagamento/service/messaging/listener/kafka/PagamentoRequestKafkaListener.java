package org.vortxyz.pagamento.service.messaging.listener.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.vortxyz.kafka.consumer.KafkaConsumer;
import org.vortxyz.kafka.pedido.avro.model.PagamentoRequestAvroModel;
import org.vortxyz.kafka.pedido.avro.model.PedidoPagamentoStatus;
import org.vortxyz.pagamento.service.domain.ports.input.message.listener.PagamentoRequestMessageListener;
import org.vortxyz.pagamento.service.messaging.mapper.PagamentoMessagingDataMapper;

import java.util.List;

@Slf4j
@Component
public class PagamentoRequestKafkaListener implements KafkaConsumer<PagamentoRequestAvroModel> {

    private final PagamentoRequestMessageListener pagamentoRequestMessageListener;
    private final PagamentoMessagingDataMapper pagamentoMessagingDataMapper;

    public PagamentoRequestKafkaListener(PagamentoRequestMessageListener pagamentoRequestMessageListener, PagamentoMessagingDataMapper pagamentoMessagingDataMapper) {
        this.pagamentoRequestMessageListener = pagamentoRequestMessageListener;
        this.pagamentoMessagingDataMapper = pagamentoMessagingDataMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.pagamento-consumer-group-id}", topics = "${pagamento-service.pagamento-request-topic-name}")
    public void receive(@Payload List<PagamentoRequestAvroModel> mensagens,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> chaves,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> particoes,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        log.info("{} pagamento requests recebidas com chaves: \"{}\", partições: \"{}\", e offsets: \"{}\".", mensagens.size(), chaves.toString(), particoes.toString(), offsets.toString());

        mensagens.forEach(pagamentoRequestAvroModel -> {
            switch (pagamentoRequestAvroModel.getPedidoPagamentoStatus()) {
                case PENDENTE -> {
                    log.info("Processando pagamento para pedido com id: {}.", pagamentoRequestAvroModel.getPedidoId());
                    pagamentoRequestMessageListener.completarPagamento(pagamentoMessagingDataMapper.pagamentoRequestAvroModelToPagamentoRequest(pagamentoRequestAvroModel));
                }
                case CANCELADO -> {
                    log.info("Cancelando pagamento para pedido com id: {}.", pagamentoRequestAvroModel.getPedidoId());
                    pagamentoRequestMessageListener.cancelarPagamento(pagamentoMessagingDataMapper.pagamentoRequestAvroModelToPagamentoRequest(pagamentoRequestAvroModel));
                }
            }
        });
    }
}
