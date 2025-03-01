package com.ibm.gerenciador_tarefas.exceptions;

public class TarefaNaoEncontradaException extends RuntimeException {
    public TarefaNaoEncontradaException(Long id) {
        super("Tarefa com ID " + id + " não encontrada.");
    }
}

