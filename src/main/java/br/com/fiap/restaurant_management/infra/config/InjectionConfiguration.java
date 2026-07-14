package br.com.fiap.restaurant_management.infra.config;

import br.com.fiap.restaurant_management.core.controller.RestauranteExpedienteController;
import br.com.fiap.restaurant_management.core.controller.RestauranteController;
import br.com.fiap.restaurant_management.core.gateway.RestauranteExpedienteGateway;
import br.com.fiap.restaurant_management.core.gateway.RestauranteGateway;
import br.com.fiap.restaurant_management.core.mapper.RestauranteExpedienteMapper;
import br.com.fiap.restaurant_management.core.mapper.RestauranteMapper;
import br.com.fiap.restaurant_management.core.usecase.RestauranteExpedienteUseCase;
import br.com.fiap.restaurant_management.core.usecase.RestauranteExpedienteUseCaseImpl;
import br.com.fiap.restaurant_management.core.controller.CardapioController;
import br.com.fiap.restaurant_management.core.controller.TipoUsuarioController;
import br.com.fiap.restaurant_management.core.controller.UsuarioController;
import br.com.fiap.restaurant_management.core.gateway.TipoUsuarioGateway;
import br.com.fiap.restaurant_management.core.gateway.UsuarioGateway;
import br.com.fiap.restaurant_management.core.mapper.CardapioMapper;
import br.com.fiap.restaurant_management.core.gateway.CardapioGateway;
import br.com.fiap.restaurant_management.core.mapper.TipoUsuarioMapper;
import br.com.fiap.restaurant_management.core.mapper.UsuarioMapper;
import br.com.fiap.restaurant_management.core.usecase.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class InjectionConfiguration {

    private final RestauranteExpedienteMapper restauranteExpedienteMapper;
    private final RestauranteExpedienteGateway restauranteExpedienteGateway;
    private final RestauranteMapper restauranteMapper;
    private final RestauranteGateway restauranteGateway;
    private final CardapioMapper cardapioMapper;
    private final CardapioGateway cardapioGateway;

    private final TipoUsuarioMapper tipoUsuarioMapper;
    private final TipoUsuarioGateway tipoUsuarioGateway;

    private final UsuarioMapper usuarioMapper;
    private final UsuarioGateway usuarioGateway;

    @Bean
    public CardapioUseCase cardapioUseCase(RestauranteUseCase restauranteUseCase) {
        return new CardapioUseCaseImpl(restauranteUseCase, cardapioMapper, cardapioGateway);
    }

    @Bean
    public CardapioController cardapioController(CardapioUseCase cardapioUseCase) {
        return new CardapioController(cardapioUseCase, cardapioMapper);
    }

    @Bean
    public TipoUsuarioUseCase tipoUsuarioUseCase() {
        return new TipoUsuarioUseCaseImpl(tipoUsuarioMapper, tipoUsuarioGateway);
    }

    @Bean
    public TipoUsuarioController tipoUsuarioController(TipoUsuarioUseCase useCase) {
        return new TipoUsuarioController(useCase);
    }

    @Bean
    public UsuarioUseCase usuarioUseCase() {
        return new UsuarioUseCaseImpl(usuarioMapper, usuarioGateway, tipoUsuarioGateway);
    }

    @Bean
    public UsuarioController usuarioController(UsuarioUseCase useCase) {
        return new UsuarioController(useCase);
    }

    @Bean
    public RestauranteExpedienteUseCase restauranteExpedienteUseCase() {
        return new RestauranteExpedienteUseCaseImpl(
                restauranteExpedienteMapper, restauranteExpedienteGateway, restauranteGateway);
    }

    @Bean
    public RestauranteExpedienteController restauranteExpedienteController(RestauranteExpedienteUseCase restauranteExpedienteUseCase) {
        return new RestauranteExpedienteController(restauranteExpedienteUseCase, restauranteExpedienteMapper);
    }

    @Bean
    public RestauranteUseCase restauranteUseCase() {
        return new RestauranteUseCaseImpl(restauranteGateway, usuarioGateway);
    }

    @Bean
    public RestauranteController restauranteController(RestauranteUseCase restauranteUseCase) {
        return new RestauranteController(restauranteUseCase, restauranteMapper);
    }
}
