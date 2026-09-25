package com.algaworks.algafood.api.v1.converter;

import com.algaworks.algafood.api.v1.dto.request.GrupoRequest;
import com.algaworks.algafood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GrupoConverter {

    @Autowired
    private ModelMapper modelMapper;

    public Grupo toDomain(GrupoRequest grupoRequest) {
        return modelMapper.map(grupoRequest, Grupo.class);
    }

    public void copyToDomain(GrupoRequest grupoRequest, Grupo grupo) {
        modelMapper.map(grupoRequest, grupo);
    }

}
