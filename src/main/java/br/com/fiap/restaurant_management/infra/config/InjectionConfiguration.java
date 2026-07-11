package br.com.fiap.restaurant_management.infra.config;

import br.com.fiap.restaurant_management.core.controller.MealController;
import br.com.fiap.restaurant_management.core.controller.RestauranteExpedienteController;
import br.com.fiap.restaurant_management.core.gateway.RestauranteExpedienteGateway;
import br.com.fiap.restaurant_management.core.mapper.MealMapper;
import br.com.fiap.restaurant_management.core.gateway.MealGateway;
import br.com.fiap.restaurant_management.core.mapper.RestauranteExpedienteMapper;
import br.com.fiap.restaurant_management.core.usecase.MealUseCase;
import br.com.fiap.restaurant_management.core.usecase.MealUseCaseImpl;
import br.com.fiap.restaurant_management.core.usecase.RestauranteExpedienteUseCase;
import br.com.fiap.restaurant_management.core.usecase.RestauranteExpedienteUseCaseImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class InjectionConfiguration {

    private final MealMapper mealMapper;
    private final MealGateway mealGateway;
    private final RestauranteExpedienteMapper restauranteExpedienteMapper;
    private final RestauranteExpedienteGateway restauranteExpedienteGateway;

    @Bean
    public MealUseCase mealUseCase() {
        return new MealUseCaseImpl(mealMapper, mealGateway);
    }

    @Bean
    public MealController mealController(MealUseCase mealUseCase) {
        return new MealController(mealUseCase, mealMapper);
    }

    @Bean
    public RestauranteExpedienteUseCase restauranteExpedienteUseCase() {
        return new RestauranteExpedienteUseCaseImpl(restauranteExpedienteMapper, restauranteExpedienteGateway);
    }

    @Bean
    public RestauranteExpedienteController restauranteExpedienteController(RestauranteExpedienteUseCase restauranteExpedienteUseCase) {
        return new RestauranteExpedienteController(restauranteExpedienteUseCase, restauranteExpedienteMapper);
    }
}
