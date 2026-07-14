package br.com.fiap.restaurant_management.core.domain;

import br.com.fiap.restaurant_management.core.exception.BusinessRuleException;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Restaurante {

    private final Long id;
    private final String nome;
    private final String endereco;
    private final String tipoCozinha;
    private final Long idDono;
    private final LocalDateTime criadoEm;
    private final LocalDateTime atualizadoEm;

    public Restaurante(String nome, String endereco, String tipoCozinha, Long idDono) {
        this(null, nome, endereco, tipoCozinha, idDono, null, null);
    }

    public Restaurante(Long id, String nome, String endereco, String tipoCozinha, Long idDono,
                       LocalDateTime criadoEm, LocalDateTime atualizadoEm) {
        this.id = id;
        this.nome = validarTexto(nome, "Nome");
        this.endereco = validarTexto(endereco, "Endereço");
        this.tipoCozinha = validarTexto(tipoCozinha, "Tipo de cozinha");
        if (idDono == null || idDono <= 0) {
            throw new BusinessRuleException("Dono do restaurante é obrigatório");
        }
        this.idDono = idDono;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
    }

    private String validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new BusinessRuleException(campo + " do restaurante é obrigatório");
        }
        return valor.trim();
    }
}
