package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.dto.request.RestauranteRequest;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteConverter {

    @Autowired
    private ModelMapper modelMapper;

    public Restaurante toDomain(RestauranteRequest restauranteRequest) {
        return modelMapper.map(restauranteRequest, Restaurante.class);
    }

}
