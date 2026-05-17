package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.controller.GrupoController;
import com.algaworks.algafood.api.controller.GrupoPermissaoController;
import com.algaworks.algafood.api.dto.GrupoDTO;
import com.algaworks.algafood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GrupoDTOConverter extends RepresentationModelAssemblerSupport<Grupo, GrupoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    public GrupoDTOConverter() {
        super(Grupo.class, GrupoDTO.class);
    }

    public GrupoDTO toModel(Grupo grupo) {
        var grupoDTO = modelMapper.map(grupo, GrupoDTO.class);

        grupoDTO.add(linkTo(methodOn(GrupoController.class)
                .buscar(grupo.getId()))
                .withSelfRel());

        grupoDTO.add(linkTo(methodOn(GrupoController.class)
                .listar())
                .withRel("grupos"));

        grupoDTO.add(
                linkTo(methodOn(GrupoPermissaoController.class)
                        .listar(grupoDTO.getId()))
                        .withRel("permissoes")
        );

        return grupoDTO;
    }

    @Override
    public CollectionModel<GrupoDTO> toCollectionModel(Iterable<? extends Grupo> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(GrupoController.class).withSelfRel());
    }
}
