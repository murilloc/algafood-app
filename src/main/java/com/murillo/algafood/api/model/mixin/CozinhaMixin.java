package com.murillo.algafood.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.murillo.algafood.domain.model.Restaurante;

import java.util.List;

@Deprecated
public abstract class CozinhaMixin {

    @JsonIgnore
    private List<Restaurante> restaurantes;
}
