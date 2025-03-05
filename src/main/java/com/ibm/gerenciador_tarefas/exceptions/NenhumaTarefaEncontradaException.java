package com.ibm.gerenciador_tarefas.exceptions;

import com.ibm.gerenciador_tarefas.dtos.StatusTarefa;

public class NenhumaTarefaEncontradaException extends RuntimeException {
    public NenhumaTarefaEncontradaException(StatusTarefa status) {
        super("Nenhuma tarefa encontrada com o status: " + status);
    }
}