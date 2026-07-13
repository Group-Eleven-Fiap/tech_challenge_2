package br.com.fiap.restaurant_management.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class RestauranteDTO {

    private Long id;
    private String nome;
    private String endereco;
    private String tipoCozinha;
    private Long idDono;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}
