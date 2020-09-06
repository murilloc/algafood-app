package com.murillo.algafood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class FormaPagamentoInputModel {

    @ApiModelProperty(example = "Cartão de crédito")
    @NotBlank
    private String descricao;

}
