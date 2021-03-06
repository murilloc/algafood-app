package com.murillo.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {
    private static final long serialVersionUID = 746553205175549481L;


    public PedidoNaoEncontradoException(String codigo) {
        super(String.format("Não existe pedido com o código %s", codigo));
    }


}
