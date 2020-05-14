package com.murillo.algafood.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.murillo.algafood.domain.model.Cozinha;
import com.murillo.algafood.domain.model.Endereco;
import com.murillo.algafood.domain.model.FormaPagamento;
import com.murillo.algafood.domain.model.Produto;

import java.time.OffsetDateTime;
import java.util.List;

public abstract class RestauranteMixin {


    @JsonIgnoreProperties(value={"nome"}, allowGetters = true)
    private Cozinha cozinha;

    @JsonIgnore
    private Endereco endereco;

    //@JsonIgnore
    private OffsetDateTime dataCadastro;

    //@JsonIgnore
    private OffsetDateTime dataAtualizacao;

    @JsonIgnore
    private List<Produto> produtos;

    @JsonIgnore
    private List<FormaPagamento> formasPagamento;

}
