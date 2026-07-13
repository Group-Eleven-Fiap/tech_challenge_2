package br.com.fiap.restaurant_management.infra.database.mapper;

import br.com.fiap.restaurant_management.core.dto.RestauranteExpedienteDTO;
import br.com.fiap.restaurant_management.infra.database.jpa.entity.RestauranteExpedienteEntity;
import br.com.fiap.restaurant_management.infra.database.jpa.entity.RestauranteEntity;
import org.springframework.stereotype.Component;

@Component
public class RestauranteExpedienteEntityMapper {

    public RestauranteExpedienteEntity toEntity(RestauranteExpedienteDTO input, RestauranteEntity restaurante) {
        return RestauranteExpedienteEntity.builder()
                .id(input.getId())
                .restaurante(restaurante)
                .diaSemana(input.getDiaSemana())
                .horaAbertura(input.getHoraAbertura())
                .horaFechamento(input.getHoraFechamento())
                .build();
    }

    public RestauranteExpedienteDTO toDTO(RestauranteExpedienteEntity entity) {
        return new RestauranteExpedienteDTO(
                entity.getId(),
                entity.getRestaurante().getId(),
                entity.getDiaSemana(),
                entity.getHoraAbertura(),
                entity.getHoraFechamento());
    }
}
