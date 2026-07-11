package br.com.fiap.restaurant_management.infra.database.jpa;

import br.com.fiap.restaurant_management.core.dto.CardapioDTO;
import br.com.fiap.restaurant_management.core.gateway.CardapioGateway;
import br.com.fiap.restaurant_management.infra.database.jpa.repository.CardapioRepository;
import br.com.fiap.restaurant_management.infra.database.mapper.CardapioEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

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

    @Override
    public CardapioDTO atualizarCardapio(Long id, CardapioDTO input) {
        var cardapioEntity = cardapioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cardápio não encontrado"));
        
        cardapioEntity.setNome(input.getNome());
        cardapioEntity.setDescricao(input.getDescricao());
        cardapioEntity.setPreco(input.getPreco());
        cardapioEntity.setDisponibilidadeRestaurante(input.getDisponibilidadeRestaurante());
        cardapioEntity.setFotoUrl(input.getFotoUrl());
        
        var updatedEntity = cardapioRepository.save(cardapioEntity);
        return cardapioEntityMapper.toCardapioDTO(updatedEntity);
    }

    @Override
    public void deletarCardapio(Long id) {
        cardapioRepository.deleteById(id);
    }

    @Override
    public Optional<CardapioDTO> obterCardapioPorId(Long id) {
        return cardapioRepository.findById(id)
            .map(cardapioEntityMapper::toCardapioDTO);
    }

    @Override
    public List<CardapioDTO> listarTodos() {
        return cardapioRepository.findAll()
            .stream()
            .map(cardapioEntityMapper::toCardapioDTO)
            .toList();
    }
}
