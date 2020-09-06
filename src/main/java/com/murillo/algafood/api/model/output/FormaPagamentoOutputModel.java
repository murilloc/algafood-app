package com.murillo.algafood.api.model.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoOutputModel {

    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "Cartão de crédito")
    private String descricao;
}
