package br.com.fiap.restaurant_management.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UsuarioOutputDto {

    private Long id;
    private String nome;
    private String email;
    private String login;
    private TipoUsuarioOutputDto tipoUsuario;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

}
