package br.com.fiap.restaurant_management.core.gateway;

import br.com.fiap.restaurant_management.core.dto.MealDTO;

public interface MealGateway {

    MealDTO createMeal(MealDTO mealDTO);
}
