package com.ibm.gerenciador_tarefas.controllers;

import com.ibm.gerenciador_tarefas.dtos.StatusTarefa;
import com.ibm.gerenciador_tarefas.dtos.TarefaCreateDTO;
import com.ibm.gerenciador_tarefas.dtos.TarefaResponseDTO;
import com.ibm.gerenciador_tarefas.dtos.TarefaUpdateDTO;
import com.ibm.gerenciador_tarefas.services.TarefaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tarefas")
@CrossOrigin(origins = "http://localhost:8080")
public class TarefaController {
    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @GetMapping
    public ResponseEntity<List<TarefaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(tarefaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarefaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tarefaService.buscarPorId(id));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TarefaResponseDTO>> listarPorStatus(@PathVariable StatusTarefa status) {
        return ResponseEntity.ok(tarefaService.listarPorStatus(status));
    }

    @PostMapping
    public ResponseEntity<TarefaResponseDTO> criarTarefa(@RequestBody TarefaCreateDTO dto) {
        return ResponseEntity.ok(tarefaService.criarTarefa(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarefaResponseDTO> atualizarTarefa(@PathVariable Long id, @RequestBody TarefaUpdateDTO dto) {
        return ResponseEntity.ok(tarefaService.atualizarTarefa(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        tarefaService.deletarTarefa(id);
        return ResponseEntity.noContent().build();
    }
}

