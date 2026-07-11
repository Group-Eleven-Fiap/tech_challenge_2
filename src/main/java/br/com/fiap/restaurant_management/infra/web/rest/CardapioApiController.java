package br.com.fiap.restaurant_management.infra.web.rest;

import br.com.fiap.restaurant_management.core.controller.CardapioController;
import br.com.fiap.restaurant_management.core.dto.CardapioDTO;
import br.com.fiap.restaurant_management.infra.web.config.CardapioControllerApi;
import br.com.fiap.restaurant_management.infra.web.dto.CardapioInput;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v2/cardapio")
@RequiredArgsConstructor
public class CardapioApiController implements CardapioControllerApi {

    private final CardapioController cardapioController;

    @Override
    public ResponseEntity<CardapioDTO> criar(@Valid @RequestBody CardapioInput input) {
        log.info("Criando novo item no cardápio");
        CardapioDTO cardapio = cardapioController.criarCardapio(mapToDto(input));
        return ResponseEntity.status(HttpStatus.CREATED).body(cardapio);
    }

    @Override
    public ResponseEntity<CardapioDTO> atualizar(@PathVariable Long id, @Valid @RequestBody CardapioInput input) {
        log.info("Atualizando cardápio com id: {}", id);
        CardapioDTO cardapio = cardapioController.atualizarCardapio(id, mapToDto(input));
        return ResponseEntity.ok(cardapio);
    }

    @Override
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        log.info("Deletando cardápio com id: {}", id);
        cardapioController.deletarCardapio(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<CardapioDTO> obterPorId(@PathVariable Long id) {
        log.info("Obtendo cardápio com id: {}", id);
        CardapioDTO cardapio = cardapioController.obterCardapioPorId(id);
        return ResponseEntity.ok(cardapio);
    }

    @Override
    public ResponseEntity<List<CardapioDTO>> listar() {
        log.info("Listando todos os cardápios");
        List<CardapioDTO> cardapios = cardapioController.listarCardapios();
        return ResponseEntity.ok(cardapios);
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
