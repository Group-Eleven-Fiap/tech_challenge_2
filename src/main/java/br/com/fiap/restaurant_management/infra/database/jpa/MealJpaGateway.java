package br.com.fiap.restaurant_management.infra.database.jpa;

import br.com.fiap.restaurant_management.core.dto.MealDTO;
import br.com.fiap.restaurant_management.core.gateway.MealGateway;
import br.com.fiap.restaurant_management.infra.database.jpa.repository.MealRepository;
import br.com.fiap.restaurant_management.infra.database.mapper.MealEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MealJpaGateway implements MealGateway {

    private final MealEntityMapper mealEntityMapper;
    private final MealRepository mealRepository;

    @Override
    public MealDTO createMeal(MealDTO input) {
        var mealEntity = mealEntityMapper.toEntity(input);
        var newEntity = mealRepository.save(mealEntity);
        return mealEntityMapper.toMealDTO(newEntity);
    }
}
