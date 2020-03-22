package com.murillo.algafood.domain.exception;


public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final Long serialVersionUID = 1L;

    public EstadoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public EstadoNaoEncontradoException(Long estadoId) {
        this(String.format("Não existe estado com o código %d", estadoId));
    }


}
