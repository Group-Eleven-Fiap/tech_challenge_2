package br.com.fiap.restaurant_management.core.controller;

import br.com.fiap.restaurant_management.core.domain.Cardapio;
import br.com.fiap.restaurant_management.core.dto.CardapioDTO;
import br.com.fiap.restaurant_management.core.mapper.CardapioMapper;
import br.com.fiap.restaurant_management.core.usecase.CardapioUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

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

    public CardapioDTO atualizarCardapio(Long id, CardapioDTO input) {
        Cardapio cardapio = cardapioMapper.toCardapio(input);
        Cardapio updatedCardapio = cardapioUseCase.atualizar(id, cardapio);
        return cardapioMapper.toCardapioDTO(updatedCardapio);
    }

    public void deletarCardapio(Long id) {
        cardapioUseCase.deletar(id);
    }

    public CardapioDTO obterCardapioPorId(Long id) {
        Cardapio cardapio = cardapioUseCase.obterPorId(id);
        return cardapioMapper.toCardapioDTO(cardapio);
    }

    public List<CardapioDTO> listarCardapios() {
        return cardapioUseCase.listar()
            .stream()
            .map(cardapioMapper::toCardapioDTO)
            .toList();
    }
}
