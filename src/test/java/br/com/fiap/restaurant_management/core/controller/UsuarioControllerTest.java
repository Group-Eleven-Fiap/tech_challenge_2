package br.com.fiap.restaurant_management.core.controller;

import br.com.fiap.restaurant_management.core.domain.Usuario;
import br.com.fiap.restaurant_management.core.dto.UsuarioInputDto;
import br.com.fiap.restaurant_management.core.dto.TipoUsuarioOutputDto;
import br.com.fiap.restaurant_management.core.dto.UsuarioOutputDto;
import br.com.fiap.restaurant_management.core.mapper.UsuarioMapper;
import br.com.fiap.restaurant_management.core.usecase.UsuarioUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private UsuarioUseCase useCase;

    private UsuarioController controller;

    @BeforeEach
    void setUp() {
        controller = new UsuarioController(useCase);
    }

    private UsuarioInputDto buildInputDto() {
        return new UsuarioInputDto(
                "Nome teste",
                "email.teste@email.com",
                "login.teste",
                "senha.teste",
                1L
        );
    }

    private UsuarioOutputDto buildOutputDto() {
        return new UsuarioOutputDto(
                100L,
                "Nome teste",
                "email.teste@email.com",
                "login.teste",
                new TipoUsuarioOutputDto(1L, "DONO"),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    @Test
    void deveCriarUsuario() {
        var input = buildInputDto();

        when(useCase.criar(input)).thenReturn(100L);

        var result = controller.criar(input);

        assertNotNull(result);
        assertEquals(100L, result);

        verify(useCase).criar(input);
    }

    @Test
    void deveConsultarUsuario() {
        var output = buildOutputDto();
        var expected = List.of(output);

        when(useCase.consultar()).thenReturn(expected);

        var result = controller.consultar();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expected, result);
        assertEquals(100L, result.getFirst().getId());
        assertEquals("Nome teste", result.getFirst().getNome());
        assertEquals("email.teste@email.com", result.getFirst().getEmail());
        assertEquals("login.teste", result.getFirst().getLogin());
        assertEquals(1L, result.getFirst().getTipoUsuario().getId());
        assertEquals("DONO", result.getFirst().getTipoUsuario().getNome());

        verify(useCase).consultar();
    }

    @Test
    void deveAtualizarUsuario() {
        var id = 100L;
        var input = new UsuarioInputDto(
                "Nome novo",
                "email.novo@email.com",
                "login.novo",
                "senha.nova",
                2L
        );

        controller.atualizar(id, input);

        verify(useCase).atualizar(id, input);
    }

    @Test
    void deveExcluirUsuario() {
        var id = 100L;

        controller.excluir(id);

        verify(useCase).excluir(id);
    }

    @Test
    void deveAtualizarTipoUsuario() {
        var usuarioId = 100L;
        var tipoUsuarioId = 2L;

        controller.atualizarTipoUsuario(usuarioId, tipoUsuarioId);

        verify(useCase).atualizarTipoUsuario(usuarioId, tipoUsuarioId);
    }
}
