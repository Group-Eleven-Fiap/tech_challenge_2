package br.com.fiap.restaurant_management.infra.database.mapper;

import br.com.fiap.restaurant_management.core.dto.RestauranteExpedienteDTO;
import br.com.fiap.restaurant_management.infra.database.jpa.entity.RestauranteExpedienteEntity;
import org.springframework.stereotype.Component;

@Component
public class RestauranteExpedienteEntityMapper {

    public RestauranteExpedienteEntity toEntity(RestauranteExpedienteDTO input) {
        return RestauranteExpedienteEntity.builder()
                .id(input.getId())
                .idRestaurante(input.getIdRestaurante())
                .diaSemana(input.getDiaSemana())
                .horaAbertura(input.getHoraAbertura())
                .horaFechamento(input.getHoraFechamento())
                .build();
    }

    public RestauranteExpedienteDTO toDTO(RestauranteExpedienteEntity entity) {
        return new RestauranteExpedienteDTO(
                entity.getId(),
                entity.getIdRestaurante(),
                entity.getDiaSemana(),
                entity.getHoraAbertura(),
                entity.getHoraFechamento());
    }
}
