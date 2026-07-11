package br.com.fiap.restaurant_management.core.usecase;

import br.com.fiap.restaurant_management.core.dto.TipoUsuarioInputDto;
import br.com.fiap.restaurant_management.core.gateway.TipoUsuarioGateway;
import br.com.fiap.restaurant_management.core.mapper.TipoUsuarioMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CriarTipoUsuarioUseCaseImpl implements CriarTipoUsuarioUseCase{

    private final TipoUsuarioMapper mapper;
    private final TipoUsuarioGateway gateway;

    @Override
    public Long criar(TipoUsuarioInputDto input) {
        return gateway.criar(mapper.map(input));
    }
}
