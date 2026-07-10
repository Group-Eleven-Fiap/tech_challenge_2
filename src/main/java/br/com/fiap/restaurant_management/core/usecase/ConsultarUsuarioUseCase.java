package br.com.fiap.restaurant_management.core.usecase;

import br.com.fiap.restaurant_management.core.dto.TipoUsuarioOutputDto;
import br.com.fiap.restaurant_management.core.dto.UsuarioOutputDto;

import java.util.List;

public interface ConsultarUsuarioUseCase {

    List<UsuarioOutputDto> consultar();
}
