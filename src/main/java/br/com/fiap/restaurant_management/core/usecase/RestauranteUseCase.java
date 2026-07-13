package br.com.fiap.restaurant_management.core.usecase;

import br.com.fiap.restaurant_management.core.domain.Restaurante;

import java.util.List;

public interface RestauranteUseCase {

    Restaurante criar(Restaurante restaurante);

    Restaurante atualizar(Long id, Restaurante restaurante);

    Restaurante consultarPorId(Long id);

    List<Restaurante> consultarTodos();

    void excluir(Long id);
}
