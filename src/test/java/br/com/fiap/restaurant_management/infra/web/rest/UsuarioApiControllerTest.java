package br.com.fiap.restaurant_management.infra.web.rest;

import br.com.fiap.restaurant_management.core.controller.UsuarioController;
import br.com.fiap.restaurant_management.core.dto.TipoUsuarioOutputDto;
import br.com.fiap.restaurant_management.core.dto.UsuarioInputDto;
import br.com.fiap.restaurant_management.core.dto.UsuarioOutputDto;
import br.com.fiap.restaurant_management.infra.web.dto.UsuarioInput;
import br.com.fiap.restaurant_management.infra.web.dto.UsuarioTipoUsuarioInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioApiControllerTest {

    @Mock
    private UsuarioController controller;

    private UsuarioApiController apiController;

    @BeforeEach
    void setUp() {
        apiController = new UsuarioApiController(controller);
    }

    private void setField(Object target, String name, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(target, value);
    }

    private UsuarioInput buildInput() throws Exception {
        var input = new UsuarioInput();

        setField(input, "nome", "Nome teste");
        setField(input, "email", "email.teste@email.com");
        setField(input, "login", "login.teste");
        setField(input, "senha", "senha.teste");
        setField(input, "tipoUsuarioId", 1L);

        return input;
    }

    private UsuarioTipoUsuarioInput buildTipoUsuarioInput() throws Exception {
        var input = new UsuarioTipoUsuarioInput();

        setField(input, "usuarioId", 100L);
        setField(input, "tipoUsuarioId", 2L);

        return input;
    }

    private UsuarioOutputDto buildOutputDto() {
        return new UsuarioOutputDto(
                100L,
                "NOME TESTE",
                "EMAIL.TESTE@EMAIL.COM",
                "LOGIN.TESTE",
                new TipoUsuarioOutputDto(1L, "DONO"),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    @Test
    void deveCriarUsuario() throws Exception {
        var input = buildInput();

        when(controller.criar(org.mockito.ArgumentMatchers.any(UsuarioInputDto.class))).thenReturn(100L);

        var result = apiController.criar(input);

        var captor = ArgumentCaptor.forClass(UsuarioInputDto.class);

        assertNotNull(result);
        assertEquals(100L, result);

        verify(controller).criar(captor.capture());

        var inputDto = captor.getValue();

        assertEquals("Nome teste", inputDto.getNome());
        assertEquals("email.teste@email.com", inputDto.getEmail());
        assertEquals("login.teste", inputDto.getLogin());
        assertEquals("senha.teste", inputDto.getSenha());
        assertEquals(1L, inputDto.getTipoUsuarioId());
    }

    @Test
    void deveAtualizarUsuario() throws Exception {
        var id = 100L;
        var input = buildInput();

        apiController.atualizar(id, input);

        var captor = ArgumentCaptor.forClass(UsuarioInputDto.class);

        verify(controller).atualizar(org.mockito.ArgumentMatchers.eq(id), captor.capture());

        var inputDto = captor.getValue();

        assertEquals("Nome teste", inputDto.getNome());
        assertEquals("email.teste@email.com", inputDto.getEmail());
        assertEquals("login.teste", inputDto.getLogin());
        assertEquals("senha.teste", inputDto.getSenha());
        assertEquals(1L, inputDto.getTipoUsuarioId());
    }

    @Test
    void deveAtualizarTipoUsuarioDoUsuario() throws Exception {
        var input = buildTipoUsuarioInput();

        apiController.atualizarTipoUsuario(input);

        verify(controller).atualizarTipoUsuario(100L, 2L);
    }

    @Test
    void deveExcluirUsuario() {
        var id = 100L;

        apiController.excluir(id);

        verify(controller).excluir(id);
    }

    @Test
    void deveConsultarUsuarios() {
        var output = buildOutputDto();
        var expected = List.of(output);

        when(controller.consultar()).thenReturn(expected);

        var result = apiController.consultar();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expected, result);
        assertEquals(100L, result.getFirst().getId());
        assertEquals("NOME TESTE", result.getFirst().getNome());
        assertEquals("EMAIL.TESTE@EMAIL.COM", result.getFirst().getEmail());
        assertEquals("LOGIN TESTE".replace(" ", "."), result.getFirst().getLogin());
        assertEquals(1L, result.getFirst().getTipoUsuario().getId());
        assertEquals("DONO", result.getFirst().getTipoUsuario().getNome());

        verify(controller).consultar();
    }
}
