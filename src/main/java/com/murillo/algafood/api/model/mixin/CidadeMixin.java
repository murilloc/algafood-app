package com.murillo.algafood.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.murillo.algafood.domain.model.Estado;

@Deprecated
public abstract  class CidadeMixin {

    @JsonIgnoreProperties(value = {"nome"}, allowGetters = true)
    private Estado estado;
}
