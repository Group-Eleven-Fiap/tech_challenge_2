package br.com.fiap.restaurant_management.core.domain;

import lombok.Getter;

@Getter
public class Cardapio {

    private Long id;
    private String nome;
    private String descricao;
    private Double preco;
    private Boolean disponibilidadeRestaurante;
    private String fotoUrl;

    public Cardapio(String nome, String descricao, Double preco, Boolean disponibilidadeRestaurante, String fotoUrl) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.disponibilidadeRestaurante = disponibilidadeRestaurante;
        this.fotoUrl = fotoUrl;
    }

    // Valida que o preço não pode ser null ou igual a 0
    public boolean validaPreco() {
        // Retorna true quando o preço for válido (não é nulo e não é exatamente 0.0)
        return preco != null && Double.compare(preco, 0.0) != 0;
    }
}
