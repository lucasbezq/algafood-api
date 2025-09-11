package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Transactional
    public Produto salvar(Produto produto, Long restauranteId) {
        var restaurante = cadastroRestauranteService.buscarRestaurante(restauranteId);
        produto.setRestaurante(restaurante);
        return produtoRepository.save(produto);
    }

    public Produto buscarProduto(Long produtoId, Long restauranteId) {
        return produtoRepository.findByIdAndRestauranteId(produtoId, restauranteId)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId, restauranteId));
    }

    public void ativar(Long produtoId, Long restauranteId) {
        var produto = buscarProduto(produtoId, restauranteId);
        produto.ativar();
        salvar(produto, produto.getRestaurante().getId());
    }

    public void inativar(Long produtoId, Long restauranteId) {
        var produto = buscarProduto(produtoId, restauranteId);
        produto.inativar();
        salvar(produto, produto.getRestaurante().getId());
    }

}
