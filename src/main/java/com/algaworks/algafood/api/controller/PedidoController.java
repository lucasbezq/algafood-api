package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.converter.PedidoConverter;
import com.algaworks.algafood.api.converter.PedidoDTOConverter;
import com.algaworks.algafood.api.converter.PedidoResumoDTOConverter;
import com.algaworks.algafood.api.dto.PedidoDTO;
import com.algaworks.algafood.api.dto.PedidoResumoDTO;
import com.algaworks.algafood.api.dto.request.PedidoRequest;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.CadastroPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoDTOConverter pedidoDTOConverter;

    @Autowired
    private CadastroPedidoService cadastroPedidoService;

    @Autowired
    private PedidoResumoDTOConverter pedidoResumoDTOConverter;

    @Autowired
    private PedidoConverter pedidoConverter;

    @GetMapping
    public List<PedidoResumoDTO> listar() {
        var pedidos = pedidoRepository.findAll();
        return pedidoResumoDTOConverter.toCollectionDTO(pedidos);
    }

    @GetMapping("/{pedidoId}")
    public PedidoDTO buscar(@PathVariable Long pedidoId) {
        var pedido = cadastroPedidoService.buscarPedido(pedidoId);
        return pedidoDTOConverter.toDTO(pedido);
    }

    @PostMapping
    public PedidoDTO adicionar(@RequestBody @Valid PedidoRequest pedidoRequest) {
        try {
            var novoPedido = pedidoConverter.toDomain(pedidoRequest);

            //TODO: pegar usuário autenticado
            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);

            novoPedido = cadastroPedidoService.emitir(novoPedido);
            return pedidoDTOConverter.toDTO(novoPedido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

}
