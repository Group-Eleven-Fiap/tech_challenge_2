package br.com.fiap.restaurant_management.core.domain;

import br.com.fiap.restaurant_management.core.exception.BusinessRuleException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TipoUsuarioTest {

    @Test
    void deveCriarTipoUsuarioComNomeValido() {
        var tipoUsuario = new TipoUsuario("Dono");

        assertEquals("DONO", tipoUsuario.getNome());
    }

    @Test
    void deveCriarTipoUsuarioComIdENomeValido() {
        var tipoUsuario = new TipoUsuario(100L, "Dono");

        assertEquals(100L, tipoUsuario.getId());
        assertEquals("DONO", tipoUsuario.getNome());
    }

    @Test
    void deveRemoverEspacosDoNomeAoCriarTipoUsuario() {
        var tipoUsuario = new TipoUsuario("  Dono  ");

        assertEquals("DONO", tipoUsuario.getNome());
    }

    @Test
    void deveAtualizarNomeDoTipoUsuario() {
        var tipoUsuario = new TipoUsuario(100L, "Dono");

        tipoUsuario.atualizarNome("Cliente");

        assertEquals(100L, tipoUsuario.getId());
        assertEquals("CLIENTE", tipoUsuario.getNome());
    }

    @Test
    void deveRemoverEspacosDoNomeAoAtualizarTipoUsuario() {
        var tipoUsuario = new TipoUsuario(100L, "Dono");

        tipoUsuario.atualizarNome("  Cliente  ");

        assertEquals("CLIENTE", tipoUsuario.getNome());
    }

    @Test
    void deveLancarExcecaoAoCriarTipoUsuarioComNomeNulo() {
        var exception = assertThrows(
                BusinessRuleException.class,
                () -> new TipoUsuario(null)
        );

        assertEquals("Nome do Tipo de Usuário não pode ser nulo ou vazio", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoAoCriarTipoUsuarioComNomeVazio() {
        var exception = assertThrows(
                BusinessRuleException.class,
                () -> new TipoUsuario("")
        );

        assertEquals("Nome do Tipo de Usuário não pode ser nulo ou vazio", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoAoCriarTipoUsuarioComIdENomeNulo() {
        var exception = assertThrows(
                BusinessRuleException.class,
                () -> new TipoUsuario(100L, null)
        );

        assertEquals("Nome do Tipo de Usuário não pode ser nulo ou vazio", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoAoCriarTipoUsuarioComIdENomeVazio() {
        var exception = assertThrows(
                BusinessRuleException.class,
                () -> new TipoUsuario(100L, "")
        );

        assertEquals("Nome do Tipo de Usuário não pode ser nulo ou vazio", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoAoAtualizarTipoUsuarioComNomeNulo() {
        var tipoUsuario = new TipoUsuario(100L, "Dono");

        var exception = assertThrows(
                BusinessRuleException.class,
                () -> tipoUsuario.atualizarNome(null)
        );

        assertEquals("Nome do Tipo de Usuário não pode ser nulo ou vazio", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoAoAtualizarTipoUsuarioComNomeVazio() {
        var tipoUsuario = new TipoUsuario(100L, "Dono");

        var exception = assertThrows(
                BusinessRuleException.class,
                () -> tipoUsuario.atualizarNome("")
        );

        assertEquals("Nome do Tipo de Usuário não pode ser nulo ou vazio", exception.getMessage());
    }
}
