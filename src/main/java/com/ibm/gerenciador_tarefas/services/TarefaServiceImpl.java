package com.ibm.gerenciador_tarefas.services;

import com.ibm.gerenciador_tarefas.dtos.StatusTarefa;
import com.ibm.gerenciador_tarefas.dtos.TarefaCreateDTO;
import com.ibm.gerenciador_tarefas.dtos.TarefaResponseDTO;
import com.ibm.gerenciador_tarefas.dtos.TarefaUpdateDTO;
import com.ibm.gerenciador_tarefas.entities.Tarefa;
import com.ibm.gerenciador_tarefas.repositories.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TarefaServiceImpl implements TarefaService {
    private final TarefaRepository tarefaRepository;

    @Autowired
    public TarefaServiceImpl(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    @Override
    @Transactional
    public TarefaResponseDTO criarTarefa(TarefaCreateDTO dto) {
        Tarefa tarefa = new Tarefa(dto.descricao(), dto.dataInicio(), dto.dataFim(), dto.status());
        tarefa = tarefaRepository.save(tarefa);
        return TarefaResponseDTO.fromEntity(tarefa);
    }

    @Override
    @Transactional
    public TarefaResponseDTO atualizarTarefa(Long id, TarefaUpdateDTO dto) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        tarefa.setDescricao(dto.descricao());
        tarefa.setDataInicio(dto.dataInicio());
        tarefa.setDataFim(dto.dataFim());
        tarefa.setStatus(dto.status());
        tarefa = tarefaRepository.save(tarefa);
        return TarefaResponseDTO.fromEntity(tarefa);
    }

    @Override
    @Transactional
    public void deletarTarefa(Long id) {
        tarefaRepository.deleteById(id);
    }

    @Override
    public TarefaResponseDTO buscarPorId(Long id) {
        return tarefaRepository.findById(id)
                .map(TarefaResponseDTO::fromEntity)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
    }

    @Override
    public List<TarefaResponseDTO> listarTodas() {
        return tarefaRepository.findAll().stream()
                .map(TarefaResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<TarefaResponseDTO> listarPorStatus(StatusTarefa status) {
        return tarefaRepository.findByStatus(status).stream()
                .map(TarefaResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }
}

