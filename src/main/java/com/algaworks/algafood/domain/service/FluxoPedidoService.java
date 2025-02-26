package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FluxoPedidoService {

    @Autowired
    private EmissaoPedidoService emissaoPedidoService;

    @Autowired
    private EnvioEmailService envioEmailService;

    @Transactional
    public void confirmar(String codigoPedido) {
        var pedido = emissaoPedidoService.buscarPedido(codigoPedido);
        pedido.confirmar();

        var mensagem = EnvioEmailService.Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado!")
                .corpo("O pedido de código " + codigoPedido + " foi confirmado!")
                .destinatario(pedido.getCliente().getEmail())
                .build();

        envioEmailService.enviar(mensagem);
    }

    @Transactional
    public void entregar(String codigoPedido) {
        var pedido = emissaoPedidoService.buscarPedido(codigoPedido);
        pedido.entregar();
    }

    @Transactional
    public void cancelar(String codigoPedido) {
        var pedido = emissaoPedidoService.buscarPedido(codigoPedido);
        pedido.cancelar();
    }
}
