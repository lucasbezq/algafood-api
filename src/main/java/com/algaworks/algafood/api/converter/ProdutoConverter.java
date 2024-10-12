package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.dto.request.ProdutoRequest;
import com.algaworks.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoConverter {

    @Autowired
    private ModelMapper modelMapper;

    public Produto toDomain(ProdutoRequest produtoRequest) {
        return modelMapper.map(produtoRequest, Produto.class);
    }

    public void copyToDomain(ProdutoRequest produtoRequest, Produto produto) {
        modelMapper.map(produtoRequest, produto);
    }

}
