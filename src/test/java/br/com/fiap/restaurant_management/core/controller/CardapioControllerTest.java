package br.com.fiap.restaurant_management.core.controller;

import br.com.fiap.restaurant_management.core.domain.Cardapio;
import br.com.fiap.restaurant_management.core.dto.CardapioDTO;
import br.com.fiap.restaurant_management.core.mapper.CardapioMapper;
import br.com.fiap.restaurant_management.core.usecase.CardapioUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardapioControllerTest {

    @Mock
    private CardapioUseCase useCase;

    @Mock
    private CardapioMapper mapper;

    private CardapioController controller;

    @BeforeEach
    void setUp() {
        controller = new CardapioController(useCase, mapper);
    }

    private CardapioDTO buildDto() {
        return new CardapioDTO(null, 1L, "Nome", "Desc", new BigDecimal("5.00"), true, "url");
    }

    private Cardapio buildDomain() {
        return new Cardapio(1L, "Nome", "Desc", new BigDecimal("5.00"), true, "url");
    }

    @Test
    void criarCardapio_shouldMapUseCaseAndReturnDto() {
        CardapioDTO inputDto = buildDto();
        Cardapio domain = buildDomain();
        Cardapio created = new Cardapio(1L, "Nome", "Desc", new BigDecimal("5.00"), true, "url");

        when(mapper.toCardapio(inputDto)).thenReturn(domain);
        when(useCase.criar(domain)).thenReturn(created);
        CardapioDTO expected = new CardapioDTO(1L, 1L, "Nome", "Desc", new BigDecimal("5.00"), true, "url");
        when(mapper.toCardapioDTO(created)).thenReturn(expected);

        CardapioDTO result = controller.criarCardapio(inputDto);

        assertNotNull(result);
        assertEquals("Nome", result.getNome());
        verify(mapper).toCardapio(inputDto);
        verify(useCase).criar(domain);
        verify(mapper).toCardapioDTO(created);
    }

    @Test
    void atualizarCardapio_shouldCallUseCaseAndReturnDto() {
        Long id = 7L;
        CardapioDTO inputDto = new CardapioDTO(null, 1L, "Novo", "NovoDesc", new BigDecimal("8.00"), false, "u");
        Cardapio domain = new Cardapio(1L, "Novo", "NovoDesc", new BigDecimal("8.00"), false, "u");
        Cardapio updated = domain;
        CardapioDTO expected = new CardapioDTO(id, 1L, "Novo", "NovoDesc", new BigDecimal("8.00"), false, "u");

        when(mapper.toCardapio(inputDto)).thenReturn(domain);
        when(useCase.atualizar(id, domain)).thenReturn(updated);
        when(mapper.toCardapioDTO(updated)).thenReturn(expected);

        CardapioDTO result = controller.atualizarCardapio(id, inputDto);

        assertNotNull(result);
        assertEquals("Novo", result.getNome());
        verify(mapper).toCardapio(inputDto);
        verify(useCase).atualizar(id, domain);
        verify(mapper).toCardapioDTO(updated);
    }

    @Test
    void deletarCardapio_shouldCallUseCaseDelete() {
        Long id = 3L;
        doNothing().when(useCase).deletar(id);

        controller.deletarCardapio(id);

        verify(useCase).deletar(id);
    }

    @Test
    void obterCardapioPorId_shouldReturnMappedDto() {
        Long id = 4L;
        Cardapio domain = buildDomain();
        CardapioDTO expected = new CardapioDTO(id, 1L, "Nome", "Desc", new BigDecimal("5.00"), true, "url");

        when(useCase.obterPorId(id)).thenReturn(domain);
        when(mapper.toCardapioDTO(domain)).thenReturn(expected);

        CardapioDTO result = controller.obterCardapioPorId(id);

        assertNotNull(result);
        assertEquals(expected.getNome(), result.getNome());
        verify(useCase).obterPorId(id);
        verify(mapper).toCardapioDTO(domain);
    }

    @Test
    void listarCardapios_shouldReturnMappedList() {
        Cardapio d1 = new Cardapio(1L, "A","a", new BigDecimal("1.00"), true, "u");
        Cardapio d2 = new Cardapio(1L, "B","b", new BigDecimal("2.00"), false, "v");
        CardapioDTO dto1 = new CardapioDTO(1L, 1L, "A","a", new BigDecimal("1.00"), true, "u");
        CardapioDTO dto2 = new CardapioDTO(2L, 1L, "B","b", new BigDecimal("2.00"), false, "v");

        when(useCase.listar()).thenReturn(List.of(d1, d2));
        when(mapper.toCardapioDTO(d1)).thenReturn(dto1);
        when(mapper.toCardapioDTO(d2)).thenReturn(dto2);

        List<CardapioDTO> result = controller.listarCardapios();

        assertEquals(2, result.size());
        assertEquals("A", result.get(0).getNome());
        assertEquals("B", result.get(1).getNome());
        verify(useCase).listar();
        verify(mapper, times(2)).toCardapioDTO(any(Cardapio.class));
    }
}
