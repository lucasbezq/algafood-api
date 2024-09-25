package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.dto.GrupoDTO;
import com.algaworks.algafood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GrupoDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    public GrupoDTO toDTO(Grupo grupo) {
        return modelMapper.map(grupo, GrupoDTO.class);
    }

    public List<GrupoDTO> toCollectionDTO(List<Grupo> grupos) {
        return grupos.stream()
                .map(grupo -> toDTO(grupo))
                .collect(Collectors.toList());
    }
}
