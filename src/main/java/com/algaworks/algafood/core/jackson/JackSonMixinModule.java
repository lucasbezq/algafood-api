package com.algaworks.algafood.core.jackson;

import com.algaworks.algafood.api.dto.mixin.CidadeMixin;
import com.algaworks.algafood.api.dto.mixin.CozinhaMixin;
import com.algaworks.algafood.api.dto.mixin.RestauranteMixin;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

@Component
public class JackSonMixinModule extends SimpleModule {

    private static final long serialVersionUID = -8245699817522595L;

    public JackSonMixinModule() {
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
    }
}
