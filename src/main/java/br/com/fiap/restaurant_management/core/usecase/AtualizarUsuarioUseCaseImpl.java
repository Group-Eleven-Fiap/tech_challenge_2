package br.com.fiap.restaurant_management.core.usecase;

import br.com.fiap.restaurant_management.core.domain.Usuario;
import br.com.fiap.restaurant_management.core.dto.UsuarioInputDto;
import br.com.fiap.restaurant_management.core.exception.BusinessRuleException;
import br.com.fiap.restaurant_management.core.gateway.TipoUsuarioGateway;
import br.com.fiap.restaurant_management.core.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AtualizarUsuarioUseCaseImpl implements AtualizarUsuarioUseCase{

    private final UsuarioGateway gateway;
    private final TipoUsuarioGateway tipoUsuarioGateway;

    @Override
    public void atualizar(Long id, UsuarioInputDto input) {

        var usuario = gateway.consultarPorId(id)
                .orElseThrow(() -> new BusinessRuleException("Usuário não encontrado"));

        var tipoUsuario =
                tipoUsuarioGateway.consultarPorId(input.getTipoUsuarioId())
                        .orElseThrow(() -> new BusinessRuleException("Tipo de Usuário não encontrado"));

        var atualizado = new Usuario(
                usuario.getId(),
                input.getNome(),
                input.getEmail(),
                input.getLogin(),
                input.getSenha(),
                tipoUsuario);

        gateway.atualizar(atualizado);
    }
}
