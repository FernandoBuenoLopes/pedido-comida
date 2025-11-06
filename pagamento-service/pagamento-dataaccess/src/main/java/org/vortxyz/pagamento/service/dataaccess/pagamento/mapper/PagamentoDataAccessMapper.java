package org.vortxyz.pagamento.service.dataaccess.pagamento.mapper;

import org.springframework.stereotype.Component;
import org.vortxyz.domain.valueobject.ClienteId;
import org.vortxyz.domain.valueobject.Dinheiro;
import org.vortxyz.domain.valueobject.PedidoId;
import org.vortxyz.pagamento.service.dataaccess.pagamento.entity.PagamentoEntity;
import org.vortxyz.pagamento.service.domain.entity.Pagamento;
import org.vortxyz.pagamento.service.domain.valueobject.PagamentoId;

@Component
public class PagamentoDataAccessMapper {

    public Pagamento pagamentoEntityToPagamento(PagamentoEntity pagamentoEntity) {

        return Pagamento.builder()
                .id(new PagamentoId(pagamentoEntity.getId()))
                .pedidoId(new PedidoId(pagamentoEntity.getPedidoId()))
                .clienteId(new ClienteId(pagamentoEntity.getClienteId()))
                .preco(new Dinheiro(pagamentoEntity.getPreco()))
                .pagamentoStatus(pagamentoEntity.getStatus())
                .criadoEm(pagamentoEntity.getCriadoEm())
                .build();
    }

    public PagamentoEntity pagamentoToPagamentoEntity(Pagamento pagamento) {

        return PagamentoEntity.builder()
                .id(pagamento.getId().getValue())
                .pedidoId(pagamento.getPedidoId().getValue())
                .clienteId(pagamento.getClienteId().getValue())
                .preco(pagamento.getPreco().getQuantia())
                .status(pagamento.getPagamentoStatus())
                .criadoEm(pagamento.getCriadoEm())
                .build();
    }
}
