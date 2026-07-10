package br.com.fiap.restaurant_management.core.usecase;

import br.com.fiap.restaurant_management.core.dto.TipoUsuarioInputDto;
import br.com.fiap.restaurant_management.core.exception.BusinessRuleException;
import br.com.fiap.restaurant_management.core.gateway.TipoUsuarioGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AtualizarTipoUsuarioUseCaseImpl implements AtualizarTipoUsuarioUseCase{

    private final TipoUsuarioGateway gateway;

    @Override
    public void atualizar(Long id, TipoUsuarioInputDto input) {

        var tipoUsuario = gateway.consultarPorId(id)
                .orElseThrow(() -> new BusinessRuleException("Tipo de Usuário não encontrado"));

        tipoUsuario.atualizarNome(input.getNome());

        gateway.atualizar(tipoUsuario);
    }
}
