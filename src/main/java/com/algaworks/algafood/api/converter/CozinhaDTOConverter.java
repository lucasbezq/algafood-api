package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.dto.CozinhaDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CozinhaDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    public CozinhaDTO toDTO(Cozinha cozinha) {
        return modelMapper.map(cozinha, CozinhaDTO.class);
    }

    public List<CozinhaDTO> toCollectionDTO(List<Cozinha> cozinhas) {
        return cozinhas.stream()
                .map(cozinha -> toDTO(cozinha))
                .collect(Collectors.toList());
    }

}
