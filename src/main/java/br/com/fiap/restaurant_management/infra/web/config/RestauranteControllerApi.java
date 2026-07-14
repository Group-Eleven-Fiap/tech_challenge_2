package br.com.fiap.restaurant_management.infra.web.config;

import br.com.fiap.restaurant_management.core.dto.RestauranteDTO;
import br.com.fiap.restaurant_management.infra.web.dto.RestauranteInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Restaurantes", description = "Cadastro e manutenção de restaurantes")
public interface RestauranteControllerApi {

    @PostMapping
    @Operation(summary = "Cadastrar restaurante",
            description = "Cadastra um restaurante associado a um usuário dono existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Restaurante cadastrado",
                    content = @Content(schema = @Schema(implementation = RestauranteDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(mediaType = "application/problem+json")),
            @ApiResponse(responseCode = "404", description = "Usuário dono não encontrado",
                    content = @Content(mediaType = "application/problem+json"))
    })
    ResponseEntity<RestauranteDTO> criar(@Valid @RequestBody RestauranteInput entrada);

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar restaurante",
            description = "Atualiza nome, endereço, tipo de cozinha e dono do restaurante.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurante atualizado",
                    content = @Content(schema = @Schema(implementation = RestauranteDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(mediaType = "application/problem+json")),
            @ApiResponse(responseCode = "404", description = "Restaurante ou dono não encontrado",
                    content = @Content(mediaType = "application/problem+json"))
    })
    ResponseEntity<RestauranteDTO> atualizar(
            @PathVariable Long id, @Valid @RequestBody RestauranteInput entrada);

    @GetMapping("/{id}")
    @Operation(summary = "Consultar restaurante por identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurante encontrado",
                    content = @Content(schema = @Schema(implementation = RestauranteDTO.class),
                            examples = @ExampleObject(value = """
                                    {"id":1,"nome":"Sabor Brasil","endereco":"Rua A, 10","tipoCozinha":"Brasileira","idDono":1}
                                    """))),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
                    content = @Content(mediaType = "application/problem+json"))
    })
    ResponseEntity<RestauranteDTO> consultarPorId(@PathVariable Long id);

    @GetMapping
    @Operation(summary = "Listar restaurantes")
    @ApiResponse(responseCode = "200", description = "Restaurantes listados",
            content = @Content(schema = @Schema(implementation = RestauranteDTO.class)))
    ResponseEntity<List<RestauranteDTO>> consultarTodos();

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir restaurante",
            description = "Exclui o restaurante e seus expedientes vinculados.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurante excluído"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
                    content = @Content(mediaType = "application/problem+json"))
    })
    ResponseEntity<Void> excluir(@PathVariable Long id);
}
