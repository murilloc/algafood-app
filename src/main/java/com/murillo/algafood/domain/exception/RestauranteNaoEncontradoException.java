package com.murillo.algafood.domain.exception;


// @ResponseStatus(value = HttpStatus.NOT_FOUND)//, reason = "entidade não encontrada) Desnecessário pois já é definida na classe pau
public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final Long serialVersionUID = 1L;

    public RestauranteNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public RestauranteNaoEncontradoException(Long restauranteId) {
        this(String.format("Não existe restaurante com o código %d", restauranteId));
    }


}
