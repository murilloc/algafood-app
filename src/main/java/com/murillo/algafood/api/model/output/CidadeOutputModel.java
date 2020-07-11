package com.murillo.algafood.api.model.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeOutputModel {

    private Long id;
    private String nome;
    private EstadoOutputModel estado;

}
