package com.murillo.algafood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CozinhaInputModel {

    @NotBlank
    private String nome;
}
