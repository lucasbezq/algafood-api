package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.dto.request.CidadeRequest;
import com.algaworks.algafood.api.dto.request.GrupoRequest;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
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
