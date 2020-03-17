package com.murillo.algafood.domain.exception;


// @ResponseStatus(value = HttpStatus.NOT_FOUND)//, reason = "entidade não encontrada) Desnecessário pois já é definida na classe pau
public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final Long serialVersionUID = 1L;

    public EstadoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public EstadoNaoEncontradoException(Long estadoId) {
        this(String.format("Não existe estado com o código %d", estadoId));
    }


}
