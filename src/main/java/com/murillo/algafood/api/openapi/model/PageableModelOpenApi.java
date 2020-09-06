package com.murillo.algafood.api.openapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel("Pageable")
public class PageableModelOpenApi {

    @ApiModelProperty(example = "0", value = "O número da página começa em 0")
    private int page;
    @ApiModelProperty(example = "10", value = "Quantidade de registros por página")
    private int size;
    @ApiModelProperty(example = "nome,asc", value = "Nome da propriedade para ordenação")
    private List<String> sort;
}
