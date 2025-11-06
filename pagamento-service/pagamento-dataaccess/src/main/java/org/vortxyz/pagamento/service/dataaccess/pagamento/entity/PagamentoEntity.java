package org.vortxyz.pagamento.service.dataaccess.pagamento.entity;

import jakarta.persistence.*;
import lombok.*;
import org.vortxyz.domain.valueobject.PagamentoStatus;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pagamentos")
@Entity
public class PagamentoEntity {

    @Id
    private UUID id;
    private UUID clienteId;
    private UUID pedidoId;
    private BigDecimal preco;
    @Enumerated(EnumType.STRING)
    private PagamentoStatus status;
    private ZonedDateTime criadoEm;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PagamentoEntity that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
