package br.com.fiap.restaurant_management.core.domain;

import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class Cardapio {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Boolean disponibilidadeRestaurante;
    private String fotoUrl;

    public Cardapio(String nome, String descricao, BigDecimal preco, Boolean disponibilidadeRestaurante, String fotoUrl) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Descrição é obrigatória");
        }
        if (preco == null || preco.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }
        if (disponibilidadeRestaurante == null) {
            throw new IllegalArgumentException("Disponibilidade no restaurante é obrigatória");
        }
        if (fotoUrl == null || fotoUrl.isBlank()) {
            throw new IllegalArgumentException("Caminho da foto é obrigatório");
        }

        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.disponibilidadeRestaurante = disponibilidadeRestaurante;
        this.fotoUrl = fotoUrl;
    }
}
