package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.converter.RestauranteConverter;
import com.algaworks.algafood.api.converter.RestauranteDTOConverter;
import com.algaworks.algafood.api.dto.RestauranteDTO;
import com.algaworks.algafood.api.dto.request.RestauranteRequest;
import com.algaworks.algafood.domain.exception.*;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private SmartValidator validator;

    @Autowired
    private RestauranteDTOConverter restauranteDTOConverter;

    @Autowired
    private RestauranteConverter restauranteConverter;

    @GetMapping
    public List<RestauranteDTO> listar() {
        var restaurantes = restauranteRepository.findAll();
        return restauranteDTOConverter.toCollectionDTO(restaurantes);
    }

    @GetMapping("/{restauranteId}")
    public RestauranteDTO buscar(@PathVariable("restauranteId") Long restauranteId) {
        return restauranteDTOConverter.toDTO(cadastroRestauranteService.buscarRestaurante(restauranteId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteDTO adicionar(@RequestBody @Valid RestauranteRequest restauranteRequest) {
        try {
            var restaurante = restauranteConverter.toDomain(restauranteRequest);
            return restauranteDTOConverter.toDTO(cadastroRestauranteService.salvar(restaurante));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public RestauranteDTO atualizar(@PathVariable Long restauranteId, @RequestBody @Valid RestauranteRequest restauranteRequest) {
        try {
            var restauranteAtual = cadastroRestauranteService.buscarRestaurante(restauranteId);
            restauranteConverter.copyToDomain(restauranteRequest, restauranteAtual);

            return restauranteDTOConverter.toDTO(cadastroRestauranteService.salvar(restauranteAtual));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long restauranteId) {
        cadastroRestauranteService.ativar(restauranteId);
    }

    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarRestaurantes(@RequestBody List<Long> restauranteIds) {
        try {
            cadastroRestauranteService.ativar(restauranteIds);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e.getCause());
        }    }

    @DeleteMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long restauranteId) {
        cadastroRestauranteService.inativar(restauranteId);
    }

    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void iativarRestaurantes(@RequestBody List<Long> restauranteIds) {
        try {
            cadastroRestauranteService.inativar(restauranteIds);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e.getCause());
        }
    }

    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abrir(@PathVariable Long restauranteId) {
        cadastroRestauranteService.abrirRestaurante(restauranteId);
    }

    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fechar(@PathVariable Long restauranteId) {
        cadastroRestauranteService.fecharRestaurante(restauranteId);
    }
}
