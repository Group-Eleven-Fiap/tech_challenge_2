package br.com.fiap.restaurant_management.core.usecase;

import br.com.fiap.restaurant_management.core.domain.Cardapio;
import java.util.List;

public interface CardapioUseCase {

    Cardapio criar(Cardapio cardapio);

    Cardapio atualizar(Long id, Cardapio cardapio);

    void deletar(Long id);

    Cardapio obterPorId(Long id);

    List<Cardapio> listar();
}
