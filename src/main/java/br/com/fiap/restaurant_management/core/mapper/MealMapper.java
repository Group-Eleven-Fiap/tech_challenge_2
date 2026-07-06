package br.com.fiap.restaurant_management.core.mapper;

import br.com.fiap.restaurant_management.core.domain.Meal;
import br.com.fiap.restaurant_management.core.dto.MealDTO;
import org.springframework.stereotype.Component;

@Component
public class MealMapper {

    public Meal toMeal(MealDTO input) {
        return new Meal(input.getName(),
                input.getDescription(),
                input.getPrice(),
                input.getPictureUrl());
    }

    public MealDTO toMealDTO(Meal meal) {
        return new MealDTO(meal.getName(),
                meal.getDescription(),
                meal.getPrice(),
                meal.getPictureUrl());
    }
}
