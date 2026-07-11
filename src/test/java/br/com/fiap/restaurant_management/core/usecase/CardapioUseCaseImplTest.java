package br.com.fiap.restaurant_management.core.usecase;

import br.com.fiap.restaurant_management.core.domain.Cardapio;
import br.com.fiap.restaurant_management.core.dto.CardapioDTO;
import br.com.fiap.restaurant_management.core.exception.BusinessRuleException;
import br.com.fiap.restaurant_management.core.gateway.CardapioGateway;
import br.com.fiap.restaurant_management.core.mapper.CardapioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardapioUseCaseImplTest {

    @Mock
    private CardapioMapper mapper;

    @Mock
    private CardapioGateway gateway;

    private CardapioUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new CardapioUseCaseImpl(mapper, gateway);
    }

    private Cardapio buildDomain() {
        return new Cardapio("Nome", "Desc", new BigDecimal("5.00"), true, "url");
    }

    private CardapioDTO buildDto() {
        return new CardapioDTO("Nome", "Desc", new BigDecimal("5.00"), true, "url");
    }

    @Test
    void criar_shouldMapGatewayAndReturnDomain() {
        Cardapio domain = buildDomain();
        CardapioDTO dto = buildDto();

        when(mapper.toCardapioDTO(domain)).thenReturn(dto);
        when(gateway.criarCardapio(dto)).thenReturn(dto);
        when(mapper.toCardapio(dto)).thenReturn(domain);

        Cardapio result = useCase.criar(domain);

        assertNotNull(result);
        assertEquals(domain.getNome(), result.getNome());
        verify(mapper).toCardapioDTO(domain);
        verify(gateway).criarCardapio(dto);
        verify(mapper).toCardapio(dto);
    }

    @Test
    void atualizar_withInvalidId_shouldThrowIllegalArgument() {
        Cardapio domain = buildDomain();
        assertThrows(IllegalArgumentException.class, () -> useCase.atualizar(0L, domain));
        assertThrows(IllegalArgumentException.class, () -> useCase.atualizar(null, domain));
    }

    @Test
    void atualizar_whenNotFound_shouldThrowBusinessRule() {
        Long id = 5L;
        Cardapio domain = buildDomain();

        when(gateway.obterCardapioPorId(id)).thenReturn(Optional.empty());

        BusinessRuleException ex = assertThrows(BusinessRuleException.class, () -> useCase.atualizar(id, domain));
        assertTrue(ex.getMessage().contains("não encontrado"));
        verify(gateway).obterCardapioPorId(id);
        verify(gateway, never()).atualizarCardapio(anyLong(), any());
    }

    @Test
    void atualizar_whenExists_shouldUpdateAndReturnDomain() {
        Long id = 6L;
        Cardapio domain = buildDomain();
        CardapioDTO dto = buildDto();

        when(gateway.obterCardapioPorId(id)).thenReturn(Optional.of(dto));
        when(mapper.toCardapioDTO(domain)).thenReturn(dto);
        when(gateway.atualizarCardapio(id, dto)).thenReturn(dto);
        when(mapper.toCardapio(dto)).thenReturn(domain);

        Cardapio result = useCase.atualizar(id, domain);

        assertNotNull(result);
        assertEquals(domain.getNome(), result.getNome());
        verify(gateway).obterCardapioPorId(id);
        verify(mapper).toCardapioDTO(domain);
        verify(gateway).atualizarCardapio(id, dto);
        verify(mapper).toCardapio(dto);
    }

    @Test
    void deletar_withInvalidId_shouldThrowIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () -> useCase.deletar(0L));
        assertThrows(IllegalArgumentException.class, () -> useCase.deletar(null));
    }

    @Test
    void deletar_whenNotFound_shouldThrowBusinessRule() {
        Long id = 9L;
        when(gateway.obterCardapioPorId(id)).thenReturn(Optional.empty());

        BusinessRuleException ex = assertThrows(BusinessRuleException.class, () -> useCase.deletar(id));
        assertTrue(ex.getMessage().contains("não encontrado"));
        verify(gateway).obterCardapioPorId(id);
        verify(gateway, never()).deletarCardapio(anyLong());
    }

    @Test
    void deletar_whenExists_shouldCallGatewayDelete() {
        Long id = 8L;
        CardapioDTO dto = buildDto();
        when(gateway.obterCardapioPorId(id)).thenReturn(Optional.of(dto));
        doNothing().when(gateway).deletarCardapio(id);

        useCase.deletar(id);

        verify(gateway).obterCardapioPorId(id);
        verify(gateway).deletarCardapio(id);
    }

    @Test
    void obterPorId_withInvalidId_shouldThrowIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () -> useCase.obterPorId(0L));
        assertThrows(IllegalArgumentException.class, () -> useCase.obterPorId(null));
    }

    @Test
    void obterPorId_whenNotFound_shouldThrowBusinessRule() {
        Long id = 11L;
        when(gateway.obterCardapioPorId(id)).thenReturn(Optional.empty());

        BusinessRuleException ex = assertThrows(BusinessRuleException.class, () -> useCase.obterPorId(id));
        assertTrue(ex.getMessage().contains("não encontrado"));
        verify(gateway).obterCardapioPorId(id);
        verifyNoInteractions(mapper);
    }

    @Test
    void obterPorId_whenFound_shouldReturnDomain() {
        Long id = 12L;
        CardapioDTO dto = buildDto();
        Cardapio domain = buildDomain();

        when(gateway.obterCardapioPorId(id)).thenReturn(Optional.of(dto));
        when(mapper.toCardapio(dto)).thenReturn(domain);

        Cardapio result = useCase.obterPorId(id);

        assertNotNull(result);
        assertEquals(domain.getNome(), result.getNome());
        verify(gateway).obterCardapioPorId(id);
        verify(mapper).toCardapio(dto);
    }

    @Test
    void listar_shouldReturnMappedList() {
        CardapioDTO d1 = new CardapioDTO("A","a", new BigDecimal("1.00"), true, "u");
        CardapioDTO d2 = new CardapioDTO("B","b", new BigDecimal("2.00"), false, "v");
        Cardapio c1 = new Cardapio("A","a", new BigDecimal("1.00"), true, "u");
        Cardapio c2 = new Cardapio("B","b", new BigDecimal("2.00"), false, "v");

        when(gateway.listarTodos()).thenReturn(List.of(d1, d2));
        when(mapper.toCardapio(d1)).thenReturn(c1);
        when(mapper.toCardapio(d2)).thenReturn(c2);

        List<Cardapio> result = useCase.listar();

        assertEquals(2, result.size());
        assertEquals("A", result.get(0).getNome());
        assertEquals("B", result.get(1).getNome());
        verify(gateway).listarTodos();
        verify(mapper, times(2)).toCardapio(any(CardapioDTO.class));
    }
}
