package com.murillo.algafood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CozinhaIdInputModel {

    @NotNull
    private Long id;
}
