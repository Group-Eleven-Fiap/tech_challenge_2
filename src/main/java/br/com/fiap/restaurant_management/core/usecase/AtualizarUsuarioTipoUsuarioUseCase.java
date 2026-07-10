package br.com.fiap.restaurant_management.core.usecase;

import br.com.fiap.restaurant_management.core.dto.UsuarioInputDto;

public interface AtualizarUsuarioTipoUsuarioUseCase {

    void atualizar(Long usuarioId, Long tipoUsuarioId);
}
