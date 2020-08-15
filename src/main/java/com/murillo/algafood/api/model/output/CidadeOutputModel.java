package com.murillo.algafood.api.model.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

//@ApiModel(value = "Cidade", description = "Representa uma cidade")
@Getter
@Setter
public class CidadeOutputModel {

    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "Maricá")
    private String nome;
    private EstadoOutputModel estado;

}
