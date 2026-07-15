package br.com.fiap.restaurant_management.infra.database.jpa;

import br.com.fiap.restaurant_management.core.dto.RestauranteExpedienteDTO;
import br.com.fiap.restaurant_management.core.exception.BusinessRuleException;
import br.com.fiap.restaurant_management.core.gateway.RestauranteExpedienteGateway;
import br.com.fiap.restaurant_management.infra.database.jpa.repository.RestauranteExpedienteRepository;
import br.com.fiap.restaurant_management.infra.database.jpa.repository.RestauranteRepository;
import br.com.fiap.restaurant_management.infra.database.mapper.RestauranteExpedienteEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class RestauranteExpedienteJpaGateway implements RestauranteExpedienteGateway {

    private final RestauranteExpedienteEntityMapper restauranteExpedienteEntityMapper;
    private final RestauranteExpedienteRepository restauranteExpedienteRepository;
    private final RestauranteRepository restauranteRepository;

    @Override
    public RestauranteExpedienteDTO createExpediente(RestauranteExpedienteDTO input) {
        var restaurante = restauranteRepository.getReferenceById(input.getIdRestaurante());
        var entity = restauranteExpedienteEntityMapper.toEntity(input, restaurante);
        restaurante.adicionarExpediente(entity);
        var novaEntity = restauranteExpedienteRepository.save(entity);
        return restauranteExpedienteEntityMapper.toDTO(novaEntity);
    }

    @Override
    public Optional<RestauranteExpedienteDTO> findById(Long id) {
        return restauranteExpedienteRepository.findById(id)
                .map(restauranteExpedienteEntityMapper::toDTO);
    }

    @Override
    public List<RestauranteExpedienteDTO> findByRestaurante(Long idRestaurante) {
        return restauranteExpedienteRepository.findByRestauranteId(idRestaurante).stream()
                .map(restauranteExpedienteEntityMapper::toDTO)
                .toList();
    }

    @Override
    public boolean existsByRestauranteAndDia(Long idRestaurante, String diaSemana) {
        return restauranteExpedienteRepository.existsByRestauranteIdAndDiaSemanaIgnoreCase(idRestaurante, diaSemana);
    }

    @Override
    public RestauranteExpedienteDTO updateExpediente(RestauranteExpedienteDTO input) {
        if (!restauranteExpedienteRepository.existsById(input.getId())) {
            throw new BusinessRuleException("expediente não encontrado");
        }
        var restaurante = restauranteRepository.getReferenceById(input.getIdRestaurante());
        var entity = restauranteExpedienteEntityMapper.toEntity(input, restaurante);
        var atualizada = restauranteExpedienteRepository.save(entity);
        return restauranteExpedienteEntityMapper.toDTO(atualizada);
    }

    @Override
    public void deleteById(Long id) {
        restauranteExpedienteRepository.deleteById(id);
    }
}
