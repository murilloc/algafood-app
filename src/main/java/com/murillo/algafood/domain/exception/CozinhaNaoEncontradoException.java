package com.murillo.algafood.domain.exception;


public class CozinhaNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final Long serialVersionUID = 1L;

    public CozinhaNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public CozinhaNaoEncontradoException(Long cozinhaId) {
        this(String.format("Não existe cozinha com o código %d", cozinhaId));
    }


}
