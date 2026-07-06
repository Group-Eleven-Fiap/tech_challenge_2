package br.com.fiap.restaurant_management.infra.config;

import br.com.fiap.restaurant_management.core.controller.MealController;
import br.com.fiap.restaurant_management.core.mapper.MealMapper;
import br.com.fiap.restaurant_management.core.gateway.MealGateway;
import br.com.fiap.restaurant_management.core.usecase.MealUseCase;
import br.com.fiap.restaurant_management.core.usecase.MealUseCaseImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class InjectionConfiguration {

    private final MealMapper mealMapper;
    private final MealGateway mealGateway;

    @Bean
    public MealUseCase mealUseCase() {
        return new MealUseCaseImpl(mealMapper, mealGateway);
    }

    @Bean
    public MealController mealController(MealUseCase mealUseCase) {
        return new MealController(mealUseCase, mealMapper);
    }
}
