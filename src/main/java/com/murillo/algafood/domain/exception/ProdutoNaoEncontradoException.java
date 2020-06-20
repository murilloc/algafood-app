package com.murillo.algafood.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = -4718178470249388436L;

    public ProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public ProdutoNaoEncontradoException(Long produtoId) {
        this(String.format("Não existe produto com o código %d", produtoId));
    }

    public ProdutoNaoEncontradoException(Long produtoId , Long restauranteId) {
        this(String.format("Não existe produto com o código %d para o restaurante código %d", produtoId, restauranteId));
    }
}
