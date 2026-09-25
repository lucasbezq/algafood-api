package com.algaworks.algafood.api.v1.converter;

import com.algaworks.algafood.api.v1.controller.ProdutoController;
import com.algaworks.algafood.api.v1.dto.FotoProdutoDTO;
import com.algaworks.algafood.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FotoProdutoDTOConverter extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    public FotoProdutoDTOConverter() {
        super(FotoProduto.class, FotoProdutoDTO.class);
    }

    public FotoProdutoDTO toModel(FotoProduto foto) {
        var fotoProdutoDTO =  modelMapper.map(foto, FotoProdutoDTO.class);

        fotoProdutoDTO.add(linkTo(methodOn(ProdutoController.class)
                .buscarFoto(foto.getRestauranteId(), foto.getProduto().getId()))
                .withSelfRel());

        return fotoProdutoDTO;
    }

}
