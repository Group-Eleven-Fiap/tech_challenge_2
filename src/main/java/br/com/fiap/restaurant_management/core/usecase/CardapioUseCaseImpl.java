package br.com.fiap.restaurant_management.core.usecase;

import br.com.fiap.restaurant_management.core.domain.Cardapio;
import br.com.fiap.restaurant_management.core.dto.CardapioDTO;
import br.com.fiap.restaurant_management.core.exception.BusinessRuleException;
import br.com.fiap.restaurant_management.core.gateway.CardapioGateway;
import br.com.fiap.restaurant_management.core.mapper.CardapioMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class CardapioUseCaseImpl implements CardapioUseCase {

    private final RestauranteUseCase restauranteUseCase;
    private final CardapioMapper cardapioMapper;
    private final CardapioGateway cardapioGateway;

    @Override
    public Cardapio criar(Cardapio cardapio) {
        consultarPorId(cardapio.getIdRestaurante());
        var cardapioDTO = cardapioMapper.toCardapioDTO(cardapio);
        CardapioDTO newCardapio = cardapioGateway.criarCardapio(cardapioDTO);
        return cardapioMapper.toCardapio(newCardapio);
    }

    @Override
    public Cardapio atualizar(Long id, Cardapio cardapio) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do cardápio inválido");
        }
        
        cardapioGateway.obterCardapioPorId(id)
            .orElseThrow(() -> new BusinessRuleException("Cardápio com ID " + id + " não encontrado"));
        
        var cardapioDTO = cardapioMapper.toCardapioDTO(cardapio);
        CardapioDTO updatedCardapio = cardapioGateway.atualizarCardapio(id, cardapioDTO);
        return cardapioMapper.toCardapio(updatedCardapio);
    }

    @Override
    public void deletar(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do cardápio inválido");
        }
        
        cardapioGateway.obterCardapioPorId(id)
            .orElseThrow(() -> new BusinessRuleException("Cardápio com ID " + id + " não encontrado"));
        
        cardapioGateway.deletarCardapio(id);
    }

    @Override
    public Cardapio obterPorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do cardápio inválido");
        }
        
        CardapioDTO cardapioDTO = cardapioGateway.obterCardapioPorId(id)
            .orElseThrow(() -> new BusinessRuleException("Cardápio com ID " + id + " não encontrado"));
        
        return cardapioMapper.toCardapio(cardapioDTO);
    }

    @Override
    public List<Cardapio> listar() {
        return cardapioGateway.listarTodos()
            .stream()
            .map(cardapioMapper::toCardapio)
            .toList();
    }

    private void consultarPorId(Long id) {
        restauranteUseCase.consultarPorId(id);
    }
}
