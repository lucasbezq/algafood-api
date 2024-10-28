package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.service.GerenciadorDeStatusPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos/{codigoPedido}")
public class GerenciadorDeStatusPedidoController {

    @Autowired
    private GerenciadorDeStatusPedidoService gerenciadorDeStatusService;

    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmar(@PathVariable String codigoPedido) {
        gerenciadorDeStatusService.confirmar(codigoPedido);
    }

    @PutMapping("/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void entregar(@PathVariable String codigoPedido) {
        gerenciadorDeStatusService.entregar(codigoPedido);
    }

    @PutMapping("/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable String codigoPedido) {
        gerenciadorDeStatusService.cancelar(codigoPedido);
    }

}
