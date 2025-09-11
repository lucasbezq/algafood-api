package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.converter.UsuarioDTOConverter;
import com.algaworks.algafood.api.dto.UsuarioDTO;
import com.algaworks.algafood.api.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilderDslKt.withRel;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private UsuarioDTOConverter usuarioDTOConverter;

    @GetMapping
    public CollectionModel<UsuarioDTO> listar(@PathVariable Long restauranteId) {
        var restaurante = cadastroRestauranteService.buscarRestaurante(restauranteId);

        var usuariosDTO = usuarioDTOConverter.toCollectionModel(restaurante.getResponsaveis())
                .removeLinks()
                .add(linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
                        .listar(restauranteId))
                        .withSelfRel())
                .add(linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
                        .associar(restauranteId, null))
                        .withRel("associar"));

        usuariosDTO.forEach(
                usuarioDTO -> usuarioDTO
                        .add(linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
                                .desassociar(restauranteId, usuarioDTO.getId()))
                                .withRel("desassociar"))
        );

        return usuariosDTO;
    }

    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        cadastroRestauranteService.associarResponsavel(restauranteId, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        cadastroRestauranteService.desassociarResponsavel(restauranteId, usuarioId);
        return ResponseEntity.noContent().build();
    }

}
