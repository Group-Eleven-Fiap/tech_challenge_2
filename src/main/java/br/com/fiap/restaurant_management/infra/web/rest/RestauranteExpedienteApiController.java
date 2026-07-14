package br.com.fiap.restaurant_management.infra.web.rest;

import br.com.fiap.restaurant_management.core.controller.RestauranteExpedienteController;
import br.com.fiap.restaurant_management.core.dto.RestauranteExpedienteDTO;
import br.com.fiap.restaurant_management.infra.web.config.RestauranteExpedienteControllerApi;
import br.com.fiap.restaurant_management.infra.web.dto.RestauranteExpedienteInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/v1/restaurante-expediente")
@RequiredArgsConstructor
public class RestauranteExpedienteApiController implements RestauranteExpedienteControllerApi {

    private final RestauranteExpedienteController restauranteExpedienteController;

    @Override
    public RestauranteExpedienteDTO create(RestauranteExpedienteInput input) {
        return restauranteExpedienteController.criarExpediente(mapToDto(null, input));
    }

    @Override
    public RestauranteExpedienteDTO findById(UUID id) {
        return restauranteExpedienteController.buscarPorId(id);
    }

    @Override
    public List<RestauranteExpedienteDTO> findByRestaurante(Long idRestaurante) {
        return restauranteExpedienteController.listarPorRestaurante(idRestaurante);
    }

    @Override
    public RestauranteExpedienteDTO update(UUID id, RestauranteExpedienteInput input) {
        return restauranteExpedienteController.atualizarExpediente(id, mapToDto(id, input));
    }

    @Override
    public void delete(UUID id) {
        restauranteExpedienteController.deletarExpediente(id);
    }

    private RestauranteExpedienteDTO mapToDto(UUID id, RestauranteExpedienteInput input) {
        return new RestauranteExpedienteDTO(
                id,
                input.getIdRestaurante(),
                input.getDiaSemana(),
                input.getHoraAbertura(),
                input.getHoraFechamento());
    }
}
