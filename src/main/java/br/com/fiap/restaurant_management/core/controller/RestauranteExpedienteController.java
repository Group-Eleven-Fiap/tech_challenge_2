package br.com.fiap.restaurant_management.core.controller;

import br.com.fiap.restaurant_management.core.domain.RestauranteExpediente;
import br.com.fiap.restaurant_management.core.dto.RestauranteExpedienteDTO;
import br.com.fiap.restaurant_management.core.mapper.RestauranteExpedienteMapper;
import br.com.fiap.restaurant_management.core.usecase.RestauranteExpedienteUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class RestauranteExpedienteController {

    private final RestauranteExpedienteUseCase restauranteExpedienteUseCase;
    private final RestauranteExpedienteMapper restauranteExpedienteMapper;

    public RestauranteExpedienteDTO criarExpediente(RestauranteExpedienteDTO input) {
        RestauranteExpediente expediente = restauranteExpedienteMapper.toDomain(input);
        RestauranteExpediente novoExpediente = restauranteExpedienteUseCase.create(expediente);
        return restauranteExpedienteMapper.toDTO(novoExpediente);
    }

    public RestauranteExpedienteDTO buscarPorId(Long id) {
        RestauranteExpediente expediente = restauranteExpedienteUseCase.findById(id);
        return restauranteExpedienteMapper.toDTO(expediente);
    }

    public List<RestauranteExpedienteDTO> listarPorRestaurante(Long idRestaurante) {
        return restauranteExpedienteUseCase.findByRestaurante(idRestaurante).stream()
                .map(restauranteExpedienteMapper::toDTO)
                .toList();
    }

    public RestauranteExpedienteDTO atualizarExpediente(Long id, RestauranteExpedienteDTO input) {
        RestauranteExpediente expediente = restauranteExpedienteMapper.toDomain(input);
        RestauranteExpediente atualizado = restauranteExpedienteUseCase.update(id, expediente);
        return restauranteExpedienteMapper.toDTO(atualizado);
    }

    public void deletarExpediente(Long id) {
        restauranteExpedienteUseCase.delete(id);
    }
}
