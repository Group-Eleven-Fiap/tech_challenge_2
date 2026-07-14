package br.com.fiap.restaurant_management.core.usecase;

import br.com.fiap.restaurant_management.core.dto.UsuarioInputDto;
import br.com.fiap.restaurant_management.core.dto.UsuarioOutputDto;

import java.util.List;

public interface UsuarioUseCase {

    Long criar(UsuarioInputDto input);

    void excluir(Long id);

    List<UsuarioOutputDto> consultar();

    void atualizar(Long id, UsuarioInputDto input);

    void atualizarTipoUsuario(Long usuarioId, Long tipoUsuarioId);
}
