package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.converter.FotoProdutoDTOConverter;
import com.algaworks.algafood.api.converter.ProdutoConverter;
import com.algaworks.algafood.api.converter.ProdutoDTOConverter;
import com.algaworks.algafood.api.dto.FotoProdutoDTO;
import com.algaworks.algafood.api.dto.ProdutoDTO;
import com.algaworks.algafood.api.dto.request.FotoProdutoRequest;
import com.algaworks.algafood.api.dto.request.ProdutoRequest;
import com.algaworks.algafood.api.openapi.controller.ProdutoControllerOpenApi;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProdutoController implements ProdutoControllerOpenApi {

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
    public FotoProdutoDTO atualizarFoto(@PathVariable Long restauranteId,
                                        @PathVariable Long produtoId,
                                        @Valid FotoProdutoRequest request,
                                        @RequestPart(required = true) MultipartFile arquivo) throws IOException {
        var produto = cadastroProdutoService.buscarProduto(produtoId, restauranteId);
        arquivo = request.getArquivo();

        //TODO: Mover para um converter
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

    @GetMapping(path = "/{produtoId}/foto", produces = MediaType.ALL_VALUE)
    public ResponseEntity<?> servirFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                        @RequestHeader("accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
        try {
            var foto = catalogoFotoProdutoService.buscarFoto(restauranteId, produtoId);

            var mediaTypeFoto = MediaType.parseMediaType(foto.getContentType());
            var acceptMediaTypes = MediaType.parseMediaTypes(acceptHeader);

            verificarCompatibilidadeMediaType(mediaTypeFoto, acceptMediaTypes);

            var fotoRecuperada = fotoStorageService.recuperar(foto.getNomeArquivo());

            if(fotoRecuperada.hasUrl()) {
                return ResponseEntity
                        .status(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
                        .build();
            } else {
                return ResponseEntity.ok()
                        .contentType(mediaTypeFoto)
                        .body(new InputStreamResource(fotoRecuperada.getInputStream()));
            }
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/{produtoId}/foto")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        catalogoFotoProdutoService.removerFoto(restauranteId, produtoId);
    }

    private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, List<MediaType> acceptMediaTypes)
            throws HttpMediaTypeNotAcceptableException {
        var isCompativel = acceptMediaTypes.stream()
                .anyMatch(mediaType -> mediaType.isCompatibleWith(mediaTypeFoto));

        if (!isCompativel) throw new HttpMediaTypeNotAcceptableException(acceptMediaTypes);
    }
}
