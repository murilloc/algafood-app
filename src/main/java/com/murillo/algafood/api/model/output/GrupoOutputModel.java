package com.murillo.algafood.api.model.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrupoOutputModel {

    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "Gerente")
    private String nome;
}
