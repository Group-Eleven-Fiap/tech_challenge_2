package br.com.fiap.restaurant_management.infra.database.jpa;

import br.com.fiap.restaurant_management.core.domain.TipoUsuario;
import br.com.fiap.restaurant_management.infra.database.jpa.entity.TipoUsuarioEntity;
import br.com.fiap.restaurant_management.infra.database.jpa.repository.TipoUsuarioRepository;
import br.com.fiap.restaurant_management.infra.database.mapper.TipoUsuarioEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TipoUsuarioJpaGatewayTest {

    @Mock
    private TipoUsuarioEntityMapper mapper;

    @Mock
    private TipoUsuarioRepository repository;

    private TipoUsuarioJpaGateway gateway;

    @BeforeEach
    void setUp() {
        gateway = new TipoUsuarioJpaGateway(mapper, repository);
    }

    private TipoUsuario buildDomain() {
        return new TipoUsuario(100L, "Dono");
    }

    private TipoUsuarioEntity buildEntity() {
        return TipoUsuarioEntity.builder()
                .id(100L)
                .nome("DONO")
                .build();
    }

    @Test
    void deveCriarTipoUsuario() {
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
    void deveAtualizarTipoUsuario() {
        var domain = buildDomain();
        var entity = buildEntity();

        when(mapper.toEntity(domain)).thenReturn(entity);

        gateway.atualizar(domain);

        verify(mapper).toEntity(domain);
        verify(repository).save(entity);
    }

    @Test
    void deveExcluirTipoUsuario() {
        var id = 100L;

        gateway.excluir(id);

        verify(repository).deleteById(id);
    }

    @Test
    void deveConsultarTiposUsuario() {
        var entity = buildEntity();
        var domain = buildDomain();

        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        var result = gateway.consultar();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(100L, result.getFirst().getId());
        assertEquals("DONO", result.getFirst().getNome());

        verify(repository).findAll();
        verify(mapper).toDomain(entity);
    }

    @Test
    void deveConsultarTipoUsuarioPorId() {
        var id = 100L;
        var entity = buildEntity();
        var domain = buildDomain();

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        var result = gateway.consultarPorId(id);

        assertTrue(result.isPresent());
        assertEquals(100L, result.get().getId());
        assertEquals("DONO", result.get().getNome());

        verify(repository).findById(id);
        verify(mapper).toDomain(entity);
    }

    @Test
    void deveRetornarOptionalVazioAoConsultarTipoUsuarioPorIdInexistente() {
        var id = 100L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        var result = gateway.consultarPorId(id);

        assertFalse(result.isPresent());

        verify(repository).findById(id);
    }
}
