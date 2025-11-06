package org.vortxyz.pagamento.service.dataaccess.credito.mapper;

import org.springframework.stereotype.Component;
import org.vortxyz.domain.valueobject.ClienteId;
import org.vortxyz.domain.valueobject.Dinheiro;
import org.vortxyz.pagamento.service.dataaccess.credito.entity.CreditoEntity;
import org.vortxyz.pagamento.service.domain.entity.Credito;
import org.vortxyz.pagamento.service.domain.valueobject.CreditoId;

@Component
public class CreditoDataAccessMapper {

    public Credito creditoEntityToCredito(CreditoEntity creditoEntity) {

        return Credito.builder()
                .id(new CreditoId(creditoEntity.getId()))
                .clienteId(new ClienteId(creditoEntity.getClienteId()))
                .creditoQuantiaTotal(new Dinheiro(creditoEntity.getCreditoQuantiaTotal()))
                .build();
    }

    public CreditoEntity creditoToCreditoEntity(Credito credito) {

        return CreditoEntity.builder()
                .id(credito.getId().getValue())
                .clienteId(credito.getClienteId().getValue())
                .creditoQuantiaTotal(credito.getCreditoQuantiaTotal().getQuantia())
                .build();
    }
}
