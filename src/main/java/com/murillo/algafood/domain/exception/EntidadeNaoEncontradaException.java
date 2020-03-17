package com.murillo.algafood.domain.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)//, reason = "entidade não encontrada)
public abstract class EntidadeNaoEncontradaException extends NegocioException {

    private static final Long serialVersionUID = 1L;

    public EntidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

}
