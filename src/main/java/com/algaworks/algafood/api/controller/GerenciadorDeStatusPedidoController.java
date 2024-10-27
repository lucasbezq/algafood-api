package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.service.GerenciadorDeStatusPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos/{pedidoId}")
public class GerenciadorDeStatusPedidoController {

    @Autowired
    private GerenciadorDeStatusPedidoService gerenciadorDeStatusService;

    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmar(@PathVariable Long pedidoId) {
        gerenciadorDeStatusService.confirmar(pedidoId);
    }

    @PutMapping("/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void entregar(@PathVariable Long pedidoId) {
        gerenciadorDeStatusService.entregar(pedidoId);
    }

    @PutMapping("/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable Long pedidoId) {
        gerenciadorDeStatusService.cancelar(pedidoId);
    }

}
