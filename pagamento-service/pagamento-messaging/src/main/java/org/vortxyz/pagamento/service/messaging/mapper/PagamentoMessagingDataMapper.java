package org.vortxyz.pagamento.service.messaging.mapper;

import org.springframework.stereotype.Component;
import org.vortxyz.domain.valueobject.PedidoPagamentoStatus;
import org.vortxyz.kafka.pedido.avro.model.PagamentoRequestAvroModel;
import org.vortxyz.kafka.pedido.avro.model.PagamentoResponseAvroModel;
import org.vortxyz.kafka.pedido.avro.model.PagamentoStatus;
import org.vortxyz.pagamento.service.domain.dto.PagamentoRequest;
import org.vortxyz.pagamento.service.domain.event.PagamentoCanceladoEvent;
import org.vortxyz.pagamento.service.domain.event.PagamentoCompletoEvent;
import org.vortxyz.pagamento.service.domain.event.PagamentoFalhoEvent;

import java.util.UUID;

@Component
public class PagamentoMessagingDataMapper {

    public PagamentoResponseAvroModel pagamentoCompletoEventToPagamentoResponseAvroModel(PagamentoCompletoEvent pagamentoCompletoEvent) {

        return PagamentoResponseAvroModel.builder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setPagamentoId(pagamentoCompletoEvent.getPagamento().getId().getValue().toString())
                .setClienteId(pagamentoCompletoEvent.getPagamento().getClienteId().getValue().toString())
                .setPedidoId(pagamentoCompletoEvent.getPagamento().getPedidoId().getValue().toString())
                .setPreco(pagamentoCompletoEvent.getPagamento().getPreco().getQuantia())
                .setCriadoEm(pagamentoCompletoEvent.getCriadoEm().toInstant())
                .setPagamentoStatus(PagamentoStatus.valueOf(pagamentoCompletoEvent.getPagamento().getPagamentoStatus().name()))
                .setMensagensFalha(pagamentoCompletoEvent.getMensagensFalha())
                .build();
    }

    public PagamentoResponseAvroModel pagamentoCanceladoEventToPagamentoResponseAvroModel(PagamentoCanceladoEvent pagamentoCanceladoEvent) {

        return PagamentoResponseAvroModel.builder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setPagamentoId(pagamentoCanceladoEvent.getPagamento().getId().getValue().toString())
                .setClienteId(pagamentoCanceladoEvent.getPagamento().getClienteId().getValue().toString())
                .setPedidoId(pagamentoCanceladoEvent.getPagamento().getPedidoId().getValue().toString())
                .setPreco(pagamentoCanceladoEvent.getPagamento().getPreco().getQuantia())
                .setCriadoEm(pagamentoCanceladoEvent.getCriadoEm().toInstant())
                .setPagamentoStatus(PagamentoStatus.valueOf(pagamentoCanceladoEvent.getPagamento().getPagamentoStatus().name()))
                .setMensagensFalha(pagamentoCanceladoEvent.getMensagensFalha())
                .build();
    }

    public PagamentoResponseAvroModel pagamentoFalhoEventToPagamentoResponseAvroModel(PagamentoFalhoEvent pagamentoFalhoEvent) {

        return PagamentoResponseAvroModel.builder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setPagamentoId(pagamentoFalhoEvent.getPagamento().getId().getValue().toString())
                .setClienteId(pagamentoFalhoEvent.getPagamento().getClienteId().getValue().toString())
                .setPedidoId(pagamentoFalhoEvent.getPagamento().getPedidoId().getValue().toString())
                .setPreco(pagamentoFalhoEvent.getPagamento().getPreco().getQuantia())
                .setCriadoEm(pagamentoFalhoEvent.getCriadoEm().toInstant())
                .setPagamentoStatus(PagamentoStatus.valueOf(pagamentoFalhoEvent.getPagamento().getPagamentoStatus().name()))
                .setMensagensFalha(pagamentoFalhoEvent.getMensagensFalha())
                .build();
    }

    public PagamentoRequest pagamentoRequestAvroModelToPagamentoRequest(PagamentoRequestAvroModel pagamentoRequestAvroModel) {

        return PagamentoRequest.builder()
                .id(pagamentoRequestAvroModel.getId())
                .sagaId(pagamentoRequestAvroModel.getSagaId())
                .clienteId(pagamentoRequestAvroModel.getClienteId())
                .pedidoId(pagamentoRequestAvroModel.getPedidoId())
                .preco(pagamentoRequestAvroModel.getPreco())
                .pedidoPagamentoStatus(PedidoPagamentoStatus.valueOf(pagamentoRequestAvroModel.getPedidoPagamentoStatus().name()))
                .criadoEm(pagamentoRequestAvroModel.getCriadoEm())
                .build();
    }
}
