package br.com.fiap.restaurant_management.infra.database.mapper;

import br.com.fiap.restaurant_management.core.domain.TipoUsuario;
import br.com.fiap.restaurant_management.infra.database.jpa.entity.TipoUsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class TipoUsuarioEntityMapper {

    public TipoUsuarioEntity toEntity(TipoUsuario domain) {
        if (domain == null) return null;
        return TipoUsuarioEntity.builder()
                .id(domain.getId())
                .nome(domain.getNome())
                .build();
    }

    public TipoUsuario toDomain(TipoUsuarioEntity entity) {
        if (entity == null) return null;
        return new TipoUsuario(entity.getId(), entity.getNome());
    }
}
