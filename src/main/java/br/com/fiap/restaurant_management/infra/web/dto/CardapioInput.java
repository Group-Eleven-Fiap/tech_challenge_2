package br.com.fiap.restaurant_management.infra.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class CardapioInput {

    @NotNull
    private Long idRestaurante;

    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;

    @NotNull
    private BigDecimal preco;

    private Boolean disponibilidadeRestaurante;

    @NotBlank
    private String fotoUrl;
}
