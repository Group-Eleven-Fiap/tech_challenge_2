package br.com.fiap.restaurant_management.core.controller;

import br.com.fiap.restaurant_management.core.domain.Cardapio;
import br.com.fiap.restaurant_management.core.dto.CardapioDTO;
import br.com.fiap.restaurant_management.core.mapper.CardapioMapper;
import br.com.fiap.restaurant_management.core.usecase.CardapioUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CardapioController {

    private final CardapioUseCase cardapioUseCase;
    private final CardapioMapper cardapioMapper;

    public CardapioDTO criarCardapio(CardapioDTO input) {

        Cardapio cardapio = cardapioMapper.toCardapio(input);
        Cardapio newCardapio = cardapioUseCase.criar(cardapio);
        return cardapioMapper.toCardapioDTO(newCardapio);
    }
}
