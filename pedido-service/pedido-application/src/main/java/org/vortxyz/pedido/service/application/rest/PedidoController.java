package org.vortxyz.pedido.service.application.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vortxyz.pedido.domain.dto.create.PedidoCriarCommand;
import org.vortxyz.pedido.domain.dto.create.PedidoCriarResponse;
import org.vortxyz.pedido.domain.dto.track.PedidoRastrearQuery;
import org.vortxyz.pedido.domain.dto.track.PedidoRastrearResponse;
import org.vortxyz.pedido.domain.ports.input.service.PedidoApplicationService;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/pedidos", produces = "application/vnd.api.v1+json")
public class PedidoController {

    private final PedidoApplicationService pedidoApplicationService;

    public PedidoController(PedidoApplicationService pedidoApplicationService) {
        this.pedidoApplicationService = pedidoApplicationService;
    }

    @PostMapping
    public ResponseEntity<PedidoCriarResponse> pedidoCriar(@RequestBody PedidoCriarCommand pedidoCriarCommand) {
        log.info("Criando pedido para cliente: {} no restaurante: {}.", pedidoCriarCommand.getClienteId(), pedidoCriarCommand.getRestauranteId());
        pedidoApplicationService.pedidoCriar(pedidoCriarCommand);
        PedidoCriarResponse pedidoCriarResponse = pedidoApplicationService.pedidoCriar(pedidoCriarCommand);
        log.info("Pedido criado com id de rastreamento: {}", pedidoCriarResponse.getPedidoRastreamentoId());
        return ResponseEntity.ok(pedidoCriarResponse);
    }

    @GetMapping
    public ResponseEntity<PedidoRastrearResponse> pedidoObterPorIdRastreamento(@PathVariable UUID rastreamentoId) {
        PedidoRastrearResponse pedidoRastrearResponse = pedidoApplicationService.pedidoRastrear(PedidoRastrearQuery.builder().pedidoRastreamentoId(rastreamentoId).build());
        log.info("O pedido com id de rastreamento: {} está: {}.", pedidoRastrearResponse.getPedidoRastreamentoId(), pedidoRastrearResponse.getPedidoStatus());
        return ResponseEntity.ok(pedidoRastrearResponse);
    }
}
