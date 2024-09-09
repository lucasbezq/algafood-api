package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        var cozinhaId = restaurante.getCozinha().getId();
        var cozinha = cadastroCozinhaService.buscarCozinha(cozinhaId);
        restaurante.setCozinha(cozinha);

        return restauranteRepository.save(restaurante);
    }

    @Transactional
    public void ativar(Long id) {
        var restauranteAtual = buscarRestaurante(id);
        restauranteAtual.ativar();
    }

    @Transactional
    public void inativar(Long id) {
        var restauranteAtual = buscarRestaurante(id);
        restauranteAtual.inativar();
    }

    public Restaurante buscarRestaurante(Long id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }

}
