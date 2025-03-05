package com.ibm.gerenciador_tarefas.dtos;

import com.ibm.gerenciador_tarefas.entities.Tarefa;

import java.time.LocalDate;

public record TarefaResponseDTO(

        Long id,
        String descricao,
        LocalDate dataInicio,
        LocalDate dataFim,
        StatusTarefa status
) {
    public static TarefaResponseDTO fromEntity(Tarefa tarefa) {
        return new TarefaResponseDTO(
                tarefa.getId(),
                tarefa.getDescricao(),
                tarefa.getDataInicio(),
                tarefa.getDataFim(),
                tarefa.getStatus()
        );
    }
}
