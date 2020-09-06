package com.murillo.algafood.api.model.output;

import com.fasterxml.jackson.annotation.JsonView;
import com.murillo.algafood.api.model.view.RestauranteView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.Example;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaOutputModel {

    @ApiModelProperty(example = "1")
    @JsonView(RestauranteView.Resumo.class)
    private Long id;

    @ApiModelProperty(example= "Brasileira")
    @JsonView(RestauranteView.Resumo.class)
    private String nome;
}
