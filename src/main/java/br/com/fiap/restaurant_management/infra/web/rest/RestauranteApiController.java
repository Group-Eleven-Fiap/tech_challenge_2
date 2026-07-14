package br.com.fiap.restaurant_management.infra.web.rest;

import br.com.fiap.restaurant_management.core.controller.RestauranteController;
import br.com.fiap.restaurant_management.core.dto.RestauranteDTO;
import br.com.fiap.restaurant_management.infra.web.config.RestauranteControllerApi;
import br.com.fiap.restaurant_management.infra.web.dto.RestauranteInput;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/restaurantes")
@RequiredArgsConstructor
public class RestauranteApiController implements RestauranteControllerApi {

    private final RestauranteController restauranteController;

    @Override
    public ResponseEntity<RestauranteDTO> criar(RestauranteInput entrada) {
        var criado = restauranteController.criar(mapearEntrada(entrada));
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @Override
    public ResponseEntity<RestauranteDTO> atualizar(Long id, RestauranteInput entrada) {
        return ResponseEntity.ok(restauranteController.atualizar(id, mapearEntrada(entrada)));
    }

    @Override
    public ResponseEntity<RestauranteDTO> consultarPorId(Long id) {
        return ResponseEntity.ok(restauranteController.consultarPorId(id));
    }

    @Override
    public ResponseEntity<List<RestauranteDTO>> consultarTodos() {
        return ResponseEntity.ok(restauranteController.consultarTodos());
    }

    @Override
    public ResponseEntity<Void> excluir(Long id) {
        restauranteController.excluir(id);
        return ResponseEntity.noContent().build();
    }

    private RestauranteDTO mapearEntrada(RestauranteInput entrada) {
        return new RestauranteDTO(
                null, entrada.getNome(), entrada.getEndereco(), entrada.getTipoCozinha(),
                entrada.getIdDono(), null, null);
    }
}
