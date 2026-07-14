package br.com.fiap.restaurant_management.infra.database.jpa.repository;

import br.com.fiap.restaurant_management.infra.database.jpa.entity.RestauranteExpedienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RestauranteExpedienteRepository extends JpaRepository<RestauranteExpedienteEntity, UUID> {

    List<RestauranteExpedienteEntity> findByRestauranteId(Long idRestaurante);

    boolean existsByRestauranteIdAndDiaSemanaIgnoreCase(Long idRestaurante, String diaSemana);
}
