package br.com.fiap.restaurant_management.infra.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UsuarioTipoUsuarioInput {

    @NotNull
    private Long usuarioId;

    @NotNull
    private Long tipoUsuarioId;

}
