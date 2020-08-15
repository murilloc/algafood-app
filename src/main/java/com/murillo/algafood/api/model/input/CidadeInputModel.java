package com.murillo.algafood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CidadeInputModel {

    @NotBlank
    @ApiModelProperty(example = "Maricá",required = true)
    private String nome;

    @NotNull
    @Valid
    private EstadoIdInputModel estado;
}
