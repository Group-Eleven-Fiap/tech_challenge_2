package br.com.fiap.restaurant_management.infra.database.jpa.repository;

import br.com.fiap.restaurant_management.infra.database.jpa.entity.CardapioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardapioRepository extends JpaRepository<CardapioEntity, Long> {
}
