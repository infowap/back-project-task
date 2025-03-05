package com.ibm.gerenciador_tarefas.dtos;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record TarefaCreateDTO(

        @NotNull @NotEmpty @NotBlank @Size(min = 3, max = 250, message = "A descrição deve ter entre 3 e 250 caracteres")
        String descricao,
        @NotNull(message = "A data de início não pode ser nula")
        LocalDate dataInicio,
        @NotNull(message = "A data de fim não pode ser nula")
        LocalDate dataFim,
        @NotNull(message = "O status não pode ser nulo")
        StatusTarefa status
) {}
