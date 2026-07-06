package br.com.fiap.restaurant_management.core.controller;

import br.com.fiap.restaurant_management.core.domain.Meal;
import br.com.fiap.restaurant_management.core.dto.MealDTO;
import br.com.fiap.restaurant_management.core.mapper.MealMapper;
import br.com.fiap.restaurant_management.core.usecase.MealUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MealController {

    private final MealUseCase mealUseCase;
    private final MealMapper mealMapper;

    public MealDTO createMeal(MealDTO input) {

        Meal meal = mealMapper.toMeal(input);
        Meal newMeal = mealUseCase.create(meal);
        return mealMapper.toMealDTO(newMeal);
    }
}
