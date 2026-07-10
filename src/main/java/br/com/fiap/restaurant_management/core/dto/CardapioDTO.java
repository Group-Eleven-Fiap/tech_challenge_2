package br.com.fiap.restaurant_management.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class CardapioDTO {

    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Boolean disponibilidadeRestaurante;
    private String fotoUrl;
}
