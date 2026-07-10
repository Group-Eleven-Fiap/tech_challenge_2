package br.com.fiap.restaurant_management.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CardapioDTO {

    private String nome;
    private String descricao;
    private Double preco;
    private Boolean disponibilidadeRestaurante;
    private String fotoUrl;
}
