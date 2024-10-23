package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.PedidoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido buscarPedido(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradaException(id));
    }

}
