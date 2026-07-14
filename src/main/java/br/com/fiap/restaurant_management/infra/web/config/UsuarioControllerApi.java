package br.com.fiap.restaurant_management.infra.web.config;

import br.com.fiap.restaurant_management.core.dto.UsuarioOutputDto;
import br.com.fiap.restaurant_management.infra.web.dto.UsuarioInput;
import br.com.fiap.restaurant_management.infra.web.dto.UsuarioTipoUsuarioInput;
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

@Tag(name = "Usuários", description = "Operações relacionadas aos usuários")
public interface UsuarioControllerApi {

    @PostMapping
    @Operation(
            summary = "Cadastrar usuário",
            description = "Cadastra um novo usuário no sistema vinculado a um tipo de usuário."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário cadastrado com sucesso",
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
                            examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/business-rule-violation\", \"title\": \"Violação de regra de negócio\", \"status\": 400, \"detail\": \"Nome do Tipo de Usuário não pode ser nulo ou vazio\", \"code\": \"VALIDATION_ERROR\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/usuario\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tipo de usuário não encontrado",
                    content = @Content(
                            mediaType = "application/problem+json",
                            examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/not-found\", \"title\": \"Recurso não encontrado\", \"status\": 404, \"detail\": \"Tipo de usuário com ID 999 não encontrado\", \"code\": \"TIPO_USUARIO_NOT_FOUND\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/usuario\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(
                            mediaType = "application/problem+json",
                            examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/internal-server-error\", \"title\": \"Erro interno do servidor\", \"status\": 500, \"detail\": \"Um erro inesperado ocorreu\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/usuario\", \"errorId\": \"Exception\"}")
                    )
            )
    })
    Long criar(@Valid @RequestBody UsuarioInput input);

    @PutMapping("{id}")
    @Operation(
            summary = "Atualizar usuário",
            description = "Atualiza os dados de um usuário existente."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário atualizado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(
                            mediaType = "application/problem+json",
                            examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/business-rule-violation\", \"title\": \"Violação de regra de negócio\", \"status\": 400, \"detail\": \"Email do Usuário não pode ser nulo ou vazio\", \"code\": \"VALIDATION_ERROR\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/usuario/1\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário ou tipo de usuário não encontrado",
                    content = @Content(
                            mediaType = "application/problem+json",
                            examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/not-found\", \"title\": \"Recurso não encontrado\", \"status\": 404, \"detail\": \"Usuário com ID 999 não encontrado\", \"code\": \"USUARIO_NOT_FOUND\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/usuario/999\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(
                            mediaType = "application/problem+json",
                            examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/internal-server-error\", \"title\": \"Erro interno do servidor\", \"status\": 500, \"detail\": \"Um erro inesperado ocorreu\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/usuario/1\", \"errorId\": \"Exception\"}")
                    )
            )
    })
    void atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioInput input);

    @PatchMapping("{usuarioId}")
    @Operation(
            summary = "Atualizar tipo de usuário do usuário",
            description = "Atualiza apenas o tipo de usuário vinculado ao usuário informado."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tipo de usuário do usuário atualizado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(
                            mediaType = "application/problem+json",
                            examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/business-rule-violation\", \"title\": \"Violação de regra de negócio\", \"status\": 400, \"detail\": \"tipoUsuarioId não pode ser nulo\", \"code\": \"VALIDATION_ERROR\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/usuario/1\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário ou tipo de usuário não encontrado",
                    content = @Content(
                            mediaType = "application/problem+json",
                            examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/not-found\", \"title\": \"Recurso não encontrado\", \"status\": 404, \"detail\": \"Usuário com ID 999 não encontrado\", \"code\": \"USUARIO_NOT_FOUND\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/usuario/999\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(
                            mediaType = "application/problem+json",
                            examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/internal-server-error\", \"title\": \"Erro interno do servidor\", \"status\": 500, \"detail\": \"Um erro inesperado ocorreu\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/usuario/1\", \"errorId\": \"Exception\"}")
                    )
            )
    })
    void atualizarTipoUsuario(@Valid @RequestBody UsuarioTipoUsuarioInput input);

    @DeleteMapping("{id}")
    @Operation(
            summary = "Excluir usuário",
            description = "Remove um usuário existente do sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário excluído com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(
                            mediaType = "application/problem+json",
                            examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/not-found\", \"title\": \"Recurso não encontrado\", \"status\": 404, \"detail\": \"Usuário com ID 999 não encontrado\", \"code\": \"USUARIO_NOT_FOUND\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/usuario/999\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(
                            mediaType = "application/problem+json",
                            examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/internal-server-error\", \"title\": \"Erro interno do servidor\", \"status\": 500, \"detail\": \"Um erro inesperado ocorreu\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/usuario/1\", \"errorId\": \"Exception\"}")
                    )
            )
    })
    void excluir(@PathVariable Long id);

    @GetMapping
    @Operation(
            summary = "Listar usuários",
            description = "Retorna uma lista com todos os usuários cadastrados."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de usuários retornada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioOutputDto.class),
                            examples = @ExampleObject(value = "[{\"id\": 1, \"nome\": \"JOÃO SILVA\", \"email\": \"JOAO@EMAIL.COM\", \"login\": \"JOAO.SILVA\", \"tipoUsuario\": {\"id\": 1, \"nome\": \"DONO\"}, \"criadoEm\": \"2026-07-14T10:00:00\", \"atualizadoEm\": null}, {\"id\": 2, \"nome\": \"MARIA SOUZA\", \"email\": \"MARIA@EMAIL.COM\", \"login\": \"MARIA.SOUZA\", \"tipoUsuario\": {\"id\": 2, \"nome\": \"CLIENTE\"}, \"criadoEm\": \"2026-07-14T10:10:00\", \"atualizadoEm\": null}]")
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(
                            mediaType = "application/problem+json",
                            examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/internal-server-error\", \"title\": \"Erro interno do servidor\", \"status\": 500, \"detail\": \"Um erro inesperado ocorreu\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/usuario\", \"errorId\": \"Exception\"}")
                    )
            )
    })
    List<UsuarioOutputDto> consultar();
}
