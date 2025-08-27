package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.dto.PedidoResumoDTO;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoResumoDTOConverter extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoResumoDTOConverter() {
        super(PedidoController.class, PedidoResumoDTO.class);
    }

    @Override
    public PedidoResumoDTO toModel(Pedido pedido) {
        var pedidoResumoDTO = modelMapper.map(pedido, PedidoResumoDTO.class);

        pedidoResumoDTO.add(linkTo(methodOn(PedidoController.class)
                .buscar(pedido.getCodigo()))
                .withSelfRel());

        pedidoResumoDTO.add(linkTo(methodOn(PedidoController.class)
                .pesquisar(null, null))
                .withRel("pedidos"));

        pedidoResumoDTO.getRestaurante().add(linkTo(methodOn(RestauranteController.class)
                .buscar(pedido.getRestaurante().getId()))
                .withSelfRel());

        pedidoResumoDTO.getCliente().add(linkTo(methodOn(UsuarioController.class)
                .buscar(pedido.getCliente().getId()))
                .withSelfRel());


        return pedidoResumoDTO;
    }

}
