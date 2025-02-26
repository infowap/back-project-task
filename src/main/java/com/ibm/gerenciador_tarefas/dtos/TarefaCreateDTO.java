package com.ibm.gerenciador_tarefas.dtos;

import java.time.LocalDate;

public record TarefaCreateDTO(
        String descricao,
        LocalDate dataInicio,
        LocalDate dataFim,
        StatusTarefa status
) {}
