package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.dto.request.RestauranteRequest;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
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

    public void copyToDomain(RestauranteRequest restauranteRequest, Restaurante restaurante) {
        //evitar HibernateException: identifier of an instance of Cozinha was altered from X to Y
        restaurante.setCozinha(new Cozinha());

        if (restaurante.getEndereco() != null) {
            restaurante.getEndereco().setCidade(new Cidade());
        }

        modelMapper.map(restauranteRequest, restaurante);
    }

}
