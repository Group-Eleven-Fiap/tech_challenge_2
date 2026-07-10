package br.com.fiap.restaurant_management.core.usecase;

import br.com.fiap.restaurant_management.core.dto.TipoUsuarioOutputDto;
import br.com.fiap.restaurant_management.core.gateway.TipoUsuarioGateway;
import br.com.fiap.restaurant_management.core.mapper.TipoUsuarioMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ConsultarTipoUsuarioUseCaseImpl implements ConsultarTipoUsuarioUseCase{

    private final TipoUsuarioMapper mapper;
    private final TipoUsuarioGateway gateway;

    @Override
    public List<TipoUsuarioOutputDto> consultar() {
        return gateway.consultar().stream()
                .map(mapper::map)
                .toList();
    }
}
