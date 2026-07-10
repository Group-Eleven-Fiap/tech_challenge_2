package br.com.fiap.restaurant_management.core.usecase;

import br.com.fiap.restaurant_management.core.dto.UsuarioInputDto;
import br.com.fiap.restaurant_management.core.exception.BusinessRuleException;
import br.com.fiap.restaurant_management.core.gateway.TipoUsuarioGateway;
import br.com.fiap.restaurant_management.core.gateway.UsuarioGateway;
import br.com.fiap.restaurant_management.core.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CriarUsuarioUseCaseImpl implements CriarUsuarioUseCase{

    private final UsuarioMapper mapper;
    private final UsuarioGateway gateway;
    private final TipoUsuarioGateway tipoUsuarioGateway;

    @Override
    public Long criar(UsuarioInputDto input) {

        var tipoUsuario =
                tipoUsuarioGateway.consultarPorId(input.getTipoUsuarioId())
                        .orElseThrow(() -> new BusinessRuleException("Tipo de Usuário não encontrado"));

        return gateway.criar(mapper.map(input, tipoUsuario));
    }
}
