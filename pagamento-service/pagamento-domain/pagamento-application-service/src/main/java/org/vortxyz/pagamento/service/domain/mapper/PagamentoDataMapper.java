package org.vortxyz.pagamento.service.domain.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.vortxyz.domain.valueobject.ClienteId;
import org.vortxyz.domain.valueobject.Dinheiro;
import org.vortxyz.domain.valueobject.PagamentoStatus;
import org.vortxyz.domain.valueobject.PedidoId;
import org.vortxyz.pagamento.service.domain.dto.PagamentoRequest;
import org.vortxyz.pagamento.service.domain.entity.Pagamento;
import org.vortxyz.pagamento.service.domain.event.PagamentoEvent;
import org.vortxyz.pagamento.service.domain.valueobject.PagamentoId;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

import static org.vortxyz.domain.DomainConstants.FUSO_HORARIO;

@Component
public class PagamentoDataMapper {
    public Pagamento pagamentoRequestToPagamento(PagamentoRequest pagamentoRequest) {

        return Pagamento.builder()
                .id(new PagamentoId(UUID.fromString(pagamentoRequest.getId())))
                .clienteId(new ClienteId(UUID.fromString(pagamentoRequest.getClienteId())))
                .pedidoId(new PedidoId(UUID.fromString(pagamentoRequest.getPedidoId())))
                .preco(new Dinheiro(pagamentoRequest.getPreco()))
                .pagamentoStatus(PagamentoStatus.valueOf(pagamentoRequest.getPedidoPagamentoStatus().name()))
                .criadoEm(ZonedDateTime.now(ZoneId.of(FUSO_HORARIO)))
                .build();
    }
}
