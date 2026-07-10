package br.com.fiap.restaurant_management.infra.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TipoUsuarioInput {

    @NotBlank
    private String nome;

}
