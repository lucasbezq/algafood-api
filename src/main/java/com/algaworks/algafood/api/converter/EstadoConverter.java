package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.dto.request.EstadoRequest;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EstadoConverter {

    @Autowired
    private ModelMapper modelMapper;

    public Estado toDomain(EstadoRequest estadoRequest) {
        return modelMapper.map(estadoRequest, Estado.class);
    }

    public void copyToDomain(EstadoRequest estadoRequest, Estado estado) {
        modelMapper.map(estadoRequest, estado);
    }
}
