package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.converter.PedidoConverter;
import com.algaworks.algafood.api.converter.PedidoDTOConverter;
import com.algaworks.algafood.api.converter.PedidoResumoDTOConverter;
import com.algaworks.algafood.api.dto.PedidoDTO;
import com.algaworks.algafood.api.dto.PedidoResumoDTO;
import com.algaworks.algafood.api.dto.request.PedidoRequest;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import com.algaworks.algafood.infrastructure.repository.spec.PedidoSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoDTOConverter pedidoDTOConverter;

    @Autowired
    private EmissaoPedidoService emissaoPedidoService;

    @Autowired
    private PedidoResumoDTOConverter pedidoResumoDTOConverter;

    @Autowired
    private PedidoConverter pedidoConverter;

    @GetMapping
    public Page<PedidoResumoDTO> pesquisar(PedidoFilter filtro, @PageableDefault(size = 10) Pageable pageable) {
        pageable = convertPageable(pageable);
        var pedidos = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageable);
        var pedidosResumoDTO = pedidoResumoDTOConverter.toCollectionDTO(pedidos.getContent());
        var pedidosResumoDTOPage = new PageImpl<>(pedidosResumoDTO, pageable, pedidos.getTotalElements());

        return pedidosResumoDTOPage;
    }

    @GetMapping("/{codigoPedido}")
    public PedidoDTO buscar(@PathVariable String codigoPedido) {
        var pedido = emissaoPedidoService.buscarPedido(codigoPedido);
        return pedidoDTOConverter.toDTO(pedido);
    }

    @PostMapping
    public PedidoDTO adicionar(@RequestBody @Valid PedidoRequest pedidoRequest) {
        try {
            var novoPedido = pedidoConverter.toDomain(pedidoRequest);

            //TODO: pegar usuário autenticado
            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);

            novoPedido = emissaoPedidoService.emitir(novoPedido);
            return pedidoDTOConverter.toDTO(novoPedido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    private Pageable convertPageable(Pageable apiPageable) {
        var map = Map.of(
                "codigo", "codigo",
                "subtotal", "subtotal",
                "taxaFrete", "taxaFrete",
                "valorTotal", "valorTotal",
                "dataCriacao", "dataCriacao",
                "restaurante.nome", "restaurante.nome",
                "restaurante.id", "restaurante.id",
                "cliente.id", "cliente.id",
                "cliente.nome", "cliente.nome"
        );

        return PageableTranslator.translate(apiPageable, map);
    }

}
