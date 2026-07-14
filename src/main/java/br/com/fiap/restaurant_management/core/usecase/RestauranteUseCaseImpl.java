package br.com.fiap.restaurant_management.core.usecase;

import br.com.fiap.restaurant_management.core.domain.Restaurante;
import br.com.fiap.restaurant_management.core.exception.ResourceNotFoundException;
import br.com.fiap.restaurant_management.core.gateway.RestauranteGateway;
import br.com.fiap.restaurant_management.core.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RestauranteUseCaseImpl implements RestauranteUseCase {

    private final RestauranteGateway restauranteGateway;
    private final UsuarioGateway usuarioGateway;

    @Override
    public Restaurante criar(Restaurante restaurante) {
        validarDono(restaurante.getIdDono());
        return restauranteGateway.criar(restaurante);
    }

    @Override
    public Restaurante atualizar(Long id, Restaurante restaurante) {
        validarId(id);
        consultarPorId(id);
        validarDono(restaurante.getIdDono());

        var restauranteAtualizado = new Restaurante(
                id, restaurante.getNome(), restaurante.getEndereco(), restaurante.getTipoCozinha(),
                restaurante.getIdDono(), restaurante.getCriadoEm(), restaurante.getAtualizadoEm());
        return restauranteGateway.atualizar(restauranteAtualizado);
    }

    @Override
    public Restaurante consultarPorId(Long id) {
        validarId(id);
        return restauranteGateway.consultarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante", "id", id));
    }

    @Override
    public List<Restaurante> consultarTodos() {
        return restauranteGateway.consultarTodos();
    }

    @Override
    public void excluir(Long id) {
        consultarPorId(id);
        restauranteGateway.excluir(id);
    }

    private void validarDono(Long idDono) {
        usuarioGateway.consultarPorId(idDono)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário dono", "id", idDono));
    }

    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new ResourceNotFoundException("Restaurante", "id", id);
        }
    }
}
