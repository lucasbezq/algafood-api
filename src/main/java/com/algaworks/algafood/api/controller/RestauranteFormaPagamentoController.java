package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.converter.FormaPagamentoDTOConverter;
import com.algaworks.algafood.api.dto.FormaPagamentoDTO;
import com.algaworks.algafood.api.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private FormaPagamentoDTOConverter formaPagamentoDTOConverter;

    @GetMapping
    public CollectionModel<FormaPagamentoDTO> listar(@PathVariable Long restauranteId) {
        var restaurante = cadastroRestauranteService.buscarRestaurante(restauranteId);
        var formasPagamentoDTO = formaPagamentoDTOConverter.toCollectionModel(restaurante.getFormasPagamento(), restauranteId);

        formasPagamentoDTO.getContent()
                .forEach(formaPagamentoDTO -> {
                    formaPagamentoDTO.add(linkTo(methodOn(RestauranteFormaPagamentoController.class)
                            .desassociar(restauranteId, formaPagamentoDTO.getId()))
                            .withRel("desassociar"));

                    formaPagamentoDTO.add(linkTo(methodOn(RestauranteFormaPagamentoController.class)
                            .associar(restauranteId, formaPagamentoDTO.getId()))
                            .withRel("associar"));
                });

        return formasPagamentoDTO;
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        cadastroRestauranteService.desassociarFormaPagamento(restauranteId, formaPagamentoId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        cadastroRestauranteService.associarFormaPagamento(restauranteId, formaPagamentoId);
        return ResponseEntity.noContent().build();
    }

}
