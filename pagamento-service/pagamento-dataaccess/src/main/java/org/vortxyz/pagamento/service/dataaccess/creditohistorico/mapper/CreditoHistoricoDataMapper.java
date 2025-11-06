package org.vortxyz.pagamento.service.dataaccess.creditohistorico.mapper;

import org.springframework.stereotype.Component;
import org.vortxyz.domain.valueobject.ClienteId;
import org.vortxyz.domain.valueobject.Dinheiro;
import org.vortxyz.pagamento.service.dataaccess.creditohistorico.entity.CreditoHistoricoEntity;
import org.vortxyz.pagamento.service.domain.entity.CreditoHistorico;
import org.vortxyz.pagamento.service.domain.valueobject.CreditoHistoricoId;

@Component
public class CreditoHistoricoDataMapper {

    public CreditoHistoricoEntity CreditoHistoricoToCreditoHistoricoEntity(CreditoHistorico creditoHistorico) {

        return CreditoHistoricoEntity.builder()
                .id(creditoHistorico.getId().getValue())
                .clienteId(creditoHistorico.getClienteId().getValue())
                .quantia(creditoHistorico.getQuantia().getQuantia())
                .tipoTransacao(creditoHistorico.getTipoTransacao())
                .build();
    }

    public CreditoHistorico creditoHistoricoEntityToCreditoHistorico(CreditoHistoricoEntity creditoHistoricoEntity) {

        return CreditoHistorico.builder()
                .id(new CreditoHistoricoId(creditoHistoricoEntity.getId()))
                .clienteId(new ClienteId(creditoHistoricoEntity.getClienteId()))
                .quantia(new Dinheiro(creditoHistoricoEntity.getQuantia()))
                .tipoTransacao(creditoHistoricoEntity.getTipoTransacao())
                .build();
    }
}
