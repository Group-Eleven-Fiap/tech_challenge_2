package br.com.fiap.restaurant_management.core.usecase;

import br.com.fiap.restaurant_management.core.dto.TipoUsuarioInputDto;
import br.com.fiap.restaurant_management.core.dto.TipoUsuarioOutputDto;

import java.util.List;

public interface TipoUsuarioUseCase {

    Long criar(TipoUsuarioInputDto input);

    void atualizar(Long id, TipoUsuarioInputDto input);

    void excluir(Long id);

    List<TipoUsuarioOutputDto> consultar();
}
