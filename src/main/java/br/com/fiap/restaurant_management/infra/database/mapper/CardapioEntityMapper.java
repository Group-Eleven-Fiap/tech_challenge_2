package br.com.fiap.restaurant_management.infra.database.mapper;

import br.com.fiap.restaurant_management.core.dto.CardapioDTO;
import br.com.fiap.restaurant_management.infra.database.jpa.entity.CardapioEntity;
import org.springframework.stereotype.Component;

@Component
public class CardapioEntityMapper {

    public CardapioEntity toEntity(CardapioDTO input) {
        return CardapioEntity.builder()
                .idRestaurante(input.getIdRestaurante())
                .nome(input.getNome())
                .descricao(input.getDescricao())
                .preco(input.getPreco())
                .disponibilidadeRestaurante(input.getDisponibilidadeRestaurante())
                .fotoUrl(input.getFotoUrl())
                .build();
    }

    public CardapioDTO toCardapioDTO(CardapioEntity entity) {
        return new CardapioDTO(entity.getId(),
                entity.getIdRestaurante(),
                entity.getNome(),
                entity.getDescricao(),
                entity.getPreco(),
                entity.getDisponibilidadeRestaurante(),
                entity.getFotoUrl());
    }
}
