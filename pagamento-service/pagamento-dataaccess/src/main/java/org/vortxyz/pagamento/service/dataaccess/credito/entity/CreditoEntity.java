package org.vortxyz.pagamento.service.dataaccess.credito.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "creditos")
@Entity
public class CreditoEntity {

    @Id
    private UUID id;
    private UUID clienteId;
    private BigDecimal creditoQuantiaTotal;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CreditoEntity that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
