package br.com.fiap.restaurant_management.core.gateway;

import br.com.fiap.restaurant_management.core.dto.RestauranteExpedienteDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestauranteExpedienteGateway {

    RestauranteExpedienteDTO createExpediente(RestauranteExpedienteDTO expedienteDTO);

    Optional<RestauranteExpedienteDTO> findById(UUID id);

    List<RestauranteExpedienteDTO> findByRestaurante(UUID idRestaurante);

    boolean existsByRestauranteAndDia(UUID idRestaurante, String diaSemana);

    RestauranteExpedienteDTO updateExpediente(RestauranteExpedienteDTO expedienteDTO);

    void deleteById(UUID id);
}
