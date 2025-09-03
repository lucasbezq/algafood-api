package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.dto.CidadeDTO;
import com.algaworks.algafood.api.dto.RestauranteResumoDTO;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RestauranteResumoDTOConverter extends RepresentationModelAssemblerSupport<Restaurante, RestauranteResumoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    public RestauranteResumoDTOConverter() {
        super(Restaurante.class, RestauranteResumoDTO.class);
    }

    public RestauranteResumoDTO toModel(Restaurante restaurante) {
        var restauranteDTO =  modelMapper.map(restaurante, RestauranteResumoDTO.class);

        restauranteDTO.add(linkTo(methodOn(RestauranteController.class)
                .buscar(restaurante.getId()))
                .withSelfRel());

        restauranteDTO.add(linkTo(methodOn(RestauranteController.class)
                .listar()).withRel("restaurantes"));

        return restauranteDTO;
    }

    @Override
    public CollectionModel<RestauranteResumoDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(RestauranteController.class).withSelfRel());
    }

}
