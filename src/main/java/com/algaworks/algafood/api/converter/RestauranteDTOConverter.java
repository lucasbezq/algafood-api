package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.controller.*;
import com.algaworks.algafood.api.dto.RestauranteDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RestauranteDTOConverter extends RepresentationModelAssemblerSupport<Restaurante, RestauranteDTO> {

    @Autowired
    private ModelMapper modelMapper;

    public RestauranteDTOConverter() {
        super(RestauranteController.class, RestauranteDTO.class);
    }

    @Override
    public RestauranteDTO toModel(Restaurante restaurante) {
     var restauranteDTO = modelMapper.map(restaurante, RestauranteDTO.class);

        restauranteDTO.add(linkTo(methodOn(RestauranteController.class)
                .buscar(restaurante.getId()))
                .withSelfRel());

        restauranteDTO.add(linkTo(methodOn(RestauranteController.class)
                .listar()).withRel("restaurantes"));

        restauranteDTO.getCozinha().add(linkTo(methodOn(CozinhaController.class)
                .buscar(restaurante.getCozinha().getId()))
                .withSelfRel());

        restauranteDTO.getEndereco()
                .getCidade()
                .add(linkTo(methodOn(CidadeController.class)
                .buscar(restaurante.getEndereco().getCidade().getId()))
                .withSelfRel());

        restauranteDTO.add(linkTo(methodOn(RestauranteFormaPagamentoController.class)
                .listar(restaurante.getId()))
                .withRel("formas-pagamento"));

        restauranteDTO.add(linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
                .listar(restauranteDTO.getId()))
                .withRel("responsaveis"));

        if (restaurante.isAberto()) {
            restauranteDTO.add(linkTo(methodOn(RestauranteController.class)
                    .fechar(restaurante.getId()))
                    .withRel("fechamento"));
        } else {
            restauranteDTO.add(linkTo(methodOn(RestauranteController.class)
                    .abrir(restaurante.getId()))
                    .withRel("abertura"));
        }

        if (restaurante.isAtivo()) {
            restauranteDTO.add(linkTo(methodOn(RestauranteController.class)
                    .inativar(restaurante.getId()))
                    .withRel("inativacao"));
        } else {
            restauranteDTO.add(linkTo(methodOn(RestauranteController.class)
                    .ativar(restaurante.getId()))
                    .withRel("ativacao"));
        }

        return restauranteDTO;
    }

}
