package br.com.fiap.restaurant_management.core.domain;

import br.com.fiap.restaurant_management.core.exception.BusinessRuleException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    private TipoUsuario buildTipoUsuario() {
        return new TipoUsuario(1L, "Dono");
    }

    @Test
    void deveCriarUsuarioComDadosValidos() {
        var tipoUsuario = buildTipoUsuario();

        var usuario = new Usuario(
                "Nome teste",
                "email.teste@email.com",
                "login.teste",
                "senha.teste",
                tipoUsuario
        );

        assertEquals("NOME TESTE", usuario.getNome());
        assertEquals("EMAIL.TESTE@EMAIL.COM", usuario.getEmail());
        assertEquals("LOGIN.TESTE", usuario.getLogin());
        assertEquals("SENHA.TESTE", usuario.getSenha());
        assertEquals(tipoUsuario, usuario.getTipoUsuario());
    }

    @Test
    void deveCriarUsuarioComIdEDadosValidos() {
        var tipoUsuario = buildTipoUsuario();

        var usuario = new Usuario(
                100L,
                "Nome teste",
                "email.teste@email.com",
                "login.teste",
                "senha.teste",
                tipoUsuario
        );

        assertEquals(100L, usuario.getId());
        assertEquals("NOME TESTE", usuario.getNome());
        assertEquals("EMAIL.TESTE@EMAIL.COM", usuario.getEmail());
        assertEquals("LOGIN.TESTE", usuario.getLogin());
        assertEquals("SENHA.TESTE", usuario.getSenha());
        assertEquals(tipoUsuario, usuario.getTipoUsuario());
    }

    @Test
    void deveCriarUsuarioComDatasEDadosValidos() {
        var criadoEm = LocalDateTime.now();
        var atualizadoEm = LocalDateTime.now().plusDays(1);
        var tipoUsuario = buildTipoUsuario();

        var usuario = new Usuario(
                100L,
                "Nome teste",
                "email.teste@email.com",
                "login.teste",
                "senha.teste",
                criadoEm,
                atualizadoEm,
                tipoUsuario
        );

        assertEquals(100L, usuario.getId());
        assertEquals("NOME TESTE", usuario.getNome());
        assertEquals("EMAIL.TESTE@EMAIL.COM", usuario.getEmail());
        assertEquals("LOGIN.TESTE", usuario.getLogin());
        assertEquals("SENHA.TESTE", usuario.getSenha());
        assertEquals(criadoEm, usuario.getCriadoEm());
        assertEquals(atualizadoEm, usuario.getAtualizadoEm());
        assertEquals(tipoUsuario, usuario.getTipoUsuario());
    }

    @Test
    void deveRemoverEspacosDosCamposAoCriarUsuario() {
        var tipoUsuario = buildTipoUsuario();

        var usuario = new Usuario(
                "  Nome teste  ",
                "  email.teste@email.com  ",
                "  login.teste  ",
                "  senha.teste  ",
                tipoUsuario
        );

        assertEquals("NOME TESTE", usuario.getNome());
        assertEquals("EMAIL.TESTE@EMAIL.COM", usuario.getEmail());
        assertEquals("LOGIN.TESTE", usuario.getLogin());
        assertEquals("SENHA.TESTE", usuario.getSenha());
    }

    @Test
    void deveAtualizarTipoUsuario() {
        var tipoUsuarioAtual = new TipoUsuario(1L, "Dono");
        var novoTipoUsuario = new TipoUsuario(2L, "Cliente");

        var usuario = new Usuario(
                100L,
                "Nome teste",
                "email.teste@email.com",
                "login.teste",
                "senha.teste",
                tipoUsuarioAtual
        );

        usuario.atualizarTipoUsuario(novoTipoUsuario);

        assertEquals(novoTipoUsuario, usuario.getTipoUsuario());
        assertEquals(2L, usuario.getTipoUsuario().getId());
        assertEquals("CLIENTE", usuario.getTipoUsuario().getNome());
    }

    @Test
    void deveLancarExcecaoAoCriarUsuarioComNomeNulo() {
        var tipoUsuario = buildTipoUsuario();

        var exception = assertThrows(
                BusinessRuleException.class,
                () -> new Usuario(
                        null,
                        "email.teste@email.com",
                        "login.teste",
                        "senha.teste",
                        tipoUsuario
                )
        );

        assertEquals("Nome do Tipo de Usuário não pode ser nulo ou vazio", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoAoCriarUsuarioComNomeVazio() {
        var tipoUsuario = buildTipoUsuario();

        var exception = assertThrows(
                BusinessRuleException.class,
                () -> new Usuario(
                        "",
                        "email.teste@email.com",
                        "login.teste",
                        "senha.teste",
                        tipoUsuario
                )
        );

        assertEquals("Nome do Tipo de Usuário não pode ser nulo ou vazio", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoAoCriarUsuarioComEmailNulo() {
        var tipoUsuario = buildTipoUsuario();

        var exception = assertThrows(
                BusinessRuleException.class,
                () -> new Usuario(
                        "Nome teste",
                        null,
                        "login.teste",
                        "senha.teste",
                        tipoUsuario
                )
        );

        assertEquals("Email do Usuário não pode ser nulo ou vazio", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoAoCriarUsuarioComEmailVazio() {
        var tipoUsuario = buildTipoUsuario();

        var exception = assertThrows(
                BusinessRuleException.class,
                () -> new Usuario(
                        "Nome teste",
                        "",
                        "login.teste",
                        "senha.teste",
                        tipoUsuario
                )
        );

        assertEquals("Email do Usuário não pode ser nulo ou vazio", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoAoCriarUsuarioComLoginNulo() {
        var tipoUsuario = buildTipoUsuario();

        var exception = assertThrows(
                BusinessRuleException.class,
                () -> new Usuario(
                        "Nome teste",
                        "email.teste@email.com",
                        null,
                        "senha.teste",
                        tipoUsuario
                )
        );

        assertEquals("Login do Usuário não pode ser nulo ou vazio", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoAoCriarUsuarioComLoginVazio() {
        var tipoUsuario = buildTipoUsuario();

        var exception = assertThrows(
                BusinessRuleException.class,
                () -> new Usuario(
                        "Nome teste",
                        "email.teste@email.com",
                        "",
                        "senha.teste",
                        tipoUsuario
                )
        );

        assertEquals("Login do Usuário não pode ser nulo ou vazio", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoAoCriarUsuarioComSenhaNula() {
        var tipoUsuario = buildTipoUsuario();

        var exception = assertThrows(
                BusinessRuleException.class,
                () -> new Usuario(
                        "Nome teste",
                        "email.teste@email.com",
                        "login.teste",
                        null,
                        tipoUsuario
                )
        );

        assertEquals("Senha do Usuário não pode ser nulo ou vazio", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoAoCriarUsuarioComSenhaVazia() {
        var tipoUsuario = buildTipoUsuario();

        var exception = assertThrows(
                BusinessRuleException.class,
                () -> new Usuario(
                        "Nome teste",
                        "email.teste@email.com",
                        "login.teste",
                        "",
                        tipoUsuario
                )
        );

        assertEquals("Senha do Usuário não pode ser nulo ou vazio", exception.getMessage());
    }

}
