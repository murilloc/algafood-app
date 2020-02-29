package com.murillo.algafood.domain.exception;

public class ViolacaoDeIntegridadeException extends RuntimeException {

    private static final Long serialVersionUID = 1L;

    public ViolacaoDeIntegridadeException(String message) {
        super(message);
    }
}
