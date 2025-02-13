package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        var fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);
        if (fotoExistente.isPresent()) produtoRepository.delete(fotoExistente.get());

        produtoRepository.save(foto);
        produtoRepository.flush();

        var novaFoto = FotoStorageService.NovaFoto.builder()
                .nomeArquivo(foto.getNomeArquivo())
                .inputStream(dadosArquivo)
                .build();

        fotoStorageService.armazenar(novaFoto);

        return foto;
    }
}
