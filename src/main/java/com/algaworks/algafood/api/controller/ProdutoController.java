package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.converter.ProdutoConverter;
import com.algaworks.algafood.api.converter.ProdutoDTOConverter;
import com.algaworks.algafood.api.dto.ProdutoDTO;
import com.algaworks.algafood.api.dto.request.ProdutoRequest;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos")
public class ProdutoController {

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private ProdutoDTOConverter produtoDTOConverter;

    @Autowired
    private ProdutoConverter produtoConverter;

    @Autowired
    private CadastroProdutoService cadastroProdutoService;

    @GetMapping
    public List<ProdutoDTO> listar(@PathVariable Long restauranteId) {
        var restaurante = cadastroRestauranteService.buscarRestaurante(restauranteId);
        return produtoDTOConverter.toCollectionDTO(restaurante.getProdutos());
    }

    @GetMapping("/{produtoId}")
    public ProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        var restaurante = cadastroRestauranteService.buscarRestaurante(restauranteId);
        return produtoDTOConverter.toDTO(cadastroProdutoService.buscarProduto(produtoId, restaurante.getId()));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDTO adicionar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoRequest produtoRequest) {
        var produto = produtoConverter.toDomain(produtoRequest);
        return produtoDTOConverter.toDTO(cadastroProdutoService.salvar(produto, restauranteId));
    }

    @PutMapping("/{produtoId}")
    public ProdutoDTO atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                @RequestBody @Valid ProdutoRequest produtoRequest) {
        var produtoAtual = cadastroProdutoService.buscarProduto(produtoId, restauranteId);
        produtoConverter.copyToDomain(produtoRequest, produtoAtual);
        return produtoDTOConverter.toDTO(cadastroProdutoService.salvar(produtoAtual, produtoAtual.getRestaurante().getId()));
    }

}
