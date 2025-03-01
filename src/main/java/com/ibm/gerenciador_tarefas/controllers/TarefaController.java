package com.ibm.gerenciador_tarefas.controllers;

import com.ibm.gerenciador_tarefas.dtos.StatusTarefa;
import com.ibm.gerenciador_tarefas.dtos.TarefaCreateDTO;
import com.ibm.gerenciador_tarefas.dtos.TarefaResponseDTO;
import com.ibm.gerenciador_tarefas.dtos.TarefaUpdateDTO;
import com.ibm.gerenciador_tarefas.services.TarefaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tarefas")
@CrossOrigin(origins = "http://localhost:8080")
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @Operation(summary = "Listar todas as tarefas", description = "Retorna uma lista de todas as tarefas cadastradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tarefas retornada com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @GetMapping
    public ResponseEntity<List<TarefaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(tarefaService.listarTodas());
    }

    @Operation(summary = "Buscar tarefa por ID", description = "Retorna os detalhes de uma tarefa com base no seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa encontrada e retornada."),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TarefaResponseDTO> buscarPorId(@Parameter(description = "ID da tarefa a ser buscada.") @PathVariable Long id) {
        return ResponseEntity.ok(tarefaService.buscarPorId(id));
    }

    @Operation(summary = "Listar tarefas por status", description = "Retorna uma lista de tarefas com base no status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tarefas com status especificado retornada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Status inválido."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<List<TarefaResponseDTO>> listarPorStatus(@Parameter(description = "Status da tarefa.") @PathVariable StatusTarefa status) {
        return ResponseEntity.ok(tarefaService.listarPorStatus(status));
    }

    @Operation(summary = "Criar uma nova tarefa", description = "Cria uma nova tarefa no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para criar a tarefa."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @PostMapping
    public ResponseEntity<TarefaResponseDTO> criarTarefa(@Valid @RequestBody TarefaCreateDTO dto) {
        TarefaResponseDTO tarefaCriada = tarefaService.criarTarefa(dto);
        URI location = URI.create("/api/tarefas/" + tarefaCriada.id()); // Define a URI do recurso criado
        return ResponseEntity.created(location).body(tarefaCriada);
    }

    @Operation(summary = "Atualizar tarefa existente", description = "Atualiza os detalhes de uma tarefa existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TarefaResponseDTO> atualizarTarefa(
            @Parameter(description = "ID da tarefa a ser atualizada.") @PathVariable Long id,
            @Valid @RequestBody TarefaUpdateDTO dto) {
        return ResponseEntity.ok(tarefaService.atualizarTarefa(id, dto));
    }

    @Operation(summary = "Deletar tarefa", description = "Deleta uma tarefa do sistema com base no seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tarefa deletada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@Parameter(description = "ID da tarefa a ser deletada.") @PathVariable Long id) {
        tarefaService.deletarTarefa(id);
        return ResponseEntity.noContent().build();
    }
}
