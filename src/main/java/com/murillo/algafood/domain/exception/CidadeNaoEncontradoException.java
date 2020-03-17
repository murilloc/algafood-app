package com.murillo.algafood.domain.exception;


// @ResponseStatus(value = HttpStatus.NOT_FOUND)//, reason = "entidade não encontrada) Desnecessário pois já é definida na classe pau
public class CidadeNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final Long serialVersionUID = 1L;

    public CidadeNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public CidadeNaoEncontradoException(Long cidadeId) {
        this(String.format("Não existe cidade com o código %d", cidadeId));
    }


}
