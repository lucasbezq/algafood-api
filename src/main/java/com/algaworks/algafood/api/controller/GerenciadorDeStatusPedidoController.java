package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.openapi.controller.GerenciadorDeStatusPedidoControllerOpenApi;
import com.algaworks.algafood.domain.service.FluxoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos/{codigoPedido}")
public class GerenciadorDeStatusPedidoController implements GerenciadorDeStatusPedidoControllerOpenApi {

    @Autowired
    private FluxoPedidoService gerenciadorDeStatusService;

    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> confirmar(@PathVariable String codigoPedido) {
        gerenciadorDeStatusService.confirmar(codigoPedido);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> entregar(@PathVariable String codigoPedido) {
        gerenciadorDeStatusService.entregar(codigoPedido);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> cancelar(@PathVariable String codigoPedido) {
        gerenciadorDeStatusService.cancelar(codigoPedido);
        return ResponseEntity.noContent().build();
    }

}
