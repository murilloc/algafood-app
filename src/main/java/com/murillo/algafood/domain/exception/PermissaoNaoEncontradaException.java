package com.murillo.algafood.domain.exception;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 7270582440500082521L;

    public PermissaoNaoEncontradaException(String message) {
        super(message);
    }

    public PermissaoNaoEncontradaException(Long permissaoId) {
        this(String.format("Não existe permissão com o código %d", permissaoId));
    }

}
