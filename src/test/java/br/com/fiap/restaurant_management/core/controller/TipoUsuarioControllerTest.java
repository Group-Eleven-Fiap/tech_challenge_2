package br.com.fiap.restaurant_management.core.controller;

import br.com.fiap.restaurant_management.core.domain.TipoUsuario;
import br.com.fiap.restaurant_management.core.dto.TipoUsuarioInputDto;
import br.com.fiap.restaurant_management.core.dto.TipoUsuarioOutputDto;
import br.com.fiap.restaurant_management.core.mapper.TipoUsuarioMapper;
import br.com.fiap.restaurant_management.core.usecase.TipoUsuarioUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TipoUsuarioControllerTest {

    @Mock
    private TipoUsuarioUseCase useCase;

    @Mock
    private TipoUsuarioMapper mapper;

    private TipoUsuarioController controller;

    @BeforeEach
    void setUp() {
        controller = new TipoUsuarioController(useCase);
    }

    private TipoUsuarioInputDto buildInputDto() {
        return new TipoUsuarioInputDto("Nome teste");
    }

    private TipoUsuarioOutputDto buildOutputDto() {
        return new TipoUsuarioOutputDto(100L, "Nome teste");
    }

    private TipoUsuario buildDomain() {
        return new TipoUsuario(100L, "Nome teste");
    }

    @Test
    void deveCriarTipoUsuario() {
        var input = buildInputDto();

        when(useCase.criar(input)).thenReturn(100L);

        var result = controller.criar(input);

        assertNotNull(result);
        assertEquals(100L, result);

        verify(useCase).criar(input);
    }

    @Test
    void deveConsultarTipoUsuario() {
        var output = buildOutputDto();
        var expected = List.of(output);

        when(useCase.consultar()).thenReturn(expected);

        var result = controller.consultar();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expected, result);
        assertEquals(100L, result.getFirst().getId());
        assertEquals("Nome teste", result.getFirst().getNome());

        verify(useCase).consultar();
    }

    @Test
    void deveAtualizarTipoUsuario() {
        var id = 100L;
        var input = new TipoUsuarioInputDto("Nome novo");

        controller.atualizar(id, input);

        verify(useCase).atualizar(id, input);
    }

    @Test
    void deveExcluirTipoUsuario() {
        var id = 100L;

        controller.excluir(id);

        verify(useCase).excluir(id);
    }
}
