package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.FotoProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.InputStream;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FotoStorageService fotoStorageService;

    @Transactional
    public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
        var restauranteId = foto.getRestauranteId();
        var produtoId = foto.getProduto().getId();
        foto.setNomeArquivo(fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo()));
        String nomeArquivoExistente = null;

        var fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);
        if (fotoExistente.isPresent()) {
            nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
            produtoRepository.delete(fotoExistente.get());
        }

        foto = produtoRepository.save(foto);
        produtoRepository.flush();

        var novaFoto = FotoStorageService.NovaFoto.builder()
                .nomeArquivo(foto.getNomeArquivo())
                .inputStream(dadosArquivo)
                .contentType(foto.getContentType())
                .size(foto.getTamanho())
                .build();

        fotoStorageService.substituir(nomeArquivoExistente, novaFoto);

        return foto;
    }

    public FotoProduto buscarFoto(Long restauranteId, Long produtoId) {
        return produtoRepository.findFotoById(restauranteId, produtoId)
                .orElseThrow(() -> new FotoProdutoNaoEncontradoException(produtoId, restauranteId));
    }

    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerFoto(Long restauranteId, Long produtoId) {
        var foto = buscarFoto(restauranteId, produtoId);
        produtoRepository.delete(foto);
        produtoRepository.flush();
        fotoStorageService.remover(foto.getNomeArquivo());
    }

}
