package br.com.fiap.restaurant_management.core.usecase;

import br.com.fiap.restaurant_management.core.dto.UsuarioOutputDto;
import br.com.fiap.restaurant_management.core.gateway.UsuarioGateway;
import br.com.fiap.restaurant_management.core.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ConsultarUsuarioUseCaseImpl implements ConsultarUsuarioUseCase{

    private final UsuarioMapper mapper;
    private final UsuarioGateway gateway;

    @Override
    public List<UsuarioOutputDto> consultar() {
        return gateway.consultar().stream()
                .map(mapper::map)
                .toList();
    }
}
