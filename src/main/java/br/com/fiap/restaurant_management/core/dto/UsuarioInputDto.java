package br.com.fiap.restaurant_management.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UsuarioInputDto {

    private String nome;
    private String email;
    private String login;
    private String senha;
    private Long tipoUsuarioId;

}
