package com.murillo.algafood.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = -6296258050487226476L;

    public GrupoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public GrupoNaoEncontradoException(Long grupoId) {
        this(String.format("Não existe grupo com o código %d", grupoId));
    }

    public GrupoNaoEncontradoException(Long grupoId, Long permissaoId) {
        this(String.format("Não existe grupo com o código %d e permissão %id", grupoId, permissaoId));
    }

}
