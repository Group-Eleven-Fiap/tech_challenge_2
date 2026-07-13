package br.com.fiap.restaurant_management.infra.database.jpa;

import br.com.fiap.restaurant_management.core.domain.Restaurante;
import br.com.fiap.restaurant_management.core.gateway.RestauranteGateway;
import br.com.fiap.restaurant_management.infra.database.jpa.repository.RestauranteRepository;
import br.com.fiap.restaurant_management.infra.database.jpa.repository.UsuarioRepository;
import br.com.fiap.restaurant_management.infra.database.mapper.RestauranteEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RestauranteJpaGateway implements RestauranteGateway {

    private final RestauranteRepository restauranteRepository;
    private final UsuarioRepository usuarioRepository;
    private final RestauranteEntityMapper restauranteEntityMapper;

    @Override
    public Restaurante criar(Restaurante restaurante) {
        var dono = usuarioRepository.getReferenceById(restaurante.getIdDono());
        var salvo = restauranteRepository.save(restauranteEntityMapper.toEntity(restaurante, dono));
        return restauranteEntityMapper.toDomain(salvo);
    }

    @Override
    public Restaurante atualizar(Restaurante restaurante) {
        var existente = restauranteRepository.getReferenceById(restaurante.getId());
        existente.setNome(restaurante.getNome());
        existente.setEndereco(restaurante.getEndereco());
        existente.setTipoCozinha(restaurante.getTipoCozinha());
        existente.setHorarioFuncionamento(restaurante.getHorarioFuncionamento());
        existente.setDono(usuarioRepository.getReferenceById(restaurante.getIdDono()));
        return restauranteEntityMapper.toDomain(restauranteRepository.save(existente));
    }

    @Override
    public Optional<Restaurante> consultarPorId(Long id) {
        return restauranteRepository.findById(id).map(restauranteEntityMapper::toDomain);
    }

    @Override
    public List<Restaurante> consultarTodos() {
        return restauranteRepository.findAll().stream().map(restauranteEntityMapper::toDomain).toList();
    }

    @Override
    public void excluir(Long id) {
        restauranteRepository.deleteById(id);
    }
}
