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

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        var cozinhaId = restaurante.getCozinha().getId();
        var cidadeId = restaurante.getEndereco().getCidade().getId();
        var cozinha = cadastroCozinhaService.buscarCozinha(cozinhaId);
        var cidade = cadastroCidadeService.buscarCidade(cidadeId);

        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);

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

    @Transactional
    public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        var restaurante = buscarRestaurante(restauranteId);
        var formaPagamento = cadastroFormaPagamentoService.buscarFormaPagamento(formaPagamentoId);

        restaurante.removerFormaPagamento(formaPagamento);
    }

    @Transactional
    public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        var restaurante = buscarRestaurante(restauranteId);
        var formaPagamento = cadastroFormaPagamentoService.buscarFormaPagamento(formaPagamentoId);

        restaurante.adicionarFormaPagamento(formaPagamento);
    }

    @Transactional
    public void abrirRestaurante(Long restauranteId) {
        var restaurante = buscarRestaurante(restauranteId);
        restaurante.abrir();
    }

    @Transactional
    public void fecharRestaurante(Long restauranteId) {
        var restaurante = buscarRestaurante(restauranteId);
        restaurante.fechar();
    }

    public Restaurante buscarRestaurante(Long id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }

}
