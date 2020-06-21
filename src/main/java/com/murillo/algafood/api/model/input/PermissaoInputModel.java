package com.murillo.algafood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PermissaoInputModel {

    @NotBlank
    private String nome;

    private String descricao;

}
