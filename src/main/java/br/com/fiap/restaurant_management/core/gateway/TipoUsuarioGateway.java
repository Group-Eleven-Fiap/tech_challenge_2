package br.com.fiap.restaurant_management.core.gateway;

import br.com.fiap.restaurant_management.core.domain.TipoUsuario;

import java.util.List;
import java.util.Optional;

public interface TipoUsuarioGateway {

    Long criar(TipoUsuario tipoUsuario);

    void atualizar(TipoUsuario tipoUsuario);

    void excluir(Long id);

    List<TipoUsuario> consultar();

    Optional<TipoUsuario> consultarPorId(Long id);

}
