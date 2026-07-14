package br.com.fiap.restaurant_management.infra.database.mapper;

import br.com.fiap.restaurant_management.core.dto.CardapioDTO;
import br.com.fiap.restaurant_management.infra.database.jpa.entity.CardapioEntity;
import br.com.fiap.restaurant_management.infra.database.jpa.entity.RestauranteEntity;
import org.springframework.stereotype.Component;

@Component
public class CardapioEntityMapper {

    public CardapioEntity toEntity(CardapioDTO input) {
        return CardapioEntity.builder()
                .id(input.getId())
                .restaurante(RestauranteEntity.builder().id(input.getIdRestaurante()).build())
                .nome(input.getNome())
                .descricao(input.getDescricao())
                .preco(input.getPreco())
                .disponibilidadeRestaurante(input.getDisponibilidadeRestaurante())
                .fotoUrl(input.getFotoUrl())
                .build();
    }

    public CardapioDTO toCardapioDTO(CardapioEntity entity) {
        return new CardapioDTO(entity.getId(),
                entity.getRestaurante().getId(),
                entity.getNome(),
                entity.getDescricao(),
                entity.getPreco(),
                entity.getDisponibilidadeRestaurante(),
                entity.getFotoUrl());
    }
}
