package br.com.fiap.restaurant_management.infra.web.rest;

import br.com.fiap.restaurant_management.core.controller.RestauranteExpedienteController;
import br.com.fiap.restaurant_management.core.dto.RestauranteExpedienteDTO;
import br.com.fiap.restaurant_management.infra.web.dto.RestauranteExpedienteInput;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/v1/restaurante-expediente")
@RequiredArgsConstructor
public class RestauranteExpedienteApiController {

    private final RestauranteExpedienteController restauranteExpedienteController;

    @PostMapping
    public RestauranteExpedienteDTO create(@Valid @RequestBody RestauranteExpedienteInput input) {
        return restauranteExpedienteController.criarExpediente(mapToDto(null, input));
    }

    @GetMapping("/{id}")
    public RestauranteExpedienteDTO findById(@PathVariable UUID id) {
        return restauranteExpedienteController.buscarPorId(id);
    }

    @GetMapping
    public List<RestauranteExpedienteDTO> findByRestaurante(@RequestParam Long idRestaurante) {
        return restauranteExpedienteController.listarPorRestaurante(idRestaurante);
    }

    @PutMapping("/{id}")
    public RestauranteExpedienteDTO update(@PathVariable UUID id, @Valid @RequestBody RestauranteExpedienteInput input) {
        return restauranteExpedienteController.atualizarExpediente(id, mapToDto(id, input));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
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
