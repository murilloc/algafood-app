package com.murillo.algafood.infra.service.report;

public class ReportException  extends RuntimeException{

    private static final long serialVersionUID = -5555776195550357332L;

    public ReportException(String message) {
        super(message);
    }

    public ReportException(String message, Throwable cause) {
        super(message, cause);
    }
}
