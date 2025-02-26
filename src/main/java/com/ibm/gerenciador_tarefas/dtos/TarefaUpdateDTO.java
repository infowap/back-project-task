package com.ibm.gerenciador_tarefas.dtos;

import java.time.LocalDate;

public record TarefaUpdateDTO(
        Long id,
        String descricao,
        LocalDate dataInicio,
        LocalDate dataFim,
        StatusTarefa status
) {}
