package com.murillo.algafood.domain.exception;


public abstract class EntidadeNaoEncontradaException extends NegocioException {

    private static final Long serialVersionUID = 1L;

    public EntidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

}
