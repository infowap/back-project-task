package com.ibm.gerenciador_tarefas.controllers;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class TarefaControllerTest {
    public static final LocalDate DATA_INICIO =  LocalDate.of(2025, 12, 30);
    public static final LocalDate DATA_FIM =  LocalDate.of(2025, 12, 31);

    @Mock
    private TarefaService tarefaService;

    @InjectMocks
    private TarefaController tarefaController;

    private TarefaResponseDTO tarefaResponseDTO;
    private TarefaCreateDTO tarefaCreateDTO;
    private TarefaUpdateDTO tarefaUpdateDTO;

    @BeforeEach
    void setUp() {

        tarefaResponseDTO = new TarefaResponseDTO(
                1L,
                "Tarefa de Teste",
                DATA_INICIO,
                DATA_FIM,
                StatusTarefa.PENDENTE
        );

        tarefaCreateDTO = new TarefaCreateDTO(
                "Tarefa de Teste",
                DATA_INICIO,
                DATA_FIM,
                StatusTarefa.PENDENTE
        );

        tarefaUpdateDTO = new TarefaUpdateDTO(
                1L,
                "Tarefa de Teste",
                DATA_INICIO,
                DATA_FIM,
                StatusTarefa.PENDENTE
        );
    }

    @Test
    void listarTodas_DeveRetornarListaDeTarefas() {
        List<TarefaResponseDTO> tarefas = Collections.singletonList(tarefaResponseDTO);

        when(tarefaService.listarTodas()).thenReturn(tarefas);

        ResponseEntity<List<TarefaResponseDTO>> response = tarefaController.listarTodas();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tarefas, response.getBody());
        verify(tarefaService, times(1)).listarTodas();
    }

    @Test
    void buscarPorId_ComIdValido_DeveRetornarTarefa() {
        Long id = 1L;

        when(tarefaService.buscarPorId(id)).thenReturn(tarefaResponseDTO);

        ResponseEntity<TarefaResponseDTO> response = tarefaController.buscarPorId(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tarefaResponseDTO, response.getBody());
        verify(tarefaService, times(1)).buscarPorId(id);
    }

    @Test
    void listarPorStatus_ComStatusValido_DeveRetornarListaDeTarefas() {
        StatusTarefa status = StatusTarefa.PENDENTE;
        List<TarefaResponseDTO> tarefasPorStatus = Collections.singletonList(tarefaResponseDTO);

        when(tarefaService.listarPorStatus(status)).thenReturn(tarefasPorStatus);

        ResponseEntity<List<TarefaResponseDTO>> response = tarefaController.listarPorStatus(status);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tarefasPorStatus, response.getBody());
        verify(tarefaService, times(1)).listarPorStatus(status);
    }

    @Test
    void criarTarefa_ComDadosValidos_DeveCriarTarefa() {
        when(tarefaService.criarTarefa(tarefaCreateDTO)).thenReturn(tarefaResponseDTO);

        ResponseEntity<TarefaResponseDTO> response = tarefaController.criarTarefa(tarefaCreateDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(tarefaResponseDTO, response.getBody());
        assertNotNull(response.getHeaders().getLocation());
        verify(tarefaService, times(1)).criarTarefa(tarefaCreateDTO);
    }

    @Test
    void atualizarTarefa_ComDadosValidos_DeveAtualizarTarefa() {
        Long id = 1L;
        TarefaResponseDTO tarefaAtualizada = new TarefaResponseDTO(
                1L,
                "Tarefa de Teste",
                DATA_INICIO,
                DATA_FIM,
                StatusTarefa.PENDENTE
        );

        when(tarefaService.atualizarTarefa(id, tarefaUpdateDTO)).thenReturn(tarefaAtualizada);

        ResponseEntity<TarefaResponseDTO> response = tarefaController.atualizarTarefa(id, tarefaUpdateDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tarefaAtualizada, response.getBody());
        verify(tarefaService, times(1)).atualizarTarefa(id, tarefaUpdateDTO);
    }

    @Test
    void deletarTarefa_ComIdValido_DeveDeletarTarefa() {
        Long id = 1L;

        doNothing().when(tarefaService).deletarTarefa(id);

        ResponseEntity<Void> response = tarefaController.deletarTarefa(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(tarefaService, times(1)).deletarTarefa(id);
    }
}