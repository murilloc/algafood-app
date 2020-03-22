package com.murillo.algafood.domain.exception;


public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final Long serialVersionUID = 1L;

    public RestauranteNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public RestauranteNaoEncontradoException(Long restauranteId) {
        this(String.format("Não existe restaurante com o código %d", restauranteId));
    }


}
