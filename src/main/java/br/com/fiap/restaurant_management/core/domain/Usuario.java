package br.com.fiap.restaurant_management.core.domain;

import br.com.fiap.restaurant_management.core.exception.BusinessRuleException;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Usuario {

    private Long id;
    private String nome;
    private String email;
    private String login;
    private String senha;
    private TipoUsuario tipoUsuario;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    public Usuario(String nome, String email, String login, String senha, TipoUsuario tipoUsuario) {
        this.nome = validarNome(nome);
        this.email = validarEmail(email);
        this.login = validarLogin(login);
        this.senha = validarSenha(senha);
        this.tipoUsuario = tipoUsuario;
    }

    public Usuario(Long id, String nome, String email, String login, String senha, TipoUsuario tipoUsuario) {
        this.id = id;
        this.nome = validarNome(nome);
        this.email = validarEmail(email);
        this.login = validarLogin(login);
        this.senha = validarSenha(senha);
        this.tipoUsuario = tipoUsuario;
    }

    public Usuario(Long id, String nome, String email, String login, String senha, LocalDateTime criadoEm, LocalDateTime atualizadoEm, TipoUsuario tipoUsuario) {
        this.id = id;
        this.nome = validarNome(nome);
        this.email = validarEmail(email);
        this.login = validarLogin(login);
        this.senha = validarSenha(senha);
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
        this.tipoUsuario = tipoUsuario;
    }

    public void atualizarTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    private String validarNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            throw new BusinessRuleException("Nome do Tipo de Usuário não pode ser nulo ou vazio");
        }
        return nome.trim().toUpperCase();
    }

    private String validarEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new BusinessRuleException("Email do Usuário não pode ser nulo ou vazio");
        }
        return email.trim().toUpperCase();
    }

    private String validarLogin(String login) {
        if (login == null || login.isEmpty()) {
            throw new BusinessRuleException("Login do Usuário não pode ser nulo ou vazio");
        }
        return login.trim().toUpperCase();
    }

    private String validarSenha(String senha) {
        if (senha == null || senha.isEmpty()) {
            throw new BusinessRuleException("Senha do Usuário não pode ser nulo ou vazio");
        }
        return senha.trim().toUpperCase();
    }


}
