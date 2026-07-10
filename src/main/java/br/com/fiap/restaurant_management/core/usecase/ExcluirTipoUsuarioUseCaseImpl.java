package br.com.fiap.restaurant_management.core.usecase;

import br.com.fiap.restaurant_management.core.exception.BusinessRuleException;
import br.com.fiap.restaurant_management.core.gateway.TipoUsuarioGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExcluirTipoUsuarioUseCaseImpl implements ExcluirTipoUsuarioUseCase{

    private final TipoUsuarioGateway gateway;

    @Override
    public void excluir(Long id) {
        gateway.consultarPorId(id)
                .orElseThrow(() -> new BusinessRuleException("Tipo de Usuário não encontrado"));

        gateway.excluir(id);
    }
}
