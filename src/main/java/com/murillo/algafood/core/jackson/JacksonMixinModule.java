package com.murillo.algafood.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.murillo.algafood.api.model.mixin.CidadeMixin;
import com.murillo.algafood.api.model.mixin.CozinhaMixin;
import com.murillo.algafood.api.model.mixin.RestauranteMixin;
import com.murillo.algafood.domain.model.Cidade;
import com.murillo.algafood.domain.model.Cozinha;
import com.murillo.algafood.domain.model.Restaurante;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {
    private static final long serialVersionUID = 6118235691651317262L;

    public JacksonMixinModule() {
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
    }
}
