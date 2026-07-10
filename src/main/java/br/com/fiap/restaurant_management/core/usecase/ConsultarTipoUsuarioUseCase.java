package br.com.fiap.restaurant_management.core.usecase;

import br.com.fiap.restaurant_management.core.dto.TipoUsuarioOutputDto;

import java.util.List;

public interface ConsultarTipoUsuarioUseCase {

    List<TipoUsuarioOutputDto> consultar();
}
