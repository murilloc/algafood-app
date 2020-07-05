package com.murillo.algafood.api.model.output;

import com.fasterxml.jackson.annotation.JsonView;
import com.murillo.algafood.api.model.view.RestauranteView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaOutputModel {

    @JsonView(RestauranteView.Resumo.class)
    private Long id;

    @JsonView(RestauranteView.Resumo.class)
    private String nome;
}
