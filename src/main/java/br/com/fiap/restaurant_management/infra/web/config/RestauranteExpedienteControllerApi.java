package br.com.fiap.restaurant_management.infra.web.config;

import br.com.fiap.restaurant_management.core.dto.RestauranteExpedienteDTO;
import br.com.fiap.restaurant_management.infra.web.dto.RestauranteExpedienteInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Expediente do Restaurante", description = "Operações relacionadas ao expediente do restaurante")
public interface RestauranteExpedienteControllerApi {

    @PostMapping
    @Operation(summary = "Criar expediente", description = "Cadastra um novo expediente para um restaurante.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Expediente criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestauranteExpedienteDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(mediaType = "application/problem+json")),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/problem+json"))
    })
    RestauranteExpedienteDTO create(@Valid @RequestBody RestauranteExpedienteInput input);

    @GetMapping("/{id}")
    @Operation(summary = "Obter expediente por ID", description = "Retorna os detalhes de um expediente específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expediente retornado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestauranteExpedienteDTO.class))),
            @ApiResponse(responseCode = "404", description = "Expediente não encontrado", content = @Content(mediaType = "application/problem+json")),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/problem+json"))
    })
    RestauranteExpedienteDTO findById(@PathVariable Long id);

    @GetMapping
    @Operation(summary = "Listar expedientes por restaurante", description = "Retorna uma lista com todos os expedientes de um restaurante.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de expedientes retornada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestauranteExpedienteDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/problem+json"))
    })
    List<RestauranteExpedienteDTO> findByRestaurante(@RequestParam Long idRestaurante);

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar expediente", description = "Atualiza os dados de um expediente existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expediente atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestauranteExpedienteDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(mediaType = "application/problem+json")),
            @ApiResponse(responseCode = "404", description = "Expediente não encontrado", content = @Content(mediaType = "application/problem+json")),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/problem+json"))
    })
    RestauranteExpedienteDTO update(@PathVariable Long id, @Valid @RequestBody RestauranteExpedienteInput input);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar expediente", description = "Remove um expediente existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2.4", description = "Expediente deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Expediente não encontrado", content = @Content(mediaType = "application/problem+json")),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/problem+json"))
    })
    void delete(@PathVariable Long id);
}
