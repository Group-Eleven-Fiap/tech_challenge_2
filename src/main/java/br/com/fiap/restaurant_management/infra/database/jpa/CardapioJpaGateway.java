package br.com.fiap.restaurant_management.infra.database.jpa;

import br.com.fiap.restaurant_management.core.dto.CardapioDTO;
import br.com.fiap.restaurant_management.core.gateway.CardapioGateway;
import br.com.fiap.restaurant_management.infra.database.jpa.repository.CardapioRepository;
import br.com.fiap.restaurant_management.infra.database.mapper.CardapioEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CardapioJpaGateway implements CardapioGateway {

    private final CardapioEntityMapper cardapioEntityMapper;
    private final CardapioRepository cardapioRepository;

    @Override
    public CardapioDTO criarCardapio(CardapioDTO input) {
        var cardapioEntity = cardapioEntityMapper.toEntity(input);
        var newEntity = cardapioRepository.save(cardapioEntity);
        return cardapioEntityMapper.toCardapioDTO(newEntity);
    }
}
