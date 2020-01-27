package com.murillo.algafood.domain.exception;

public class EntidadeNapEncontradaException extends RuntimeException {

    private static final Long serialVersionUID = 1L;

    public EntidadeNapEncontradaException(String message) {
        super(message);
    }
}
