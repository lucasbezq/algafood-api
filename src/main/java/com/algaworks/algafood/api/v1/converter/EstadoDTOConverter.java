package com.algaworks.algafood.api.v1.converter;

import com.algaworks.algafood.api.v1.controller.EstadoController;
import com.algaworks.algafood.api.v1.dto.EstadoDTO;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EstadoDTOConverter extends RepresentationModelAssemblerSupport<Estado, EstadoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    public EstadoDTOConverter() {
        super(Estado.class, EstadoDTO.class);
    }

    @Override
    public EstadoDTO toModel(Estado estado) {
        var estadoDTO = modelMapper.map(estado, EstadoDTO.class);

        estadoDTO.add(linkTo(methodOn(EstadoController.class)
                .buscar(estadoDTO.getId())).withSelfRel());

        estadoDTO.add(linkTo(methodOn(EstadoController.class)
                .listar()).withRel("estados"));

        return estadoDTO;
    }

    @Override
    public CollectionModel<EstadoDTO> toCollectionModel(Iterable<? extends Estado> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(EstadoController.class).withSelfRel());
    }
}
