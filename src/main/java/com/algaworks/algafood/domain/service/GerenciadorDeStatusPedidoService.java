package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.StatusPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

import static com.algaworks.algafood.domain.util.Constants.MSG_ALTERACAO_PEDIDO_NEGADA;

@Service
public class GerenciadorDeStatusPedidoService {

    @Autowired
    private EmissaoPedidoService emissaoPedidoService;

    @Transactional
    public void confirmar(Long pedidoId) {
        var pedido = emissaoPedidoService.buscarPedido(pedidoId);

        if (!pedido.getStatus().equals(StatusPedido.CRIADO)) {
            throw new NegocioException(String.format(MSG_ALTERACAO_PEDIDO_NEGADA,
                    pedido.getId(), pedido.getStatus().getDescricao(), StatusPedido.CONFIRMADO.getDescricao()));
        }

        pedido.setStatus(StatusPedido.CONFIRMADO);
        pedido.setDataConfirmacao(OffsetDateTime.now());
    }

    @Transactional
    public void entregar(Long pedidoId) {
        var pedido = emissaoPedidoService.buscarPedido(pedidoId);

        if (!pedido.getStatus().equals(StatusPedido.CONFIRMADO)) {
            throw new NegocioException(String.format(MSG_ALTERACAO_PEDIDO_NEGADA,
                    pedido.getId(), pedido.getStatus().getDescricao(), StatusPedido.ENTREGUE.getDescricao()));
        }

        pedido.setStatus(StatusPedido.ENTREGUE);
        pedido.setDataEntrega(OffsetDateTime.now());
    }

    @Transactional
    public void cancelar(Long pedidoId) {
        var pedido = emissaoPedidoService.buscarPedido(pedidoId);

        if (!pedido.getStatus().equals(StatusPedido.CRIADO)) {
            throw new NegocioException(String.format(MSG_ALTERACAO_PEDIDO_NEGADA,
                    pedido.getId(), pedido.getStatus().getDescricao(), StatusPedido.CANCELADO.getDescricao()));
        }

        pedido.setStatus(StatusPedido.CANCELADO);
        pedido.setDataCancelamento(OffsetDateTime.now());
    }
}
