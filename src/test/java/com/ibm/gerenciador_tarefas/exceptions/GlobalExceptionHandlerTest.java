package com.ibm.gerenciador_tarefas.exceptions;

import com.ibm.gerenciador_tarefas.dtos.StatusTarefa;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;
    private HttpServletRequest request;
    private MethodArgumentNotValidException methodArgumentNotValidException;
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
        bindingResult = mock(BindingResult.class);
        methodArgumentNotValidException = new MethodArgumentNotValidException(null, bindingResult);
        request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/test");
    }

    @Test
    void testHandleValidationExceptions() {
        // Simulando erro de validação para dois campos
        when(bindingResult.getFieldErrors()).thenReturn(List.of(
                new FieldError("tarefa", "titulo", "O título é obrigatório"),
                new FieldError("tarefa", "descricao", "A descrição deve ter pelo menos 10 caracteres")
        ));

        ResponseEntity<Map<String, String>> response =
                exceptionHandler.handleValidationExceptions(methodArgumentNotValidException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("O título é obrigatório", response.getBody().get("titulo"));
        assertEquals("A descrição deve ter pelo menos 10 caracteres", response.getBody().get("descricao"));
    }

    @Test
    void testHandleListaNaoEncontradaException() {
        ListaNaoEncontradaException ex = new ListaNaoEncontradaException("Lista não encontrada");

        ResponseEntity<ErroResponse> response = exceptionHandler.handleListaNaoEncontradaException(ex, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Lista Não Encontrada", response.getBody().getError());
        assertEquals(ex.getMessage(), response.getBody().getMessage());
        assertEquals("/api/test", response.getBody().getPath());
    }

    @Test
    void testHandleValidacaoException() {
        ValidacaoException ex = new ValidacaoException("Erro de validação");

        ResponseEntity<ErroResponse> response = exceptionHandler.handleValidacaoException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro de Validação", response.getBody().getError());
        assertEquals(ex.getMessage(), response.getBody().getMessage());
        assertEquals("/api/test", response.getBody().getPath());
    }

    @Test
    void testHandleNenhumaTarefaEncontradaException() {
        NenhumaTarefaEncontradaException ex = new NenhumaTarefaEncontradaException(StatusTarefa.PENDENTE);

        ResponseEntity<ErroResponse> response = exceptionHandler.handleNenhumaTarefaEncontradaException(ex, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Nenhuma Tarefa Encontrada", response.getBody().getError());
        assertEquals(ex.getMessage(), response.getBody().getMessage());
        assertEquals("/api/test", response.getBody().getPath());
    }

    @Test
    void testHandleTarefaNaoEncontradaException() {
        TarefaNaoEncontradaException ex = new TarefaNaoEncontradaException(1L);

        ResponseEntity<ErroResponse> response = exceptionHandler.handleTarefaNaoEncontrada(ex, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Tarefa Não Encontrada", response.getBody().getError());
        assertEquals(ex.getMessage(), response.getBody().getMessage());
        assertEquals("/api/test", response.getBody().getPath());
    }

    @Test
    void testHandleDataInvalidaException() {
        DataInvalidaException ex = new DataInvalidaException();

        ResponseEntity<ErroResponse> response = exceptionHandler.handleDataInvalida(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Data Inválida", response.getBody().getError());
        assertEquals(ex.getMessage(), response.getBody().getMessage());
        assertEquals("/api/test", response.getBody().getPath());
    }

    @Test
    void testHandleGenericException() {
        Exception ex = new Exception("Erro genérico");

        ResponseEntity<ErroResponse> response = exceptionHandler.handleGenericException(ex, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro Inesperado", response.getBody().getError());
        assertEquals("Erro genérico", response.getBody().getMessage());
        assertEquals("/api/test", response.getBody().getPath());
    }
}