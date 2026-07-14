package br.com.fiap.restaurant_management.infra.web.config;

import br.com.fiap.restaurant_management.core.dto.TipoUsuarioOutputDto;
import br.com.fiap.restaurant_management.infra.web.dto.TipoUsuarioInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Tipos de usuário", description = "Operações relacionadas aos tipos de usuário")
public interface TipoUsuarioControllerApi {

    @PostMapping
    @Operation(
            summary = "Cadastrar tipo de usuário",
            description = "Cadastra um novo tipo de usuário no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tipo de usuário cadastrado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Long.class),
                            examples = @ExampleObject(value = "1")
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(
                            mediaType = "application/problem+json",
                            examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/business-rule-violation\", \"title\": \"Violação de regra de negócio\", \"status\": 400, \"detail\": \"Nome do Tipo de Usuário não pode ser nulo ou vazio\", \"code\": \"VALIDATION_ERROR\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/tipo-usuario\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(
                            mediaType = "application/problem+json",
                            examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/internal-server-error\", \"title\": \"Erro interno do servidor\", \"status\": 500, \"detail\": \"Um erro inesperado ocorreu\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/tipo-usuario\", \"errorId\": \"Exception\"}")
                    )
            )
    })
    Long criar(@Valid @RequestBody TipoUsuarioInput input);

    @PutMapping("{id}")
    @Operation(
            summary = "Atualizar tipo de usuário",
            description = "Atualiza os dados de um tipo de usuário existente."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tipo de usuário atualizado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(
                            mediaType = "application/problem+json",
                            examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/business-rule-violation\", \"title\": \"Violação de regra de negócio\", \"status\": 400, \"detail\": \"Nome do Tipo de Usuário não pode ser nulo ou vazio\", \"code\": \"VALIDATION_ERROR\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/tipo-usuario/1\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tipo de usuário não encontrado",
                    content = @Content(
                            mediaType = "application/problem+json",
                            examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/not-found\", \"title\": \"Recurso não encontrado\", \"status\": 404, \"detail\": \"Tipo de usuário com ID 999 não encontrado\", \"code\": \"TIPO_USUARIO_NOT_FOUND\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/tipo-usuario/999\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(
                            mediaType = "application/problem+json",
                            examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/internal-server-error\", \"title\": \"Erro interno do servidor\", \"status\": 500, \"detail\": \"Um erro inesperado ocorreu\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/tipo-usuario/1\", \"errorId\": \"Exception\"}")
                    )
            )
    })
    void atualizar(@PathVariable Long id, @Valid @RequestBody TipoUsuarioInput input);

    @DeleteMapping("{id}")
    @Operation(
            summary = "Excluir tipo de usuário",
            description = "Remove um tipo de usuário existente do sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tipo de usuário excluído com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tipo de usuário não encontrado",
                    content = @Content(
                            mediaType = "application/problem+json",
                            examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/not-found\", \"title\": \"Recurso não encontrado\", \"status\": 404, \"detail\": \"Tipo de usuário com ID 999 não encontrado\", \"code\": \"TIPO_USUARIO_NOT_FOUND\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/tipo-usuario/999\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(
                            mediaType = "application/problem+json",
                            examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/internal-server-error\", \"title\": \"Erro interno do servidor\", \"status\": 500, \"detail\": \"Um erro inesperado ocorreu\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/tipo-usuario/1\", \"errorId\": \"Exception\"}")
                    )
            )
    })
    void excluir(@PathVariable Long id);

    @GetMapping
    @Operation(
            summary = "Listar tipos de usuário",
            description = "Retorna uma lista com todos os tipos de usuário cadastrados."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de tipos de usuário retornada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TipoUsuarioOutputDto.class),
                            examples = @ExampleObject(value = "[{\"id\": 1, \"nome\": \"DONO\"}, {\"id\": 2, \"nome\": \"CLIENTE\"}]")
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(
                            mediaType = "application/problem+json",
                            examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/internal-server-error\", \"title\": \"Erro interno do servidor\", \"status\": 500, \"detail\": \"Um erro inesperado ocorreu\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/tipo-usuario\", \"errorId\": \"Exception\"}")
                    )
            )
    })
    List<TipoUsuarioOutputDto> consultar();
}
