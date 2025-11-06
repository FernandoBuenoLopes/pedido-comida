package org.vortxyz.pagamento.service.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.vortxyz.domain.valueobject.PedidoPagamentoStatus;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Builder
@AllArgsConstructor
public class PagamentoRequest {
    private String id;
    private String sagaId;
    private String pedidoId;
    private String clienteId;
    private BigDecimal preco;
    private Instant criadoEm;
    @Setter
    private PedidoPagamentoStatus pedidoPagamentoStatus;
}
