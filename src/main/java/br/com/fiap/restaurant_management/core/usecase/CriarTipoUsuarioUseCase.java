package br.com.fiap.restaurant_management.core.usecase;

import br.com.fiap.restaurant_management.core.dto.TipoUsuarioInputDto;

public interface CriarTipoUsuarioUseCase {

    Long criar(TipoUsuarioInputDto input);
}
