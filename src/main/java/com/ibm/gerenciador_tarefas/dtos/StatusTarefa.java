package com.ibm.gerenciador_tarefas.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum StatusTarefa {
    @JsonProperty("EXECUTANDO")
    EXECUTANDO,

    @JsonProperty("CONCLUIDA")
    CONCLUIDA,

    @JsonProperty("PENDENTE")
    PENDENTE
}
