package com.murillo.algafood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EstadoIdInputModel {

    @NotNull
    @ApiModelProperty(example = "1")
    private Long id;
}
