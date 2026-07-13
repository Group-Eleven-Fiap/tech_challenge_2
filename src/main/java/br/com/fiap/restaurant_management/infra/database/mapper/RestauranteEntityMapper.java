package br.com.fiap.restaurant_management.infra.database.mapper;

import br.com.fiap.restaurant_management.core.domain.Restaurante;
import br.com.fiap.restaurant_management.infra.database.jpa.entity.RestauranteEntity;
import br.com.fiap.restaurant_management.infra.database.jpa.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class RestauranteEntityMapper {

    public RestauranteEntity toEntity(Restaurante restaurante, UsuarioEntity dono) {
        return RestauranteEntity.builder()
                .id(restaurante.getId())
                .nome(restaurante.getNome())
                .endereco(restaurante.getEndereco())
                .tipoCozinha(restaurante.getTipoCozinha())
                .horarioFuncionamento(restaurante.getHorarioFuncionamento())
                .dono(dono)
                .criadoEm(restaurante.getCriadoEm())
                .atualizadoEm(restaurante.getAtualizadoEm())
                .build();
    }

    public Restaurante toDomain(RestauranteEntity entity) {
        return new Restaurante(
                entity.getId(), entity.getNome(), entity.getEndereco(), entity.getTipoCozinha(),
                entity.getHorarioFuncionamento(), entity.getDono().getId(),
                entity.getCriadoEm(), entity.getAtualizadoEm());
    }
}
