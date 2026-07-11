package br.com.fiap.restaurant_management.core.usecase;

import br.com.fiap.restaurant_management.core.domain.RestauranteExpediente;

import java.util.List;
import java.util.UUID;

public interface RestauranteExpedienteUseCase {

    RestauranteExpediente create(RestauranteExpediente expediente);

    RestauranteExpediente findById(UUID id);

    List<RestauranteExpediente> findByRestaurante(UUID idRestaurante);

    RestauranteExpediente update(UUID id, RestauranteExpediente expediente);

    void delete(UUID id);
}
