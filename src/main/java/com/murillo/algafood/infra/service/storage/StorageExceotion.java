package com.murillo.algafood.infra.service.storage;

public class StorageExceotion extends RuntimeException {

    private static final long serialVersionUID = -3183760941678442630L;

    public StorageExceotion(String message) {
        super(message);
    }

    public StorageExceotion(String message, Throwable cause) {
        super(message, cause);
    }
}
