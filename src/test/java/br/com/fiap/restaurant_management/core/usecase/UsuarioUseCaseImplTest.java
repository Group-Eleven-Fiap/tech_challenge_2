package br.com.fiap.restaurant_management.core.usecase;


import br.com.fiap.restaurant_management.core.domain.TipoUsuario;
import br.com.fiap.restaurant_management.core.domain.Usuario;
import br.com.fiap.restaurant_management.core.dto.TipoUsuarioOutputDto;
import br.com.fiap.restaurant_management.core.dto.UsuarioInputDto;
import br.com.fiap.restaurant_management.core.dto.UsuarioOutputDto;
import br.com.fiap.restaurant_management.core.exception.ResourceNotFoundException;
import br.com.fiap.restaurant_management.core.gateway.TipoUsuarioGateway;
import br.com.fiap.restaurant_management.core.gateway.UsuarioGateway;
import br.com.fiap.restaurant_management.core.mapper.UsuarioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioUseCaseImplTest {

    @Mock
    private UsuarioMapper mapper;

    @Mock
    private UsuarioGateway gateway;

    @Mock
    private TipoUsuarioGateway tipoUsuarioGateway;

    private UsuarioUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new UsuarioUseCaseImpl(mapper, gateway, tipoUsuarioGateway);
    }

    private TipoUsuario buildTipoUsuario() {
        return new TipoUsuario(1L, "Dono");
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

    private Usuario buildDomain() {
        return new Usuario(
                100L,
                "Nome teste",
                "email.teste@email.com",
                "login.teste",
                "senha.teste",
                buildTipoUsuario()
        );
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
    void deveCriarUsuario() {
        var input = buildInputDto();
        var tipoUsuario = buildTipoUsuario();
        var usuario = buildDomain();

        when(tipoUsuarioGateway.consultarPorId(input.getTipoUsuarioId())).thenReturn(Optional.of(tipoUsuario));
        when(mapper.map(input, tipoUsuario)).thenReturn(usuario);
        when(gateway.criar(usuario)).thenReturn(100L);

        var result = useCase.criar(input);

        assertNotNull(result);
        assertEquals(100L, result);

        verify(tipoUsuarioGateway).consultarPorId(input.getTipoUsuarioId());
        verify(mapper).map(input, tipoUsuario);
        verify(gateway).criar(usuario);
    }

    @Test
    void deveLancarExcecaoAoCriarUsuarioComTipoUsuarioInexistente() {
        var input = buildInputDto();

        when(tipoUsuarioGateway.consultarPorId(input.getTipoUsuarioId())).thenReturn(Optional.empty());

        var exception = assertThrows(
                ResourceNotFoundException.class,
                () -> useCase.criar(input)
        );

        assertEquals("Tipo de Usuário não encontrado", exception.getMessage());

        verify(tipoUsuarioGateway).consultarPorId(input.getTipoUsuarioId());
        verify(mapper, never()).map(any(UsuarioInputDto.class), any(TipoUsuario.class));
        verify(gateway, never()).criar(any(Usuario.class));
    }

    @Test
    void deveConsultarUsuarios() {
        var usuario = buildDomain();
        var output = buildOutputDto();

        when(gateway.consultar()).thenReturn(List.of(usuario));
        when(mapper.map(usuario)).thenReturn(output);

        var result = useCase.consultar();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(100L, result.getFirst().getId());
        assertEquals("NOME TESTE", result.getFirst().getNome());
        assertEquals("EMAIL.TESTE@EMAIL.COM", result.getFirst().getEmail());
        assertEquals("LOGIN.TESTE", result.getFirst().getLogin());
        assertEquals(1L, result.getFirst().getTipoUsuario().getId());
        assertEquals("DONO", result.getFirst().getTipoUsuario().getNome());

        verify(gateway).consultar();
        verify(mapper).map(usuario);
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
        var usuarioExistente = buildDomain();
        var novoTipoUsuario = new TipoUsuario(2L, "Cliente");

        when(gateway.consultarPorId(id)).thenReturn(Optional.of(usuarioExistente));
        when(tipoUsuarioGateway.consultarPorId(input.getTipoUsuarioId())).thenReturn(Optional.of(novoTipoUsuario));

        useCase.atualizar(id, input);

        var captor = ArgumentCaptor.forClass(Usuario.class);

        verify(gateway).consultarPorId(id);
        verify(tipoUsuarioGateway).consultarPorId(input.getTipoUsuarioId());
        verify(gateway).atualizar(captor.capture());

        var usuarioAtualizado = captor.getValue();

        assertEquals(100L, usuarioAtualizado.getId());
        assertEquals("NOME NOVO", usuarioAtualizado.getNome());
        assertEquals("EMAIL.NOVO@EMAIL.COM", usuarioAtualizado.getEmail());
        assertEquals("LOGIN.NOVO", usuarioAtualizado.getLogin());
        assertEquals("SENHA.NOVA", usuarioAtualizado.getSenha());
        assertEquals(novoTipoUsuario, usuarioAtualizado.getTipoUsuario());
    }

    @Test
    void deveLancarExcecaoAoAtualizarUsuarioInexistente() {
        var id = 100L;
        var input = buildInputDto();

        when(gateway.consultarPorId(id)).thenReturn(Optional.empty());

        var exception = assertThrows(
                ResourceNotFoundException.class,
                () -> useCase.atualizar(id, input)
        );

        assertEquals("Usuário não encontrado", exception.getMessage());

        verify(gateway).consultarPorId(id);
        verify(tipoUsuarioGateway, never()).consultarPorId(any());
        verify(gateway, never()).atualizar(any(Usuario.class));
    }

    @Test
    void deveLancarExcecaoAoAtualizarUsuarioComTipoUsuarioInexistente() {
        var id = 100L;
        var input = buildInputDto();
        var usuarioExistente = buildDomain();

        when(gateway.consultarPorId(id)).thenReturn(Optional.of(usuarioExistente));
        when(tipoUsuarioGateway.consultarPorId(input.getTipoUsuarioId())).thenReturn(Optional.empty());

        var exception = assertThrows(
                ResourceNotFoundException.class,
                () -> useCase.atualizar(id, input)
        );

        assertEquals("Tipo de Usuário não encontrado", exception.getMessage());

        verify(gateway).consultarPorId(id);
        verify(tipoUsuarioGateway).consultarPorId(input.getTipoUsuarioId());
        verify(gateway, never()).atualizar(any(Usuario.class));
    }

    @Test
    void deveExcluirUsuario() {
        var id = 100L;
        var usuario = buildDomain();

        when(gateway.consultarPorId(id)).thenReturn(Optional.of(usuario));

        useCase.excluir(id);

        verify(gateway).consultarPorId(id);
        verify(gateway).excluir(id);
    }

    @Test
    void deveLancarExcecaoAoExcluirUsuarioInexistente() {
        var id = 100L;

        when(gateway.consultarPorId(id)).thenReturn(Optional.empty());

        var exception = assertThrows(
                ResourceNotFoundException.class,
                () -> useCase.excluir(id)
        );

        assertEquals("Usuário não encontrado", exception.getMessage());

        verify(gateway).consultarPorId(id);
        verify(gateway, never()).excluir(id);
    }

    @Test
    void deveAtualizarTipoUsuarioDoUsuario() {
        var usuarioId = 100L;
        var tipoUsuarioId = 2L;
        var usuario = buildDomain();
        var novoTipoUsuario = new TipoUsuario(tipoUsuarioId, "Cliente");

        when(gateway.consultarPorId(usuarioId)).thenReturn(Optional.of(usuario));
        when(tipoUsuarioGateway.consultarPorId(tipoUsuarioId)).thenReturn(Optional.of(novoTipoUsuario));

        useCase.atualizarTipoUsuario(usuarioId, tipoUsuarioId);

        var captor = ArgumentCaptor.forClass(Usuario.class);

        verify(gateway).consultarPorId(usuarioId);
        verify(tipoUsuarioGateway).consultarPorId(tipoUsuarioId);
        verify(gateway).atualizar(captor.capture());

        var usuarioAtualizado = captor.getValue();

        assertEquals(100L, usuarioAtualizado.getId());
        assertEquals(novoTipoUsuario, usuarioAtualizado.getTipoUsuario());
        assertEquals(2L, usuarioAtualizado.getTipoUsuario().getId());
        assertEquals("CLIENTE", usuarioAtualizado.getTipoUsuario().getNome());
    }

    @Test
    void deveLancarExcecaoAoAtualizarTipoUsuarioDeUsuarioInexistente() {
        var usuarioId = 100L;
        var tipoUsuarioId = 2L;

        when(gateway.consultarPorId(usuarioId)).thenReturn(Optional.empty());

        var exception = assertThrows(
                ResourceNotFoundException.class,
                () -> useCase.atualizarTipoUsuario(usuarioId, tipoUsuarioId)
        );

        assertEquals("Usuário não encontrado", exception.getMessage());

        verify(gateway).consultarPorId(usuarioId);
        verify(tipoUsuarioGateway, never()).consultarPorId(any());
        verify(gateway, never()).atualizar(any(Usuario.class));
    }

    @Test
    void deveLancarExcecaoAoAtualizarTipoUsuarioInexistenteDoUsuario() {
        var usuarioId = 100L;
        var tipoUsuarioId = 2L;
        var usuario = buildDomain();

        when(gateway.consultarPorId(usuarioId)).thenReturn(Optional.of(usuario));
        when(tipoUsuarioGateway.consultarPorId(tipoUsuarioId)).thenReturn(Optional.empty());

        var exception = assertThrows(
                ResourceNotFoundException.class,
                () -> useCase.atualizarTipoUsuario(usuarioId, tipoUsuarioId)
        );

        assertEquals("Tipo de Usuário não encontrado", exception.getMessage());

        verify(gateway).consultarPorId(usuarioId);
        verify(tipoUsuarioGateway).consultarPorId(tipoUsuarioId);
        verify(gateway, never()).atualizar(any(Usuario.class));
    }
}
