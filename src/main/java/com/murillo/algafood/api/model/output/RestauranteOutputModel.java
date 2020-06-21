package com.murillo.algafood.api.model.output;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteOutputModel {

    private Long id;
    private String nome;
    private BigDecimal taxaFrete;
    private CozinhaOutputModel cozinha;
    private Boolean ativo;
    private Boolean aberto;
    private EnderecoOutputModel endereco;
}
