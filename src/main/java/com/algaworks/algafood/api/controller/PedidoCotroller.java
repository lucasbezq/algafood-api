package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.converter.PedidoDTOConverter;
import com.algaworks.algafood.api.dto.PedidoDTO;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.CadastroPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoCotroller {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoDTOConverter pedidoDTOConverter;

    @Autowired
    private CadastroPedidoService cadastroPedidoService;

    @GetMapping
    public List<PedidoDTO> listar() {
        var pedidos = pedidoRepository.findAll();
        return pedidoDTOConverter.toCollectionDTO(pedidos);
    }

    @GetMapping("/{pedidoId}")
    public PedidoDTO buscar(@PathVariable Long pedidoId) {
        var pedido = cadastroPedidoService.buscarPedido(pedidoId);
        return pedidoDTOConverter.toDTO(pedido);
    }


}
