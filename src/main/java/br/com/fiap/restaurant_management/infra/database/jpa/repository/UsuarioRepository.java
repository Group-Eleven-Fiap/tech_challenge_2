package br.com.fiap.restaurant_management.infra.database.jpa.repository;

import br.com.fiap.restaurant_management.infra.database.jpa.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
}
