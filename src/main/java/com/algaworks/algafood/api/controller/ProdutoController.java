package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.converter.ProdutoConverter;
import com.algaworks.algafood.api.converter.ProdutoDTOConverter;
import com.algaworks.algafood.api.dto.ProdutoDTO;
import com.algaworks.algafood.api.dto.request.FotoProdutoRequest;
import com.algaworks.algafood.api.dto.request.ProdutoRequest;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
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

    @PutMapping(path = "/{produtoId}/foto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void atualizarFoto(@PathVariable Long restauranteId,
                              @PathVariable Long produtoId,
                              @Valid FotoProdutoRequest request) {

        var nomeArquivo = UUID.randomUUID().toString()
                .concat("_")
                .concat(request.getArquivo().getOriginalFilename());

        var arquivoFoto = Path.of("S:\\catalogo", nomeArquivo);

        try {
            request.getArquivo().transferTo(arquivoFoto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
