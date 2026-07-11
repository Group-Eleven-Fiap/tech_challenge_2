package br.com.fiap.restaurant_management.core.mapper;

import br.com.fiap.restaurant_management.core.domain.Cardapio;
import br.com.fiap.restaurant_management.core.dto.CardapioDTO;
import org.springframework.stereotype.Component;

@Component
public class CardapioMapper {

    public Cardapio toCardapio(CardapioDTO input) {
        return new Cardapio(input.getIdRestaurante(),
                input.getNome(),
                input.getDescricao(),
                input.getPreco(),
                input.getDisponibilidadeRestaurante(),
                input.getFotoUrl());
    }

    public CardapioDTO toCardapioDTO(Cardapio cardapio) {
        return new CardapioDTO(null,
                cardapio.getIdRestaurante(),
                cardapio.getNome(),
                cardapio.getDescricao(),
                cardapio.getPreco(),
                cardapio.getDisponibilidadeRestaurante(),
                cardapio.getFotoUrl());
    }
}
