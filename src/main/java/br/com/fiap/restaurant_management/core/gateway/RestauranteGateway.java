package br.com.fiap.restaurant_management.core.gateway;

import br.com.fiap.restaurant_management.core.domain.Restaurante;

import java.util.List;
import java.util.Optional;

public interface RestauranteGateway {

    Restaurante criar(Restaurante restaurante);

    Restaurante atualizar(Restaurante restaurante);

    Optional<Restaurante> consultarPorId(Long id);

    List<Restaurante> consultarTodos();

    void excluir(Long id);
}
