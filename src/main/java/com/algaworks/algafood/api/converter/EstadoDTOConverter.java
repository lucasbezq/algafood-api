package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.dto.EstadoDTO;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstadoDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    public EstadoDTO toDTO(Estado estado) {
        return modelMapper.map(estado, EstadoDTO.class);
    }

    public List<EstadoDTO> toCollectionDTO(List<Estado> estados) {
        return estados.stream()
                .map(estado -> toDTO(estado))
                .collect(Collectors.toList());
    }
}
