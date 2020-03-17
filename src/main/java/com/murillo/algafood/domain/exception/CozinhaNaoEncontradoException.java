package com.murillo.algafood.domain.exception;


// @ResponseStatus(value = HttpStatus.NOT_FOUND)//, reason = "entidade não encontrada) Desnecessário pois já é definida na classe pau
public class CozinhaNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final Long serialVersionUID = 1L;

    public CozinhaNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public CozinhaNaoEncontradoException(Long cozinhaId) {
        this(String.format("Não existe cozinha com o código %d", cozinhaId));
    }


}
