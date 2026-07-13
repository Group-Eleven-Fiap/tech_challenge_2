package br.com.fiap.restaurant_management.core.usecase;

import br.com.fiap.restaurant_management.core.domain.Restaurante;
import br.com.fiap.restaurant_management.core.domain.Usuario;
import br.com.fiap.restaurant_management.core.exception.ResourceNotFoundException;
import br.com.fiap.restaurant_management.core.gateway.RestauranteGateway;
import br.com.fiap.restaurant_management.core.gateway.UsuarioGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestauranteUseCaseImplTest {

    @Mock
    private RestauranteGateway restauranteGateway;
    @Mock
    private UsuarioGateway usuarioGateway;

    private RestauranteUseCaseImpl useCase;
    private Restaurante restaurante;

    @BeforeEach
    void preparar() {
        useCase = new RestauranteUseCaseImpl(restauranteGateway, usuarioGateway);
        restaurante = new Restaurante("Sabor Brasil", "Rua A", "Brasileira", "11h às 22h", 1L);
    }

    @Test
    void deveCriarQuandoDonoExiste() {
        var dono = new Usuario(1L, "Dono", "dono@email.com", "dono", "senha", null);
        when(usuarioGateway.consultarPorId(1L)).thenReturn(Optional.of(dono));
        when(restauranteGateway.criar(restaurante)).thenReturn(restaurante);

        assertEquals(restaurante, useCase.criar(restaurante));
        verify(restauranteGateway).criar(restaurante);
    }

    @Test
    void naoDeveCriarQuandoDonoNaoExiste() {
        when(usuarioGateway.consultarPorId(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> useCase.criar(restaurante));
        verify(restauranteGateway, never()).criar(restaurante);
    }

    @Test
    void deveConsultarEListar() {
        when(restauranteGateway.consultarPorId(2L)).thenReturn(Optional.of(restaurante));
        when(restauranteGateway.consultarTodos()).thenReturn(List.of(restaurante));

        assertEquals(restaurante, useCase.consultarPorId(2L));
        assertEquals(1, useCase.consultarTodos().size());
    }

    @Test
    void deveInformarRestauranteNaoEncontrado() {
        when(restauranteGateway.consultarPorId(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> useCase.consultarPorId(99L));
        assertThrows(ResourceNotFoundException.class, () -> useCase.consultarPorId(0L));
        assertThrows(ResourceNotFoundException.class, () -> useCase.consultarPorId(null));
    }

    @Test
    void deveAtualizarRestaurante() {
        var dono = new Usuario(1L, "Dono", "dono@email.com", "dono", "senha", null);
        when(restauranteGateway.consultarPorId(2L)).thenReturn(Optional.of(restaurante));
        when(usuarioGateway.consultarPorId(1L)).thenReturn(Optional.of(dono));
        when(restauranteGateway.atualizar(org.mockito.ArgumentMatchers.any(Restaurante.class)))
                .thenAnswer(invocacao -> invocacao.getArgument(0));

        var atualizado = useCase.atualizar(2L, restaurante);

        assertEquals(2L, atualizado.getId());
        assertEquals("Sabor Brasil", atualizado.getNome());
    }

    @Test
    void deveExcluirRestauranteExistente() {
        when(restauranteGateway.consultarPorId(2L)).thenReturn(Optional.of(restaurante));

        useCase.excluir(2L);

        verify(restauranteGateway).excluir(2L);
    }

    @Test
    void naoDeveExcluirRestauranteInexistente() {
        when(restauranteGateway.consultarPorId(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> useCase.excluir(2L));
        verify(restauranteGateway, never()).excluir(2L);
    }
}
