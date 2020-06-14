package com.murillo.algafood.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {
    private static final long serialVersionUID = 1896465061479679393L;

    public UsuarioNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public UsuarioNaoEncontradoException(Long usuarioId) {
        this(String.format("Não existe usuário com o código %d", usuarioId));
    }
}
