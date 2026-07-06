package br.com.fiap.restaurant_management.infra.database.mapper;

import br.com.fiap.restaurant_management.core.dto.MealDTO;
import br.com.fiap.restaurant_management.infra.database.jpa.entity.MealEntity;
import org.springframework.stereotype.Component;

@Component
public class MealEntityMapper {

    public MealEntity toEntity(MealDTO input) {
        return MealEntity.builder()
                .name(input.getName())
                .description(input.getDescription())
                .price(input.getPrice())
                .pictureUrl(input.getPictureUrl())
                .build();
    }

    public MealDTO toMealDTO(MealEntity entity) {
        return new MealDTO(entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getPictureUrl());
    }
}
