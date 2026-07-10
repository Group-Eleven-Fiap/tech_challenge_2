package br.com.fiap.restaurant_management.core.usecase;

import br.com.fiap.restaurant_management.core.exception.BusinessRuleException;
import br.com.fiap.restaurant_management.core.gateway.TipoUsuarioGateway;
import br.com.fiap.restaurant_management.core.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AtualizarUsuarioTipoUsuarioUseCaseImpl implements AtualizarUsuarioTipoUsuarioUseCase{

    private final UsuarioGateway gateway;
    private final TipoUsuarioGateway tipoUsuarioGateway;

    @Override
    public void atualizar(Long usuarioId, Long tipoUsuarioId) {

        var usuario = gateway.consultarPorId(usuarioId)
                .orElseThrow(() -> new BusinessRuleException("Usuário não encontrado"));

        var tipoUsuario =
                tipoUsuarioGateway.consultarPorId(tipoUsuarioId)
                        .orElseThrow(() -> new BusinessRuleException("Tipo de Usuário não encontrado"));

        usuario.atualizarTipoUsuario(tipoUsuario);

        gateway.atualizar(usuario);
    }
}
