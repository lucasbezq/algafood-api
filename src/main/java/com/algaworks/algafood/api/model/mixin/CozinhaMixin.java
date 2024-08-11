package com.algaworks.algafood.api.model.mixin;

import com.algaworks.algafood.domain.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.ArrayList;
import java.util.List;

@JsonRootName("cozinha")
public abstract class CozinhaMixin {

    @JsonIgnore
    private List<Restaurante> restaurantes;
}