package com.ibm.gerenciador_tarefas.repositories;

import com.ibm.gerenciador_tarefas.dtos.StatusTarefa;
import com.ibm.gerenciador_tarefas.entities.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    List<Tarefa> findByStatus(StatusTarefa status);
}

