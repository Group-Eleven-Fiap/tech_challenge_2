package br.com.fiap.restaurant_management.infra.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UsuarioInput {

    @NotBlank
    private String nome;

    @NotBlank
    private String email;

    @NotBlank
    private String login;

    @NotBlank
    private String senha;

    @NotNull
    private Long tipoUsuarioId;

}
