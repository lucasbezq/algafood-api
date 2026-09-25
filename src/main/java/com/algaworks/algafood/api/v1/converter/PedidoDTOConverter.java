package com.algaworks.algafood.api.v1.converter;

import com.algaworks.algafood.api.v1.LinksUtil;
import com.algaworks.algafood.api.v1.dto.PedidoDTO;
import com.algaworks.algafood.api.v1.controller.*;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinksUtil linksUtil;

    public PedidoDTO toModel(Pedido pedido) {
        var pedidoDTO = modelMapper.map(pedido, PedidoDTO.class);

        pedidoDTO.add(linksUtil.linkToPedidos());

        pedidoDTO.add(linkTo(methodOn(PedidoController.class)
                .buscar(pedido.getCodigo()))
                .withSelfRel());

        pedidoDTO.getRestaurante().add(linkTo(methodOn(RestauranteController.class)
                .buscar(pedido.getRestaurante().getId()))
                .withSelfRel());

        pedidoDTO.getCliente().add(linkTo(methodOn(UsuarioController.class)
                .buscar(pedido.getCliente().getId()))
                .withSelfRel());

        pedidoDTO.getEnderecoEntrega().getCidade().add(linkTo(methodOn(CidadeController.class)
                .buscar(pedido.getEnderecoEntrega().getCidade().getId()))
                .withSelfRel());

        pedidoDTO.getFormaPagamento().add(linkTo(methodOn(FormaPagamentoController.class)
                .buscar(pedido.getFormaPagamento().getId(), null))
                .withSelfRel());

        pedidoDTO.getItens().forEach(item -> {
            item.add(linkTo(methodOn(ProdutoController.class)
                    .buscar(pedido.getRestaurante().getId(), item.getProdutoId()))
                    .withSelfRel());
        });

        if (pedido.podeSerConfirmado()) {
            pedidoDTO.add(linkTo(methodOn(GerenciadorDeStatusPedidoController.class)
                    .confirmar(pedido.getCodigo()))
                    .withRel("confirmar"));
        }

        if (pedido.podeSerEntregue()) {
            pedidoDTO.add(linkTo(methodOn(GerenciadorDeStatusPedidoController.class)
                    .entregar(pedido.getCodigo()))
                    .withRel("entregar"));
        }

        if (pedido.podeSerCancelado()) {
            pedidoDTO.add(linkTo(methodOn(GerenciadorDeStatusPedidoController.class)
                    .cancelar(pedido.getCodigo()))
                    .withRel("cancelar"));
        }

        return pedidoDTO;
    }

}
