package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.controller.*;
import com.algaworks.algafood.api.dto.PedidoDTO;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoDTO toModel(Pedido pedido) {
        var pedidoDTO = modelMapper.map(pedido, PedidoDTO.class);
        var pageVariables = new TemplateVariables(
                new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM)
        );
        var pedidosUrl = linkTo(PedidoController.class).toUri().toString();

        pedidoDTO.add(linkTo(methodOn(PedidoController.class)
                .buscar(pedido.getCodigo()))
                .withSelfRel());

        pedidoDTO.add(new Link(UriTemplate.of(pedidosUrl,
                pageVariables), "pedidos"));

        /*pedidoDTO.add(linkTo(methodOn(PedidoController.class)
                .pesquisar(null, null))
                .withRel("pedidos"));*/

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

        return pedidoDTO;
    }

}
