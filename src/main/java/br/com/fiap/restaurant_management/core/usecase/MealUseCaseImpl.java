package br.com.fiap.restaurant_management.core.usecase;

import br.com.fiap.restaurant_management.core.domain.Meal;
import br.com.fiap.restaurant_management.core.dto.MealDTO;
import br.com.fiap.restaurant_management.core.exception.BusinessRuleException;
import br.com.fiap.restaurant_management.core.gateway.MealGateway;
import br.com.fiap.restaurant_management.core.mapper.MealMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MealUseCaseImpl implements MealUseCase {

    private final MealMapper mealMapper;
    private final MealGateway mealGateway;

    @Override
    public Meal create(Meal meal) {

        validateMeal(meal);
        var mealDTO = mealMapper.toMealDTO(meal);
        MealDTO newMeal = mealGateway.createMeal(mealDTO);
        return mealMapper.toMeal(newMeal);
    }

    private void validateMeal(Meal meal) {
        if (!meal.validatePrice()) {
            throw new BusinessRuleException("price cannot be null or 0");
        }
    }
}
