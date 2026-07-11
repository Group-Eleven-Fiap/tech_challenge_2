package br.com.fiap.restaurant_management.core.usecase;

import br.com.fiap.restaurant_management.core.exception.BusinessRuleException;
import br.com.fiap.restaurant_management.core.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExcluirUsuarioUseCaseImpl implements ExcluirUsuarioUseCase{

    private final UsuarioGateway gateway;

    @Override
    public void excluir(Long id) {
        gateway.consultarPorId(id).orElseThrow(() -> new BusinessRuleException("Usuário não encontrado"));
        gateway.excluir(id);
    }
}
