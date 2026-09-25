package com.algaworks.algafood.api.v1.converter;

import com.algaworks.algafood.api.v1.dto.request.CozinhaRequest;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CozinhaConverter {

    @Autowired
    private ModelMapper modelMapper;

    public Cozinha toDomain(CozinhaRequest cozinhaRequest) {
        return modelMapper.map(cozinhaRequest, Cozinha.class);
    }

    public void copyToDomain(CozinhaRequest cozinhaRequest, Cozinha cozinha) {
        modelMapper.map(cozinhaRequest, cozinha);
    }

}
