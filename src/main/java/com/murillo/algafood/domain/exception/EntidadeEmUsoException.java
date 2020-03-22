package com.murillo.algafood.domain.exception;

public class EntidadeEmUsoException extends NegocioException {

    private static final Long serialVersionUID = 1L;

    public EntidadeEmUsoException(String message) {
        super(message);
    }
}
