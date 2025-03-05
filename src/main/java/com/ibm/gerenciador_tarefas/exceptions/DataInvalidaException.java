package com.ibm.gerenciador_tarefas.exceptions;

public class DataInvalidaException extends RuntimeException {
    public DataInvalidaException() {
        super("A data de início deve ser anterior à data de fim.");
    }
}

