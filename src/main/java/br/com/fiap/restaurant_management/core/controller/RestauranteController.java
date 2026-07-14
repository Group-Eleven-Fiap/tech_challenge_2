package br.com.fiap.restaurant_management.core.controller;

import br.com.fiap.restaurant_management.core.dto.RestauranteDTO;
import br.com.fiap.restaurant_management.core.mapper.RestauranteMapper;
import br.com.fiap.restaurant_management.core.usecase.RestauranteUseCase;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RestauranteController {

    private final RestauranteUseCase restauranteUseCase;
    private final RestauranteMapper restauranteMapper;

    public RestauranteDTO criar(RestauranteDTO entrada) {
        return restauranteMapper.toDTO(restauranteUseCase.criar(restauranteMapper.toDomain(entrada)));
    }

    public RestauranteDTO atualizar(Long id, RestauranteDTO entrada) {
        return restauranteMapper.toDTO(restauranteUseCase.atualizar(id, restauranteMapper.toDomain(entrada)));
    }

    public RestauranteDTO consultarPorId(Long id) {
        return restauranteMapper.toDTO(restauranteUseCase.consultarPorId(id));
    }

    public List<RestauranteDTO> consultarTodos() {
        return restauranteUseCase.consultarTodos().stream().map(restauranteMapper::toDTO).toList();
    }

    public void excluir(Long id) {
        restauranteUseCase.excluir(id);
    }
}
