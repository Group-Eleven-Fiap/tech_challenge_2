package br.com.fiap.restaurant_management.core.gateway;

import br.com.fiap.restaurant_management.core.domain.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioGateway {

    Long criar(Usuario usuario);

    void atualizar(Usuario usuario);

    void excluir(Long id);

    List<Usuario> consultar();

    Optional<Usuario> consultarPorId(Long id);

    void alterarTipoUsuario(Long usuarioId, Long tipoUsuarioId);

}
