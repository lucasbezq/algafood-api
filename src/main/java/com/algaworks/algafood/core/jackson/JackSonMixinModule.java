package com.algaworks.algafood.core.jackson;

import com.algaworks.algafood.api.dto.mixin.CidadeMixin;
import com.algaworks.algafood.domain.model.Cidade;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

@Component
public class JackSonMixinModule extends SimpleModule {

    private static final long serialVersionUID = -8245699817522595L;

    public JackSonMixinModule() {
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
    }
}
