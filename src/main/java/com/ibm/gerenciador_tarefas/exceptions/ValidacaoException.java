package com.ibm.gerenciador_tarefas.exceptions;

import org.springframework.http.HttpStatus;

public class ValidacaoException extends RuntimeException {
    private final HttpStatus status;

    public ValidacaoException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
