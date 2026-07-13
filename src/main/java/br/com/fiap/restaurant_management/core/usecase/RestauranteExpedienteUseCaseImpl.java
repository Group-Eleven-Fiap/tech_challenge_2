package br.com.fiap.restaurant_management.core.usecase;

import br.com.fiap.restaurant_management.core.domain.RestauranteExpediente;
import br.com.fiap.restaurant_management.core.exception.BusinessRuleException;
import br.com.fiap.restaurant_management.core.exception.ResourceNotFoundException;
import br.com.fiap.restaurant_management.core.gateway.RestauranteExpedienteGateway;
import br.com.fiap.restaurant_management.core.gateway.RestauranteGateway;
import br.com.fiap.restaurant_management.core.mapper.RestauranteExpedienteMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class RestauranteExpedienteUseCaseImpl implements RestauranteExpedienteUseCase {

    private final RestauranteExpedienteMapper restauranteExpedienteMapper;
    private final RestauranteExpedienteGateway restauranteExpedienteGateway;
    private final RestauranteGateway restauranteGateway;

    @Override
    public RestauranteExpediente create(RestauranteExpediente expediente) {

        validateExpediente(expediente);
        validateRestauranteExistente(expediente.getIdRestaurante());
        validateDiaDisponivel(expediente.getIdRestaurante(), expediente.getDiaSemana());

        var expedienteDTO = restauranteExpedienteMapper.toDTO(expediente);
        var novoExpediente = restauranteExpedienteGateway.createExpediente(expedienteDTO);
        return restauranteExpedienteMapper.toDomain(novoExpediente);
    }

    @Override
    public RestauranteExpediente findById(UUID id) {
        return restauranteExpedienteGateway.findById(id)
                .map(restauranteExpedienteMapper::toDomain)
                .orElseThrow(() -> new BusinessRuleException("expediente não encontrado"));
    }

    @Override
    public List<RestauranteExpediente> findByRestaurante(Long idRestaurante) {
        return restauranteExpedienteGateway.findByRestaurante(idRestaurante).stream()
                .map(restauranteExpedienteMapper::toDomain)
                .toList();
    }

    @Override
    public RestauranteExpediente update(UUID id, RestauranteExpediente expediente) {

        validateExpediente(expediente);
        validateRestauranteExistente(expediente.getIdRestaurante());

        var existente = findById(id);
        boolean trocouDia = !existente.getDiaSemana().equalsIgnoreCase(expediente.getDiaSemana());
        if (trocouDia) {
            validateDiaDisponivel(expediente.getIdRestaurante(), expediente.getDiaSemana());
        }

        var expedienteAtualizado = new RestauranteExpediente(
                id,
                expediente.getIdRestaurante(),
                expediente.getDiaSemana(),
                expediente.getHoraAbertura(),
                expediente.getHoraFechamento());

        var expedienteDTO = restauranteExpedienteMapper.toDTO(expedienteAtualizado);
        var atualizado = restauranteExpedienteGateway.updateExpediente(expedienteDTO);
        return restauranteExpedienteMapper.toDomain(atualizado);
    }

    @Override
    public void delete(UUID id) {
        findById(id);
        restauranteExpedienteGateway.deleteById(id);
    }

    private void validateExpediente(RestauranteExpediente expediente) {
        if (!expediente.validateIdRestaurante()) {
            throw new BusinessRuleException("restaurante é obrigatório");
        }
        if (!expediente.validateDiaSemana()) {
            throw new BusinessRuleException("dia da semana inválido");
        }
        if (!expediente.validateHorarios()) {
            throw new BusinessRuleException("horário de fechamento deve ser posterior ao horário de abertura");
        }
    }

    private void validateDiaDisponivel(Long idRestaurante, String diaSemana) {
        if (restauranteExpedienteGateway.existsByRestauranteAndDia(idRestaurante, diaSemana)) {
            throw new BusinessRuleException("já existe um expediente cadastrado para este restaurante neste dia da semana");
        }
    }

    private void validateRestauranteExistente(Long idRestaurante) {
        restauranteGateway.consultarPorId(idRestaurante)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante", "id", idRestaurante));
    }
}
