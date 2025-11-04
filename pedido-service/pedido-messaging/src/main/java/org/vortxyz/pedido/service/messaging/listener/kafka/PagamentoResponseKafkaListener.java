package org.vortxyz.pedido.service.messaging.listener.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.vortxyz.kafka.consumer.KafkaConsumer;
import org.vortxyz.kafka.pedido.avro.model.PagamentoResponseAvroModel;
import org.vortxyz.pedido.domain.ports.input.message.listener.pagamento.PagamentoResponseMessageListener;
import org.vortxyz.pedido.service.messaging.mapper.PedidoMessagingDataMapper;

import java.util.List;

@Slf4j
@Component
public class PagamentoResponseKafkaListener implements KafkaConsumer<PagamentoResponseAvroModel> {

    private final PagamentoResponseMessageListener pagamentoResponseMessageListener;
    private final PedidoMessagingDataMapper pedidoMessagingDataMapper;

    public PagamentoResponseKafkaListener(PagamentoResponseMessageListener pagamentoResponseMessageListener, PedidoMessagingDataMapper pedidoMessagingDataMapper) {
        this.pagamentoResponseMessageListener = pagamentoResponseMessageListener;
        this.pedidoMessagingDataMapper = pedidoMessagingDataMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.pagamento-consumer-group-id}", topics = "${pedido-service.pagamento-response-topic-name}")
    public void receive(@Payload List<PagamentoResponseAvroModel> mensagens,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> chaves,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> particoes,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        log.info("Número de mensagens recebidas de pagamento: {}, com as chaves: {}, partições: {}, e offsets: {}.", mensagens.size(), chaves.toString(), particoes.toString(), offsets.toString());

        mensagens.forEach(pagamentoResponseAvroModel -> {
            switch (pagamentoResponseAvroModel.getPagamentoStatus()) {
                case COMPLETO -> {
                    log.info("Processando pagamento bem-sucedido para pedido com id: {}.", pagamentoResponseAvroModel.getPagamentoId());
                    pagamentoResponseMessageListener.pagamentoCompleto(pedidoMessagingDataMapper.pagamentoResponseAvroModelToPagamentoResponse(pagamentoResponseAvroModel));
                }
                case CANCELADO, FALHO-> {
                    log.info("Processando pagamento mau-sucedido para pedido com id: {}.", pagamentoResponseAvroModel.getPagamentoId());
                    pagamentoResponseMessageListener.pagamentoCancelado(pedidoMessagingDataMapper.pagamentoResponseAvroModelToPagamentoResponse(pagamentoResponseAvroModel));
                }
            }
        });
    }
}
