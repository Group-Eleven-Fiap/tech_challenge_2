package br.com.fiap.restaurant_management.infra.database.jpa;

import br.com.fiap.restaurant_management.core.dto.CardapioDTO;
import br.com.fiap.restaurant_management.infra.database.jpa.entity.CardapioEntity;
import br.com.fiap.restaurant_management.infra.database.jpa.entity.RestauranteEntity;
import br.com.fiap.restaurant_management.infra.database.jpa.repository.CardapioRepository;
import br.com.fiap.restaurant_management.infra.database.mapper.CardapioEntityMapper;
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
class CardapioJpaGatewayTest {

    @Mock
    private CardapioEntityMapper mapper;

    @Mock
    private CardapioRepository repository;

    private CardapioJpaGateway gateway;

    @BeforeEach
    void setUp() {
        gateway = new CardapioJpaGateway(mapper, repository);
    }

    private CardapioDTO buildDto() {
        return new CardapioDTO(1L, 1L, "Nome", "Desc", new BigDecimal("5.00"), true, "url");
    }

    private CardapioEntity buildEntity() {
        CardapioEntity e = new CardapioEntity();
        e.setId(1L);
        e.setRestaurante(new RestauranteEntity());
        e.setNome("Nome");
        e.setDescricao("Desc");
        e.setPreco(new BigDecimal("5.00"));
        e.setDisponibilidadeRestaurante(true);
        e.setFotoUrl("url");
        return e;
    }

    @Test
    void criarCardapio_shouldMapSaveAndReturnDto() {
        CardapioDTO dto = buildDto();
        CardapioEntity entity = buildEntity();

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toCardapioDTO(entity)).thenReturn(dto);

        CardapioDTO result = gateway.criarCardapio(dto);

        assertNotNull(result);
        assertEquals(dto.getNome(), result.getNome());
        verify(mapper).toEntity(dto);
        verify(repository).save(entity);
        verify(mapper).toCardapioDTO(entity);
    }

    @Test
    void atualizarCardapio_whenExists_shouldUpdateAndReturnDto() {
        Long id = 10L;
        CardapioDTO input = new CardapioDTO(id, 1L, "Novo", "NovoDesc", new BigDecimal("8.00"), false, "newUrl");
        CardapioEntity existing = buildEntity();
        existing.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);
        when(mapper.toCardapioDTO(existing)).thenReturn(input);

        CardapioDTO result = gateway.atualizarCardapio(id, input);

        assertNotNull(result);
        assertEquals("Novo", existing.getNome());
        assertEquals(new BigDecimal("8.00"), existing.getPreco());
        assertFalse(existing.getDisponibilidadeRestaurante());
        verify(repository).findById(id);
        verify(repository).save(existing);
        verify(mapper).toCardapioDTO(existing);
    }

    @Test
    void atualizarCardapio_whenNotFound_shouldThrow() {
        Long id = 99L;
        CardapioDTO input = buildDto();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> gateway.atualizarCardapio(id, input));

        verify(repository).findById(id);
        verify(repository, never()).save(any());
    }

    @Test
    void deletarCardapio_shouldCallRepositoryDeleteById() {
        Long id = 5L;

        doNothing().when(repository).deleteById(id);

        gateway.deletarCardapio(id);

        verify(repository).deleteById(id);
    }

    @Test
    void obterCardapioPorId_whenPresent_shouldReturnOptionalDto() {
        Long id = 2L;
        CardapioEntity entity = buildEntity();
        CardapioDTO dto = buildDto();

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toCardapioDTO(entity)).thenReturn(dto);

        Optional<CardapioDTO> result = gateway.obterCardapioPorId(id);

        assertTrue(result.isPresent());
        assertEquals(dto.getNome(), result.get().getNome());
        verify(repository).findById(id);
        verify(mapper).toCardapioDTO(entity);
    }

    @Test
    void obterCardapioPorId_whenAbsent_shouldReturnEmptyOptional() {
        Long id = 3L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        Optional<CardapioDTO> result = gateway.obterCardapioPorId(id);

        assertFalse(result.isPresent());
        verify(repository).findById(id);
        verifyNoInteractions(mapper);
    }

    @Test
    void listarTodos_shouldReturnMappedList() {
        CardapioEntity e1 = buildEntity();
        CardapioEntity e2 = buildEntity();
        e2.setId(2L);
        CardapioDTO d1 = new CardapioDTO(1L, 1L, "A","a", new BigDecimal("1.00"), true, "u");
        CardapioDTO d2 = new CardapioDTO(2L, 1L, "B","b", new BigDecimal("2.00"), false, "v");

        when(repository.findAll()).thenReturn(List.of(e1, e2));
        when(mapper.toCardapioDTO(e1)).thenReturn(d1);
        when(mapper.toCardapioDTO(e2)).thenReturn(d2);

        List<CardapioDTO> result = gateway.listarTodos();

        assertEquals(2, result.size());
        assertEquals("A", result.get(0).getNome());
        assertEquals("B", result.get(1).getNome());
        verify(repository).findAll();
        verify(mapper, times(2)).toCardapioDTO(any());
    }
}
