package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @GetMapping
    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    @GetMapping(path = "/{restauranteId}")
    public Restaurante buscar(@PathVariable("restauranteId") Long restauranteId) {
        return cadastroRestauranteService.buscarRestaurante(restauranteId);
    }

    @PostMapping
    public Restaurante adicionar(@RequestBody Restaurante restaurante) {
        return cadastroRestauranteService.salvar(restaurante);
    }

    @PutMapping(path = "/{restauranteId}")
    public Restaurante atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante) {
        var restauranteAtual = cadastroRestauranteService.buscarRestaurante(restauranteId);
        BeanUtils.copyProperties(restaurante, restauranteAtual,
                "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
        return cadastroRestauranteService.salvar(restauranteAtual);
    }

    @PatchMapping(path = "/{restauranteId}")
    public Restaurante atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos) {
        var restauranteAutal = cadastroRestauranteService.buscarRestaurante(restauranteId);
        merge(campos, restauranteAutal);
        return atualizar(restauranteId, restauranteAutal);
    }

    private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
        var objectMapper = new ObjectMapper();
        var restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

        dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
            field.setAccessible(true);

            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

            ReflectionUtils.setField(field, restauranteDestino, novoValor);
        });
    }

}
