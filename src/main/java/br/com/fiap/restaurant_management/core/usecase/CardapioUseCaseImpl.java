package br.com.fiap.restaurant_management.core.usecase;

import br.com.fiap.restaurant_management.core.domain.Cardapio;
import br.com.fiap.restaurant_management.core.dto.CardapioDTO;
import br.com.fiap.restaurant_management.core.exception.BusinessRuleException;
import br.com.fiap.restaurant_management.core.gateway.CardapioGateway;
import br.com.fiap.restaurant_management.core.mapper.CardapioMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CardapioUseCaseImpl implements CardapioUseCase {

    private final CardapioMapper cardapioMapper;
    private final CardapioGateway cardapioGateway;

    @Override
    public Cardapio criar(Cardapio cardapio) {

        validarPreco(cardapio);
        var cardapioDTO = cardapioMapper.toCardapioDTO(cardapio);
        CardapioDTO newCardapio = cardapioGateway.criarCardapio(cardapioDTO);
        return cardapioMapper.toCardapio(newCardapio);
    }

    private void validarPreco(Cardapio cardapio) {
        if (!cardapio.validaPreco()) {
            throw new BusinessRuleException("price cannot be null or 0");
        }
    }
}
