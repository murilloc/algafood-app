package com.murillo.algafood.domain.exception;

public class EstadoEmUsoException extends EntidadeEmUsoException {

    private static final String MSG_ESTADO_EM_USO = "Não é possível excluir estado id %d pois está sendo usado no momento";

    private static final Long serialVersionUID = 1L;

    public EstadoEmUsoException(String message) {
        super(message);
    }

    public EstadoEmUsoException(Long estadoId) {
        this(String.format(MSG_ESTADO_EM_USO, estadoId));
    }
}
