package br.com.fiap.restaurant_management.core.usecase;

import br.com.fiap.restaurant_management.core.domain.RestauranteExpediente;

import java.util.List;

public interface RestauranteExpedienteUseCase {

    RestauranteExpediente create(RestauranteExpediente expediente);

    RestauranteExpediente findById(Long id);

    List<RestauranteExpediente> findByRestaurante(Long idRestaurante);

    RestauranteExpediente update(Long id, RestauranteExpediente expediente);

    void delete(Long id);
}
