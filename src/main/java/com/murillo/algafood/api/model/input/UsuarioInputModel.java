package com.murillo.algafood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioInputModel {

    @NotBlank
    private String nome;

    @Email
    @NotBlank
    private String email;


}
