package com.ibm.gerenciador_tarefas.services;

import com.ibm.gerenciador_tarefas.dtos.*;
import com.ibm.gerenciador_tarefas.entities.Tarefa;
import com.ibm.gerenciador_tarefas.exceptions.*;
import com.ibm.gerenciador_tarefas.repositories.TarefaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TarefaServiceImplTest {

    public static final LocalDate DATA_INICIO =  LocalDate.of(2025, 12, 30);
    public static final LocalDate DATA_FIM =  LocalDate.of(2025, 12, 31);

    @Mock
    private TarefaRepository tarefaRepository;

    @InjectMocks
    private TarefaServiceImpl tarefaService;

    private Tarefa tarefa;
    private TarefaCreateDTO createDTO;
    private TarefaUpdateDTO updateDTO;

    @BeforeEach
    void setUp() {
        tarefa = new Tarefa("Descrição", DATA_INICIO, DATA_FIM, StatusTarefa.PENDENTE);
        createDTO = new TarefaCreateDTO("Descrição", DATA_INICIO, DATA_FIM, StatusTarefa.PENDENTE);
        updateDTO = new TarefaUpdateDTO(1L,"Nova Descrição", DATA_INICIO, DATA_FIM.plusDays(2), StatusTarefa.CONCLUIDA);
    }

    @Test
    void deveCriarTarefaComSucesso() {
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefa);

        TarefaResponseDTO response = tarefaService.criarTarefa(createDTO);

        assertNotNull(response);
        assertEquals(createDTO.descricao(), response.descricao());
        assertEquals(createDTO.dataInicio(), response.dataInicio());
        assertEquals(createDTO.dataFim(), response.dataFim());
        assertEquals(createDTO.status(), response.status());
    }

    @Test
    void deveLancarExcecaoQuandoDataInvalidaAoCriar() {
        createDTO = new TarefaCreateDTO("Descrição", LocalDate.now(), LocalDate.now().minusDays(1), StatusTarefa.PENDENTE);

        assertThrows(DataInvalidaException.class, () -> tarefaService.criarTarefa(createDTO));
    }

    @Test
    void deveAtualizarTarefaComSucesso() {
        when(tarefaRepository.findById(anyLong())).thenReturn(Optional.of(tarefa));
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefa);

        TarefaResponseDTO response = tarefaService.atualizarTarefa(1L, updateDTO);

        assertNotNull(response);
        assertEquals(updateDTO.descricao(), response.descricao());
    }

    @Test
    void deveLancarExcecaoQuandoDataInvalidaAoAtualizar() {
        updateDTO = new TarefaUpdateDTO(1L,"Nova Descrição", DATA_INICIO, DATA_INICIO.minusDays(4), StatusTarefa.CONCLUIDA);

        assertThrows(DataInvalidaException.class, () -> tarefaService.atualizarTarefa(1L, updateDTO));
    }

    @Test
    void deveLancarExcecaoQuandoTarefaNaoEncontradaAoAtualizar() {
        when(tarefaRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TarefaNaoEncontradaException.class, () -> tarefaService.atualizarTarefa(1L, updateDTO));
    }

    @Test
    void deveDeletarTarefaComSucesso() {
        when(tarefaRepository.findById(anyLong())).thenReturn(Optional.of(tarefa));
        doNothing().when(tarefaRepository).delete(any(Tarefa.class));

        assertDoesNotThrow(() -> tarefaService.deletarTarefa(1L));
    }

    @Test
    void deveLancarExcecaoQuandoTarefaNaoEncontradaAoDeletar() {
        when(tarefaRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TarefaNaoEncontradaException.class, () -> tarefaService.deletarTarefa(1L));
    }

    @Test
    void deveBuscarTarefaPorIdComSucesso() {
        when(tarefaRepository.findById(anyLong())).thenReturn(Optional.of(tarefa));

        TarefaResponseDTO response = tarefaService.buscarPorId(1L);

        assertNotNull(response);
        assertEquals(tarefa.getDescricao(), response.descricao());
    }

    @Test
    void deveLancarExcecaoQuandoTarefaNaoEncontradaAoBuscarPorId() {
        when(tarefaRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TarefaNaoEncontradaException.class, () -> tarefaService.buscarPorId(1L));
    }

    @Test
    void deveListarTodasTarefasComSucesso() {
        when(tarefaRepository.findAll()).thenReturn(List.of(tarefa));

        List<TarefaResponseDTO> response = tarefaService.listarTodas();

        assertFalse(response.isEmpty());
    }

    @Test
    void deveListarTarefasPorStatusComSucesso() {
        when(tarefaRepository.findByStatus(any(StatusTarefa.class))).thenReturn(List.of(tarefa));

        List<TarefaResponseDTO> response = tarefaService.listarPorStatus(StatusTarefa.PENDENTE);

        assertFalse(response.isEmpty());
    }

    @Test
    void deveLancarExcecaoQuandoNenhumaTarefaEncontradaPorStatus() {
        when(tarefaRepository.findByStatus(any(StatusTarefa.class))).thenReturn(List.of());

        assertThrows(NenhumaTarefaEncontradaException.class, () -> tarefaService.listarPorStatus(StatusTarefa.PENDENTE));
    }
}