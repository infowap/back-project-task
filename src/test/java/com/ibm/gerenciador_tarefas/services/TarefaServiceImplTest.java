package com.ibm.gerenciador_tarefas.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.ibm.gerenciador_tarefas.dtos.StatusTarefa;
import com.ibm.gerenciador_tarefas.dtos.TarefaCreateDTO;
import com.ibm.gerenciador_tarefas.dtos.TarefaResponseDTO;
import com.ibm.gerenciador_tarefas.dtos.TarefaUpdateDTO;
import com.ibm.gerenciador_tarefas.entities.Tarefa;
import com.ibm.gerenciador_tarefas.exceptions.DataInvalidaException;
import com.ibm.gerenciador_tarefas.exceptions.TarefaNaoEncontradaException;
import com.ibm.gerenciador_tarefas.repositories.TarefaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Optional;
import java.util.List;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class TarefaServiceTest {

    @Mock
    private TarefaRepository tarefaRepository;

    @InjectMocks
    private TarefaServiceImpl tarefaService;

    private Tarefa tarefa;
    private TarefaCreateDTO tarefaCreateDTO;
    private TarefaUpdateDTO tarefaUpdateDTO;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    @BeforeEach
    void setUp() {
        dataInicio = LocalDate.now();
        dataFim = LocalDate.now().plusDays(3);

        tarefa = new Tarefa("Tarefa Teste", dataInicio, dataFim, StatusTarefa.PENDENTE);
        tarefa.setId(1L);

        tarefaCreateDTO = new TarefaCreateDTO("Tarefa Teste", dataInicio, dataFim, StatusTarefa.PENDENTE);
        tarefaUpdateDTO = new TarefaUpdateDTO(2L, "Tarefa Atualizada", dataInicio, dataFim.plusDays(2), StatusTarefa.CONCLUIDA);
    }

    @Test
    void criarTarefa_DeveRetornarTarefaCriada() {
        when(tarefaRepository.save(any())).thenReturn(tarefa);

        TarefaResponseDTO resposta = tarefaService.criarTarefa(tarefaCreateDTO);

        assertNotNull(resposta);
        assertEquals(tarefa.getDescricao(), resposta.descricao());
        assertEquals(tarefa.getDataInicio(), resposta.dataInicio());
        assertEquals(tarefa.getDataFim(), resposta.dataFim());
    }

    @Test
    void criarTarefa_DeveLancarExcecao_QuandoDataInvalida() {
        TarefaCreateDTO dtoInvalido = new TarefaCreateDTO("Tarefa Inválida", dataFim, dataInicio, StatusTarefa.PENDENTE);

        assertThrows(DataInvalidaException.class, () -> tarefaService.criarTarefa(dtoInvalido));
    }

    @Test
    void atualizarTarefa_DeveRetornarTarefaAtualizada() {
        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa));
        when(tarefaRepository.save(any())).thenReturn(tarefa);

        TarefaResponseDTO resposta = tarefaService.atualizarTarefa(1L, tarefaUpdateDTO);

        assertNotNull(resposta);
        assertEquals(tarefaUpdateDTO.descricao(), resposta.descricao());
        assertEquals(tarefaUpdateDTO.dataInicio(), resposta.dataInicio());
        assertEquals(tarefaUpdateDTO.dataFim(), resposta.dataFim());
    }

    @Test
    void atualizarTarefa_DeveLancarExcecao_QuandoNaoEncontrada() {
        when(tarefaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TarefaNaoEncontradaException.class, () -> tarefaService.atualizarTarefa(1L, tarefaUpdateDTO));
    }

    @Test
    void deletarTarefa_DeveDeletarTarefaComSucesso() {
        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa));
        doNothing().when(tarefaRepository).delete(any());

        assertDoesNotThrow(() -> tarefaService.deletarTarefa(1L));
    }

    @Test
    void deletarTarefa_DeveLancarExcecao_QuandoNaoEncontrada() {
        when(tarefaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TarefaNaoEncontradaException.class, () -> tarefaService.deletarTarefa(1L));
    }

    @Test
    void listarTodas_DeveRetornarListaDeTarefas() {
        List<Tarefa> tarefas = Arrays.asList(tarefa);
        when(tarefaRepository.findAll()).thenReturn(tarefas);

        List<TarefaResponseDTO> resposta = tarefaService.listarTodas();

        assertFalse(resposta.isEmpty());
        assertEquals(1, resposta.size());
        assertEquals(tarefa.getDescricao(), resposta.get(0).descricao());
    }
}
