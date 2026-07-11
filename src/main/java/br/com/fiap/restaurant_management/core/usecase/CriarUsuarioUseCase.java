package br.com.fiap.restaurant_management.core.usecase;

import br.com.fiap.restaurant_management.core.dto.UsuarioInputDto;

public interface CriarUsuarioUseCase {

    Long criar(UsuarioInputDto input);
}
