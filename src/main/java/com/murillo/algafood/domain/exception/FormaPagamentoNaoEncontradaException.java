package com.murillo.algafood.domain.exception;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {


    private static final long serialVersionUID = -2808127653219027174L;

    public FormaPagamentoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }


    public FormaPagamentoNaoEncontradaException(Long formaPagamentoId) {
        this(String.format("Não existe forma de pagamento com o código %d", formaPagamentoId));
    }
}
