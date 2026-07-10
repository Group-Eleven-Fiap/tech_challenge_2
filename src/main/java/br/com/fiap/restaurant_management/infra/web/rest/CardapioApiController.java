package br.com.fiap.restaurant_management.infra.web.rest;

import br.com.fiap.restaurant_management.core.controller.CardapioController;
import br.com.fiap.restaurant_management.core.dto.CardapioDTO;
import br.com.fiap.restaurant_management.infra.web.dto.CardapioInput;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/meal")
@RequiredArgsConstructor
public class CardapioApiController {

    private final CardapioController cardapioController;

    @PostMapping
    public CardapioDTO criar(@Valid @RequestBody CardapioInput input) {
        return cardapioController.criarCardapio(mapToDto(input));
    }

    private CardapioDTO mapToDto(CardapioInput input) {
        return new CardapioDTO(
                input.getNome(),
                input.getDescricao(),
                input.getPreco(),
                input.getDisponibilidadeRestaurante(),
                input.getFotoUrl());
    }
}
