package br.com.fiap.restaurant_management.infra.database.jpa.repository;

import br.com.fiap.restaurant_management.infra.database.jpa.entity.RestauranteExpedienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestauranteExpedienteRepository extends JpaRepository<RestauranteExpedienteEntity, Long> {

    List<RestauranteExpedienteEntity> findByRestauranteId(Long idRestaurante);

    boolean existsByRestauranteIdAndDiaSemanaIgnoreCase(Long idRestaurante, String diaSemana);
}
