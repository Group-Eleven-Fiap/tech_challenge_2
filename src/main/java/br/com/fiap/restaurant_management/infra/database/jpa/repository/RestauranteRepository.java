package br.com.fiap.restaurant_management.infra.database.jpa.repository;

import br.com.fiap.restaurant_management.infra.database.jpa.entity.RestauranteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestauranteRepository extends JpaRepository<RestauranteEntity, Long> {
}
