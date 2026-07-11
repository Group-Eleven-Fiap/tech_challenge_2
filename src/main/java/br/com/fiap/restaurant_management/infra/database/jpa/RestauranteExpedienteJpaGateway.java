package br.com.fiap.restaurant_management.infra.database.jpa;

import br.com.fiap.restaurant_management.core.dto.RestauranteExpedienteDTO;
import br.com.fiap.restaurant_management.core.exception.BusinessRuleException;
import br.com.fiap.restaurant_management.core.gateway.RestauranteExpedienteGateway;
import br.com.fiap.restaurant_management.infra.database.jpa.repository.RestauranteExpedienteRepository;
import br.com.fiap.restaurant_management.infra.database.mapper.RestauranteExpedienteEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestauranteExpedienteJpaGateway implements RestauranteExpedienteGateway {

    private final RestauranteExpedienteEntityMapper restauranteExpedienteEntityMapper;
    private final RestauranteExpedienteRepository restauranteExpedienteRepository;

    @Override
    public RestauranteExpedienteDTO createExpediente(RestauranteExpedienteDTO input) {
        var entity = restauranteExpedienteEntityMapper.toEntity(input);
        var novaEntity = restauranteExpedienteRepository.save(entity);
        return restauranteExpedienteEntityMapper.toDTO(novaEntity);
    }

    @Override
    public Optional<RestauranteExpedienteDTO> findById(UUID id) {
        return restauranteExpedienteRepository.findById(id)
                .map(restauranteExpedienteEntityMapper::toDTO);
    }

    @Override
    public List<RestauranteExpedienteDTO> findByRestaurante(UUID idRestaurante) {
        return restauranteExpedienteRepository.findByIdRestaurante(idRestaurante).stream()
                .map(restauranteExpedienteEntityMapper::toDTO)
                .toList();
    }

    @Override
    public boolean existsByRestauranteAndDia(UUID idRestaurante, String diaSemana) {
        return restauranteExpedienteRepository.existsByIdRestauranteAndDiaSemanaIgnoreCase(idRestaurante, diaSemana);
    }

    @Override
    public RestauranteExpedienteDTO updateExpediente(RestauranteExpedienteDTO input) {
        if (!restauranteExpedienteRepository.existsById(input.getId())) {
            throw new BusinessRuleException("expediente não encontrado");
        }
        var entity = restauranteExpedienteEntityMapper.toEntity(input);
        var atualizada = restauranteExpedienteRepository.save(entity);
        return restauranteExpedienteEntityMapper.toDTO(atualizada);
    }

    @Override
    public void deleteById(UUID id) {
        restauranteExpedienteRepository.deleteById(id);
    }
}
