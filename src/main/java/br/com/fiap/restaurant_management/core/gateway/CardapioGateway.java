package br.com.fiap.restaurant_management.core.gateway;

import br.com.fiap.restaurant_management.core.dto.CardapioDTO;
import java.util.List;
import java.util.Optional;

public interface CardapioGateway {

    CardapioDTO criarCardapio(CardapioDTO cardapioDTO);

    CardapioDTO atualizarCardapio(Long id, CardapioDTO cardapioDTO);

    void deletarCardapio(Long id);

    Optional<CardapioDTO> obterCardapioPorId(Long id);

    List<CardapioDTO> listarTodos();
}
