package br.com.fiap.restaurant_management.infra.database.mapper;

import br.com.fiap.restaurant_management.core.domain.Usuario;
import br.com.fiap.restaurant_management.infra.database.jpa.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class UsuarioEntityMapper {

    private TipoUsuarioEntityMapper tipoUsuarioEntityMapper;

    public UsuarioEntityMapper(TipoUsuarioEntityMapper tipoUsuarioEntityMapper) {
        this.tipoUsuarioEntityMapper = tipoUsuarioEntityMapper;
    }

    public UsuarioEntity toEntity(Usuario domain) {
        if (domain == null) return null;
        return UsuarioEntity.builder()
                .id(domain.getId())
                .nome(domain.getNome())
                .email(domain.getEmail())
                .login(domain.getLogin())
                .senha(domain.getSenha())
                .criadoEm(domain.getCriadoEm())
                .atualizadoEm(domain.getAtualizadoEm())
                .tipoUsuario(tipoUsuarioEntityMapper.toEntity(domain.getTipoUsuario()))
                .build();
    }

    public Usuario toDomain(UsuarioEntity entity) {
        if (entity == null) return null;
        return new Usuario(
                entity.getId(),
                entity.getNome(),
                entity.getEmail(),
                entity.getLogin(),
                entity.getSenha(),
                entity.getCriadoEm(),
                entity.getAtualizadoEm(),
                tipoUsuarioEntityMapper.toDomain(entity.getTipoUsuario()));
    }
}
