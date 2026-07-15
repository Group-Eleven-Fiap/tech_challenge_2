package br.com.fiap.restaurant_management.core.gateway;

import br.com.fiap.restaurant_management.core.dto.RestauranteExpedienteDTO;

import java.util.List;
import java.util.Optional;

public interface RestauranteExpedienteGateway {

    RestauranteExpedienteDTO createExpediente(RestauranteExpedienteDTO expedienteDTO);

    Optional<RestauranteExpedienteDTO> findById(Long id);

    List<RestauranteExpedienteDTO> findByRestaurante(Long idRestaurante);

    boolean existsByRestauranteAndDia(Long idRestaurante, String diaSemana);

    RestauranteExpedienteDTO updateExpediente(RestauranteExpedienteDTO expedienteDTO);

    void deleteById(Long id);
}
