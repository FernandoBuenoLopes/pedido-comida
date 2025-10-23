package org.vortxyz.pedido.domain.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.vortxyz.domain.valueobject.PagamentoStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PagamentoResponse {
    private String id;
    private String sagaId;
    private String pedidoId;
    private String pagamentoId;
    private String clienteId;
    private BigDecimal preco;
    private Instant criadoEm;
    private PagamentoStatus pagamentoStatus;
    private List<String> mensagensFalha;
}
