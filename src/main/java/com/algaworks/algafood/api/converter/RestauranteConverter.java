package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.dto.request.RestauranteRequest;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.stereotype.Component;

@Component
public class RestauranteConverter {

    public Restaurante toDomain(RestauranteRequest restauranteRequest) {
        var restaurante = new Restaurante();
        restaurante.setNome(restauranteRequest.getNome());
        restaurante.setTaxaFrete(restauranteRequest.getTaxaFrete());

        var cozinha = new Cozinha();
        cozinha.setId(restauranteRequest.getCozinha().getId());
        restaurante.setCozinha(cozinha);

        return restaurante;
    }

}
