package br.com.fiap.restaurant_management.infra.web.rest;

import br.com.fiap.restaurant_management.core.controller.RestauranteController;
import br.com.fiap.restaurant_management.core.dto.RestauranteDTO;
import br.com.fiap.restaurant_management.infra.web.dto.RestauranteInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/restaurantes")
@RequiredArgsConstructor
@Tag(name = "Restaurantes", description = "Cadastro e manutenção de restaurantes")
public class RestauranteApiController {

    private final RestauranteController restauranteController;

    @PostMapping
    @Operation(summary = "Cadastrar restaurante")
    public ResponseEntity<RestauranteDTO> criar(@Valid @RequestBody RestauranteInput entrada) {
        var criado = restauranteController.criar(mapearEntrada(entrada));
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar restaurante")
    public ResponseEntity<RestauranteDTO> atualizar(
            @PathVariable Long id, @Valid @RequestBody RestauranteInput entrada) {
        return ResponseEntity.ok(restauranteController.atualizar(id, mapearEntrada(entrada)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar restaurante por identificador")
    public ResponseEntity<RestauranteDTO> consultarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(restauranteController.consultarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar restaurantes")
    public ResponseEntity<List<RestauranteDTO>> consultarTodos() {
        return ResponseEntity.ok(restauranteController.consultarTodos());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir restaurante")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        restauranteController.excluir(id);
        return ResponseEntity.noContent().build();
    }

    private RestauranteDTO mapearEntrada(RestauranteInput entrada) {
        return new RestauranteDTO(
                null, entrada.getNome(), entrada.getEndereco(), entrada.getTipoCozinha(),
                entrada.getIdDono(), null, null);
    }
}
