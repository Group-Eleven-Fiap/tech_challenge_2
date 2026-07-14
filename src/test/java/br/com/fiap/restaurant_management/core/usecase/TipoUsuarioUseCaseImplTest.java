package br.com.fiap.restaurant_management.core.usecase;

import br.com.fiap.restaurant_management.core.domain.TipoUsuario;
import br.com.fiap.restaurant_management.core.dto.TipoUsuarioInputDto;
import br.com.fiap.restaurant_management.core.dto.TipoUsuarioOutputDto;
import br.com.fiap.restaurant_management.core.exception.BusinessRuleException;
import br.com.fiap.restaurant_management.core.gateway.TipoUsuarioGateway;
import br.com.fiap.restaurant_management.core.mapper.TipoUsuarioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TipoUsuarioUseCaseImplTest {

    @Mock
    private TipoUsuarioMapper mapper;

    @Mock
    private TipoUsuarioGateway gateway;

    private TipoUsuarioUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new TipoUsuarioUseCaseImpl(mapper, gateway);
    }

    private TipoUsuarioInputDto buildInputDto() {
        return new TipoUsuarioInputDto("Dono");
    }

    private TipoUsuario buildDomain() {
        return new TipoUsuario(100L, "Dono");
    }

    private TipoUsuarioOutputDto buildOutputDto() {
        return new TipoUsuarioOutputDto(100L, "DONO");
    }

    @Test
    void deveCriarTipoUsuario() {
        var input = buildInputDto();
        var tipoUsuario = buildDomain();

        when(mapper.map(input)).thenReturn(tipoUsuario);
        when(gateway.criar(tipoUsuario)).thenReturn(100L);

        var result = useCase.criar(input);

        assertNotNull(result);
        assertEquals(100L, result);

        verify(mapper).map(input);
        verify(gateway).criar(tipoUsuario);
    }

    @Test
    void deveConsultarTiposUsuario() {
        var tipoUsuario = buildDomain();
        var output = buildOutputDto();

        when(gateway.consultar()).thenReturn(List.of(tipoUsuario));
        when(mapper.map(tipoUsuario)).thenReturn(output);

        var result = useCase.consultar();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(100L, result.getFirst().getId());
        assertEquals("DONO", result.getFirst().getNome());

        verify(gateway).consultar();
        verify(mapper).map(tipoUsuario);
    }

    @Test
    void deveAtualizarTipoUsuario() {
        var id = 100L;
        var input = new TipoUsuarioInputDto("Cliente");
        var tipoUsuario = buildDomain();

        when(gateway.consultarPorId(id)).thenReturn(Optional.of(tipoUsuario));

        useCase.atualizar(id, input);

        var captor = ArgumentCaptor.forClass(TipoUsuario.class);

        verify(gateway).consultarPorId(id);
        verify(gateway).atualizar(captor.capture());

        var tipoUsuarioAtualizado = captor.getValue();

        assertEquals(100L, tipoUsuarioAtualizado.getId());
        assertEquals("CLIENTE", tipoUsuarioAtualizado.getNome());
    }

    @Test
    void deveLancarExcecaoAoAtualizarTipoUsuarioInexistente() {
        var id = 100L;
        var input = new TipoUsuarioInputDto("Cliente");

        when(gateway.consultarPorId(id)).thenReturn(Optional.empty());

        var exception = assertThrows(
                BusinessRuleException.class,
                () -> useCase.atualizar(id, input)
        );

        assertEquals("Tipo de Usuário não encontrado", exception.getMessage());

        verify(gateway).consultarPorId(id);
        verify(gateway, never()).atualizar(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void deveExcluirTipoUsuario() {
        var id = 100L;
        var tipoUsuario = buildDomain();

        when(gateway.consultarPorId(id)).thenReturn(Optional.of(tipoUsuario));

        useCase.excluir(id);

        verify(gateway).consultarPorId(id);
        verify(gateway).excluir(id);
    }

    @Test
    void deveLancarExcecaoAoExcluirTipoUsuarioInexistente() {
        var id = 100L;

        when(gateway.consultarPorId(id)).thenReturn(Optional.empty());

        var exception = assertThrows(
                BusinessRuleException.class,
                () -> useCase.excluir(id)
        );

        assertEquals("Tipo de Usuário não encontrado", exception.getMessage());

        verify(gateway).consultarPorId(id);
        verify(gateway, never()).excluir(id);
    }
}
