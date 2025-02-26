package com.ibm.gerenciador_tarefas.services;

import com.ibm.gerenciador_tarefas.dtos.StatusTarefa;
import com.ibm.gerenciador_tarefas.dtos.TarefaCreateDTO;
import com.ibm.gerenciador_tarefas.dtos.TarefaResponseDTO;
import com.ibm.gerenciador_tarefas.dtos.TarefaUpdateDTO;

import java.util.List;

public interface TarefaService {
    TarefaResponseDTO criarTarefa(TarefaCreateDTO dto);
    TarefaResponseDTO atualizarTarefa(Long id, TarefaUpdateDTO dto);
    void deletarTarefa(Long id);
    TarefaResponseDTO buscarPorId(Long id);
    List<TarefaResponseDTO> listarTodas();
    List<TarefaResponseDTO> listarPorStatus(StatusTarefa status);
}

