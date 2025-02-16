package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.converter.FotoProdutoDTOConverter;
import com.algaworks.algafood.api.converter.ProdutoConverter;
import com.algaworks.algafood.api.converter.ProdutoDTOConverter;
import com.algaworks.algafood.api.dto.FotoProdutoDTO;
import com.algaworks.algafood.api.dto.ProdutoDTO;
import com.algaworks.algafood.api.dto.request.FotoProdutoRequest;
import com.algaworks.algafood.api.dto.request.ProdutoRequest;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
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

    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProdutoService;

    @Autowired
    private FotoProdutoDTOConverter fotoProdutoDTOConverter;

    @Autowired
    private FotoStorageService fotoStorageService;

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
    public FotoProdutoDTO atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                        @Valid FotoProdutoRequest request) throws IOException {
        //TODO: Mover para um converter
        var produto = cadastroProdutoService.buscarProduto(produtoId, restauranteId);
        var arquivo = request.getArquivo();

        var foto = new FotoProduto();
        foto.setProduto(produto);
        foto.setDescricao(request.getDescricao());
        foto.setContentType(arquivo.getContentType());
        foto.setTamanho(arquivo.getSize());
        foto.setNomeArquivo(arquivo.getOriginalFilename());

        var fotoSalva = catalogoFotoProdutoService.salvar(foto, arquivo.getInputStream());
        return fotoProdutoDTOConverter.toDTO(fotoSalva);
    }

    @GetMapping(path = "/{produtoId}/foto", produces = MediaType.APPLICATION_JSON_VALUE)
    public FotoProdutoDTO buscarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        var foto = catalogoFotoProdutoService.buscarFoto(restauranteId, produtoId);
        return fotoProdutoDTOConverter.toDTO(foto);
    }

    @GetMapping(path = "/{produtoId}/foto", produces =  MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> servirFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        try {
            var foto = catalogoFotoProdutoService.buscarFoto(restauranteId, produtoId);
            var inputStream = fotoStorageService.recuperar(foto.getNomeArquivo());

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(new InputStreamResource(inputStream));
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
