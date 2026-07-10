package br.com.fiap.restaurant_management.infra.config;

import br.com.fiap.restaurant_management.core.controller.CardapioController;
import br.com.fiap.restaurant_management.core.controller.TipoUsuarioController;
import br.com.fiap.restaurant_management.core.gateway.TipoUsuarioGateway;
import br.com.fiap.restaurant_management.core.mapper.CardapioMapper;
import br.com.fiap.restaurant_management.core.gateway.CardapioGateway;
import br.com.fiap.restaurant_management.core.mapper.TipoUsuarioMapper;
import br.com.fiap.restaurant_management.core.usecase.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class InjectionConfiguration {

    private final CardapioMapper cardapioMapper;
    private final CardapioGateway cardapioGateway;

    private final TipoUsuarioMapper tipoUsuarioMapper;
    private final TipoUsuarioGateway tipoUsuarioGateway;

    @Bean
    public CardapioUseCase cardapioUseCase() {
        return new CardapioUseCaseImpl(cardapioMapper, cardapioGateway);
    }

    @Bean
    public CardapioController cardapioController(CardapioUseCase cardapioUseCase) {
        return new CardapioController(cardapioUseCase, cardapioMapper);
    }

    @Bean
    public CriarTipoUsuarioUseCase criarTipoUsuarioUseCase() {
        return new CriarTipoUsuarioUseCaseImpl(tipoUsuarioMapper, tipoUsuarioGateway);
    }

    @Bean
    public AtualizarTipoUsuarioUseCase atualizarTipoUsuarioUseCase() {
        return new AtualizarTipoUsuarioUseCaseImpl(tipoUsuarioGateway);
    }

    @Bean
    public ExcluirTipoUsuarioUseCase excluirTipoUsuarioUseCase() {
        return new ExcluirTipoUsuarioUseCaseImpl(tipoUsuarioGateway);
    }

    @Bean
    public ConsultarTipoUsuarioUseCase consultarTipoUsuarioUseCase() {
        return new ConsultarTipoUsuarioUseCaseImpl(tipoUsuarioMapper, tipoUsuarioGateway);
    }

    @Bean
    public TipoUsuarioController tipoUsuarioController(CriarTipoUsuarioUseCase criarTipoUsuarioUseCase,
                                                       AtualizarTipoUsuarioUseCase atualizarTipoUsuarioUseCase,
                                                       ExcluirTipoUsuarioUseCase excluirTipoUsuarioUseCase,
                                                       ConsultarTipoUsuarioUseCase consultarTipoUsuarioUseCase) {
        return new TipoUsuarioController(
                criarTipoUsuarioUseCase,
                atualizarTipoUsuarioUseCase,
                excluirTipoUsuarioUseCase,
                consultarTipoUsuarioUseCase);
    }
}
