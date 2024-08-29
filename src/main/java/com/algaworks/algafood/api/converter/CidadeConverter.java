package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.dto.request.CidadeRequest;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeConverter {

    @Autowired
    private ModelMapper modelMapper;

    public Cidade toDomain(CidadeRequest cidadeRequest) {
        return modelMapper.map(cidadeRequest, Cidade.class);
    }

    public void copyToDomain(CidadeRequest cidadeRequest, Cidade cidade) {
        cidade.setEstado(new Estado());
        modelMapper.map(cidadeRequest, cidade);
    }

}
