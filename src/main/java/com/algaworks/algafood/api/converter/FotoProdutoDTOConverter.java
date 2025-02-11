package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.dto.CidadeDTO;
import com.algaworks.algafood.api.dto.FotoProdutoDTO;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FotoProdutoDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    public FotoProdutoDTO toDTO(FotoProduto foto) {
        return modelMapper.map(foto, FotoProdutoDTO.class);
    }

}
