package com.ibm.gerenciador_tarefas.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ibm.gerenciador_tarefas.dtos.StatusTarefa;
import com.ibm.gerenciador_tarefas.dtos.TarefaCreateDTO;
import com.ibm.gerenciador_tarefas.dtos.TarefaResponseDTO;
import com.ibm.gerenciador_tarefas.dtos.TarefaUpdateDTO;
import com.ibm.gerenciador_tarefas.services.TarefaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class TarefaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TarefaService tarefaService;

    @InjectMocks
    private TarefaController tarefaController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(tarefaController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void deveRetornarListaDeTarefas() throws Exception {
        List<TarefaResponseDTO> tarefas = Arrays.asList(new TarefaResponseDTO(1L, "Tarefa 1", LocalDate.now(), LocalDate.now().plusDays(20), StatusTarefa.PENDENTE));
        when(tarefaService.listarTodas()).thenReturn(tarefas);

        mockMvc.perform(get("/api/tarefas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].descricao").value("Tarefa 1"));
    }

    @Test
    void deveRetornarTarefaQuandoEncontrada() throws Exception {
        TarefaResponseDTO tarefa = new TarefaResponseDTO(1L, "Tarefa 1", LocalDate.now(), LocalDate.now().plusDays(10), StatusTarefa.PENDENTE);
        when(tarefaService.buscarPorId(1L)).thenReturn(tarefa);

        mockMvc.perform(get("/api/tarefas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.descricao").value("Tarefa 1"));
    }

    @Test
    void DeveRetornarListaDeTarefasComStatusEspecifico() throws Exception {
        List<TarefaResponseDTO> tarefas = Arrays.asList(new TarefaResponseDTO(2L, "Tarefa 2", LocalDate.now(), LocalDate.now().plusDays(5), StatusTarefa.CONCLUIDA));
        when(tarefaService.listarPorStatus(StatusTarefa.CONCLUIDA)).thenReturn(tarefas);

        mockMvc.perform(get("/api/tarefas/status/CONCLUIDA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2L))
                .andExpect(jsonPath("$[0].status").value("CONCLUIDA"));
    }

    @Test
    void deveCriarTarefaComSucesso() throws Exception {
        TarefaCreateDTO novaTarefa = new TarefaCreateDTO("Nova Tarefa", LocalDate.now(), LocalDate.now().plusDays(5), StatusTarefa.CONCLUIDA);
        TarefaResponseDTO resposta = new TarefaResponseDTO(3L, "Nova Tarefa", LocalDate.now(), LocalDate.now().plusDays(5), StatusTarefa.PENDENTE);
        when(tarefaService.criarTarefa(any())).thenReturn(resposta);

        mockMvc.perform(post("/api/tarefas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novaTarefa)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.descricao").value("Nova Tarefa"));
    }

    @Test
    void deveAtualizarTarefaExistente() throws Exception {
        TarefaUpdateDTO atualizacao = new TarefaUpdateDTO(1L, "Tarefa Atualizada", LocalDate.now(), LocalDate.now().plusDays(5), StatusTarefa.CONCLUIDA);
        TarefaResponseDTO resposta = new TarefaResponseDTO(1L, "Tarefa Atualizada", LocalDate.now(), LocalDate.now().plusDays(5), StatusTarefa.CONCLUIDA);
        when(tarefaService.atualizarTarefa(eq(1L), any())).thenReturn(resposta);

        mockMvc.perform(put("/api/tarefas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(atualizacao)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricao").value("Tarefa Atualizada"))
                .andExpect(jsonPath("$.status").value("CONCLUIDA"));
    }

    @Test
    void deveDeletarComSucesso() throws Exception {
        doNothing().when(tarefaService).deletarTarefa(1L);

        mockMvc.perform(delete("/api/tarefas/1"))
                .andExpect(status().isNoContent());
    }
}
