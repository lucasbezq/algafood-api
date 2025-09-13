package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.controller.ProdutoController;
import com.algaworks.algafood.api.dto.ProdutoDTO;
import com.algaworks.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProdutoDTOConverter extends RepresentationModelAssemblerSupport<Produto, ProdutoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    public ProdutoDTOConverter() {
        super(Produto.class, ProdutoDTO.class);
    }

    public ProdutoDTO toModel(Produto produto) {
        var produtoDTO = modelMapper.map(produto, ProdutoDTO.class);

        produtoDTO.add(linkTo(methodOn(ProdutoController.class)
                .buscar(produto.getRestaurante().getId(), produto.getId()))
                .withSelfRel());

        if (produto.isAtivo()) {
            produtoDTO.add(linkTo(methodOn(ProdutoController.class)
                    .inativar(produto.getRestaurante().getId(), produto.getId()))
                    .withRel("inativar"));
        } else {
            produtoDTO.add(linkTo(methodOn(ProdutoController.class)
                    .ativar(produto.getRestaurante().getId(), produto.getId()))
                    .withRel("ativar"));
        }

        produtoDTO.add(linkTo(methodOn(ProdutoController.class)
                .listar(produto.getRestaurante().getId()))
                .withRel("produtos"));

        produtoDTO.add(linkTo(methodOn(ProdutoController.class)
                .buscarFoto(produto.getRestaurante().getId(), produto.getId()))
                .withRel("foto"));

        return produtoDTO;
    }

}
