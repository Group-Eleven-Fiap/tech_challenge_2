package br.com.fiap.restaurant_management.core.domain;

import br.com.fiap.restaurant_management.core.exception.BusinessRuleException;
import lombok.Getter;

@Getter
public class TipoUsuario {

    private Long id;
    private String nome;

    public TipoUsuario(String nome) {
        this.nome = validarNome(nome);
    }

    public TipoUsuario(Long id, String nome) {
        this.id = id;
        this.nome = validarNome(nome);
    }

    private String validarNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            throw new BusinessRuleException("Nome do Tipo de Usuário não pode ser nulo ou vazio");
        }
        return nome.trim().toUpperCase();
    }

    public void atualizarNome(String nome) {
        this.nome = validarNome(nome);
    }

}
