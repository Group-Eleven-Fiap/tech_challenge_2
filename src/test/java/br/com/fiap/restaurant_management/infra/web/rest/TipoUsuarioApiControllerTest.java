package br.com.fiap.restaurant_management.infra.web.rest;

import br.com.fiap.restaurant_management.core.controller.TipoUsuarioController;
import br.com.fiap.restaurant_management.core.dto.TipoUsuarioInputDto;
import br.com.fiap.restaurant_management.core.dto.TipoUsuarioOutputDto;
import br.com.fiap.restaurant_management.infra.web.dto.TipoUsuarioInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TipoUsuarioApiControllerTest {

    @Mock
    private TipoUsuarioController controller;

    private TipoUsuarioApiController apiController;

    @BeforeEach
    void setUp() {
        apiController = new TipoUsuarioApiController(controller);
    }

    private void setField(Object target, String name, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(target, value);
    }

    private TipoUsuarioInput buildInput() throws Exception {
        var input = new TipoUsuarioInput();
        setField(input, "nome", "Dono");
        return input;
    }

    private TipoUsuarioOutputDto buildOutputDto() {
        return new TipoUsuarioOutputDto(100L, "DONO");
    }

    @Test
    void deveCriarTipoUsuario() throws Exception {
        var input = buildInput();

        when(controller.criar(org.mockito.ArgumentMatchers.any(TipoUsuarioInputDto.class))).thenReturn(100L);

        var result = apiController.criar(input);

        var captor = ArgumentCaptor.forClass(TipoUsuarioInputDto.class);

        assertNotNull(result);
        assertEquals(100L, result);

        verify(controller).criar(captor.capture());

        var inputDto = captor.getValue();

        assertEquals("Dono", inputDto.getNome());
    }

    @Test
    void deveAtualizarTipoUsuario() throws Exception {
        var id = 100L;
        var input = buildInput();

        apiController.atualizar(id, input);

        var captor = ArgumentCaptor.forClass(TipoUsuarioInputDto.class);

        verify(controller).atualizar(org.mockito.ArgumentMatchers.eq(id), captor.capture());

        var inputDto = captor.getValue();

        assertEquals("Dono", inputDto.getNome());
    }

    @Test
    void deveExcluirTipoUsuario() {
        var id = 100L;

        apiController.excluir(id);

        verify(controller).excluir(id);
    }

    @Test
    void deveConsultarTiposUsuario() {
        var output = buildOutputDto();
        var expected = List.of(output);

        when(controller.consultar()).thenReturn(expected);

        var result = apiController.consultar();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expected, result);
        assertEquals(100L, result.getFirst().getId());
        assertEquals("DONO", result.getFirst().getNome());

        verify(controller).consultar();
    }
}
