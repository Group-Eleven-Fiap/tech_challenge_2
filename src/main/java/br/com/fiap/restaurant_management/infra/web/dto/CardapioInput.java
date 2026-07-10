package br.com.fiap.restaurant_management.infra.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CardapioInput {

    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;

    @NotNull
    private Double preco;

    private Boolean disponibilidadeRestaurante;

    @NotBlank
    private String fotoUrl;
}
