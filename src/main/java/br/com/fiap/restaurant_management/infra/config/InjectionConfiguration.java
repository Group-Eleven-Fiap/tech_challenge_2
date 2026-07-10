package br.com.fiap.restaurant_management.infra.config;

import br.com.fiap.restaurant_management.core.controller.CardapioController;
import br.com.fiap.restaurant_management.core.mapper.CardapioMapper;
import br.com.fiap.restaurant_management.core.gateway.CardapioGateway;
import br.com.fiap.restaurant_management.core.usecase.CardapioUseCase;
import br.com.fiap.restaurant_management.core.usecase.CardapioUseCaseImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class InjectionConfiguration {

    private final CardapioMapper cardapioMapper;
    private final CardapioGateway cardapioGateway;

    @Bean
    public CardapioUseCase cardapioUseCase() {
        return new CardapioUseCaseImpl(cardapioMapper, cardapioGateway);
    }

    @Bean
    public CardapioController cardapioController(CardapioUseCase cardapioUseCase) {
        return new CardapioController(cardapioUseCase, cardapioMapper);
    }
}
