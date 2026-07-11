package br.com.fiap.restaurant_management.infra.web.rest;

import br.com.fiap.restaurant_management.core.controller.CardapioController;
import br.com.fiap.restaurant_management.core.dto.CardapioDTO;
import br.com.fiap.restaurant_management.infra.web.dto.CardapioInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardapioApiControllerTest {

    @Mock
    private CardapioController mockController;

    private CardapioApiController apiController;

    @BeforeEach
    void setUp() {
        apiController = new CardapioApiController(mockController);
    }

    private void setField(Object target, String name, Object value) throws Exception {
        Field f = target.getClass().getDeclaredField(name);
        f.setAccessible(true);
        f.set(target, value);
    }

    private CardapioInput buildInput() throws Exception {
        CardapioInput in = new CardapioInput();
        setField(in, "nome", "Pizza");
        setField(in, "descricao", "Delicious");
        setField(in, "preco", new BigDecimal("10.00"));
        setField(in, "disponibilidadeRestaurante", Boolean.TRUE);
        setField(in, "fotoUrl", "http://img.png");
        return in;
    }

    @Test
    void criar_shouldReturnCreatedWithBody() throws Exception {
        CardapioInput input = buildInput();
        CardapioDTO expected = new CardapioDTO("Pizza", "Delicious", new BigDecimal("10.00"), true, "http://img.png");

        when(mockController.criarCardapio(any(CardapioDTO.class))).thenReturn(expected);

        ResponseEntity<CardapioDTO> response = apiController.criar(input);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Pizza", response.getBody().getNome());
        verify(mockController, times(1)).criarCardapio(any(CardapioDTO.class));
    }

    @Test
    void atualizar_shouldReturnOkWithBody() throws Exception {
        CardapioInput input = buildInput();
        Long id = 1L;
        CardapioDTO expected = new CardapioDTO("Pizza", "Delicious", new BigDecimal("12.00"), true, "http://img.png");

        when(mockController.atualizarCardapio(eq(id), any(CardapioDTO.class))).thenReturn(expected);

        ResponseEntity<CardapioDTO> response = apiController.atualizar(id, input);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("12.00", response.getBody().getPreco().toString());
        verify(mockController, times(1)).atualizarCardapio(eq(id), any(CardapioDTO.class));
    }

    @Test
    void deletar_shouldReturnNoContent() {
        Long id = 2L;

        doNothing().when(mockController).deletarCardapio(id);

        ResponseEntity<Void> response = apiController.deletar(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(mockController, times(1)).deletarCardapio(id);
    }

    @Test
    void obterPorId_shouldReturnOkWithBody() {
        Long id = 3L;
        CardapioDTO expected = new CardapioDTO("Sushi", "Fresh", new BigDecimal("20.00"), true, "http://sushi.png");

        when(mockController.obterCardapioPorId(id)).thenReturn(expected);

        ResponseEntity<CardapioDTO> response = apiController.obterPorId(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Sushi", response.getBody().getNome());
        verify(mockController, times(1)).obterCardapioPorId(id);
    }

    @Test
    void listar_shouldReturnOkWithList() {
        CardapioDTO a = new CardapioDTO("A", "a", new BigDecimal("1.00"), true, "u");
        CardapioDTO b = new CardapioDTO("B", "b", new BigDecimal("2.00"), false, "v");

        when(mockController.listarCardapios()).thenReturn(List.of(a, b));

        ResponseEntity<List<CardapioDTO>> response = apiController.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(mockController, times(1)).listarCardapios();
    }
}
