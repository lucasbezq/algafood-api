package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.controller.CozinhaController;
import com.algaworks.algafood.api.dto.CozinhaDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CozinhaDTOConverter extends RepresentationModelAssemblerSupport<Cozinha, CozinhaDTO> {

    @Autowired
    private ModelMapper modelMapper;

    public CozinhaDTOConverter() {
        super(CozinhaController.class, CozinhaDTO.class);
    }

    @Override
    public CozinhaDTO toModel(Cozinha cozinha) {
        var cozinhaDTO = modelMapper.map(cozinha, CozinhaDTO.class);

        cozinhaDTO.add(linkTo(methodOn(CozinhaController.class)
                .buscar(cozinha.getId()))
                .withSelfRel());

        cozinhaDTO.add(linkTo(methodOn(CozinhaController.class)
                .listar(null))
                .withRel("cozinhas"));

        return cozinhaDTO;
    }

}
