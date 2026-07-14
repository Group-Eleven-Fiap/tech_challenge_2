package br.com.fiap.restaurant_management.infra.database.jpa;

import br.com.fiap.restaurant_management.core.domain.TipoUsuario;
import br.com.fiap.restaurant_management.core.domain.Usuario;
import br.com.fiap.restaurant_management.infra.database.jpa.entity.TipoUsuarioEntity;
import br.com.fiap.restaurant_management.infra.database.jpa.entity.UsuarioEntity;
import br.com.fiap.restaurant_management.infra.database.jpa.repository.UsuarioRepository;
import br.com.fiap.restaurant_management.infra.database.mapper.UsuarioEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioJpaGatewayTest {

    @Mock
    private UsuarioEntityMapper mapper;

    @Mock
    private UsuarioRepository repository;

    private UsuarioJpaGateway gateway;

    @BeforeEach
    void setUp() {
        gateway = new UsuarioJpaGateway(mapper, repository);
    }

    private TipoUsuario buildTipoUsuario() {
        return new TipoUsuario(1L, "Dono");
    }

    private TipoUsuarioEntity buildTipoUsuarioEntity() {
        return TipoUsuarioEntity.builder()
                .id(1L)
                .nome("DONO")
                .build();
    }

    private Usuario buildDomain() {
        return new Usuario(
                100L,
                "Nome teste",
                "email.teste@email.com",
                "login.teste",
                "senha.teste",
                LocalDateTime.now(),
                LocalDateTime.now(),
                buildTipoUsuario()
        );
    }

    private UsuarioEntity buildEntity() {
        return UsuarioEntity.builder()
                .id(100L)
                .nome("NOME TESTE")
                .email("EMAIL.TESTE@EMAIL.COM")
                .login("LOGIN.TESTE")
                .senha("SENHA.TESTE")
                .criadoEm(LocalDateTime.now())
                .atualizadoEm(LocalDateTime.now())
                .tipoUsuario(buildTipoUsuarioEntity())
                .build();
    }

    @Test
    void deveCriarUsuario() {
        var domain = buildDomain();
        var entity = buildEntity();

        when(mapper.toEntity(domain)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);

        var result = gateway.criar(domain);

        assertNotNull(result);
        assertEquals(100L, result);

        verify(mapper).toEntity(domain);
        verify(repository).save(entity);
    }

    @Test
    void deveAtualizarUsuario() {
        var domain = buildDomain();
        var entity = buildEntity();

        when(mapper.toEntity(domain)).thenReturn(entity);

        gateway.atualizar(domain);

        verify(mapper).toEntity(domain);
        verify(repository).save(entity);
    }

    @Test
    void deveExcluirUsuario() {
        var id = 100L;

        gateway.excluir(id);

        verify(repository).deleteById(id);
    }

    @Test
    void deveConsultarUsuarios() {
        var entity = buildEntity();
        var domain = buildDomain();

        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        var result = gateway.consultar();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(100L, result.getFirst().getId());
        assertEquals("NOME TESTE", result.getFirst().getNome());
        assertEquals("EMAIL.TESTE@EMAIL.COM", result.getFirst().getEmail());
        assertEquals("LOGIN.TESTE", result.getFirst().getLogin());
        assertEquals("SENHA.TESTE", result.getFirst().getSenha());
        assertEquals(1L, result.getFirst().getTipoUsuario().getId());
        assertEquals("DONO", result.getFirst().getTipoUsuario().getNome());

        verify(repository).findAll();
        verify(mapper).toDomain(entity);
    }

    @Test
    void deveConsultarUsuarioPorId() {
        var id = 100L;
        var entity = buildEntity();
        var domain = buildDomain();

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        var result = gateway.consultarPorId(id);

        assertTrue(result.isPresent());
        assertEquals(100L, result.get().getId());
        assertEquals("NOME TESTE", result.get().getNome());
        assertEquals("EMAIL.TESTE@EMAIL.COM", result.get().getEmail());
        assertEquals("LOGIN.TESTE", result.get().getLogin());
        assertEquals("SENHA.TESTE", result.get().getSenha());
        assertEquals(1L, result.get().getTipoUsuario().getId());
        assertEquals("DONO", result.get().getTipoUsuario().getNome());

        verify(repository).findById(id);
        verify(mapper).toDomain(entity);
    }

    @Test
    void deveRetornarOptionalVazioAoConsultarUsuarioPorIdInexistente() {
        var id = 100L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        var result = gateway.consultarPorId(id);

        assertFalse(result.isPresent());

        verify(repository).findById(id);
    }

    @Test
    void deveAlterarTipoUsuarioSemExecutarAcao() {
        var usuarioId = 100L;
        var tipoUsuarioId = 1L;

        gateway.alterarTipoUsuario(usuarioId, tipoUsuarioId);
    }
}
