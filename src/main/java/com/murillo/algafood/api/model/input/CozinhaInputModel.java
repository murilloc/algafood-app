package com.murillo.algafood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CozinhaInputModel {

    @ApiModelProperty(example = "Brasileira", required = true)
    @NotBlank
    private String nome;
}
