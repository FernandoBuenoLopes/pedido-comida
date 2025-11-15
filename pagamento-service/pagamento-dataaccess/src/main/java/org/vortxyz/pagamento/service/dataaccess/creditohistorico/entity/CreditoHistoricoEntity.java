package org.vortxyz.pagamento.service.dataaccess.creditohistorico.entity;

import javax.persistence.*;
import lombok.*;
import org.vortxyz.pagamento.service.domain.valueobject.TipoTransacao;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "credito_historicos")
@Entity
public class CreditoHistoricoEntity {

    @Id
    private UUID id;
    private UUID clienteId;
    private BigDecimal quantia;
    @Enumerated(EnumType.STRING)
    private TipoTransacao tipoTransacao;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CreditoHistoricoEntity that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
