package com.ibm.gerenciador_tarefas.exceptions;

public class ListaNaoEncontradaException extends RuntimeException {
    public ListaNaoEncontradaException(String status) {
        super("Lista com status " + status + " não encontrada.");
    }
}
