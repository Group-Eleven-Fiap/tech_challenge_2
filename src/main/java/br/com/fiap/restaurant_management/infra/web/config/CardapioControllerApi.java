package br.com.fiap.restaurant_management.infra.web.config;

import br.com.fiap.restaurant_management.core.dto.CardapioDTO;
import br.com.fiap.restaurant_management.infra.web.dto.CardapioInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Cardápio", description = "Operações relacionadas ao cardápio do restaurante")
public interface CardapioControllerApi {

    @PostMapping
    @Operation(summary = "Criar item no cardápio", description = "Cadastra um novo item no cardápio do restaurante.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CardapioDTO.class), examples = @ExampleObject(value = "{\"nome\": \"Pasta à Carbonara\", \"descricao\": \"Massa fresca com molho cremoso de ovos, queijo parmesão e pancetta\", \"preco\": 45.90, \"disponibilidadeRestaurante\": true, \"fotoUrl\": \"https://example.com/images/pasta-carbonara.jpg\"}"))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(mediaType = "application/problem+json", examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/business-rule-violation\", \"title\": \"Violação de regra de negócio\", \"status\": 400, \"detail\": \"Nome é obrigatório\", \"code\": \"VALIDATION_ERROR\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/cardapio\"}"))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/problem+json", examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/internal-server-error\", \"title\": \"Erro interno do servidor\", \"status\": 500, \"detail\": \"Um erro inesperado ocorreu\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/cardapio\", \"errorId\": \"Exception\"}")))
    })
    ResponseEntity<CardapioDTO> criar(@RequestBody @Valid CardapioInput input);

    @GetMapping
    @Operation(summary = "Listar todos os itens do cardápio", description = "Retorna uma lista com todos os itens cadastrados no cardápio.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cardápios retornada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CardapioDTO.class), examples = @ExampleObject(value = "[{\"nome\": \"Pasta à Carbonara\", \"descricao\": \"Massa fresca com molho cremoso de ovos, queijo parmesão e pancetta\", \"preco\": 45.90, \"disponibilidadeRestaurante\": true, \"fotoUrl\": \"https://example.com/images/pasta-carbonara.jpg\"}, {\"nome\": \"Pizza Margherita\", \"descricao\": \"Pizza com molho de tomate, mozzarella e manjericão fresco\", \"preco\": 38.50, \"disponibilidadeRestaurante\": true, \"fotoUrl\": \"https://example.com/images/pizza-margherita.jpg\"}]"))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/problem+json", examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/internal-server-error\", \"title\": \"Erro interno do servidor\", \"status\": 500, \"detail\": \"Um erro inesperado ocorreu\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/cardapio\", \"errorId\": \"Exception\"}")))
    })
    ResponseEntity<List<CardapioDTO>> listar();

    @GetMapping("/{id}")
    @Operation(summary = "Obter item do cardápio por ID", description = "Retorna os detalhes de um item específico do cardápio.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item retornado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CardapioDTO.class), examples = @ExampleObject(value = "{\"nome\": \"Pasta à Carbonara\", \"descricao\": \"Massa fresca com molho cremoso de ovos, queijo parmesão e pancetta\", \"preco\": 45.90, \"disponibilidadeRestaurante\": true, \"fotoUrl\": \"https://example.com/images/pasta-carbonara.jpg\"}"))),
            @ApiResponse(responseCode = "404", description = "Item não encontrado", content = @Content(mediaType = "application/problem+json", examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/not-found\", \"title\": \"Recurso não encontrado\", \"status\": 404, \"detail\": \"Cardápio com ID 999 não encontrado\", \"code\": \"CARDAPIO_NOT_FOUND\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/cardapio/999\"}"))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/problem+json", examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/internal-server-error\", \"title\": \"Erro interno do servidor\", \"status\": 500, \"detail\": \"Um erro inesperado ocorreu\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/cardapio/1\", \"errorId\": \"Exception\"}")))
    })
    ResponseEntity<CardapioDTO> obterPorId(@PathVariable Long id);

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar item do cardápio", description = "Atualiza os dados de um item existente no cardápio.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CardapioDTO.class), examples = @ExampleObject(value = "{\"nome\": \"Pasta à Carbonara Premium\", \"descricao\": \"Massa fresca com molho cremoso de ovos, queijo parmesão e pancetta de qualidade superior\", \"preco\": 52.90, \"disponibilidadeRestaurante\": true, \"fotoUrl\": \"https://example.com/images/pasta-carbonara-premium.jpg\"}"))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(mediaType = "application/problem+json", examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/business-rule-violation\", \"title\": \"Violação de regra de negócio\", \"status\": 400, \"detail\": \"Preço deve ser maior que zero\", \"code\": \"VALIDATION_ERROR\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/cardapio/1\"}"))),
            @ApiResponse(responseCode = "404", description = "Item não encontrado", content = @Content(mediaType = "application/problem+json", examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/not-found\", \"title\": \"Recurso não encontrado\", \"status\": 404, \"detail\": \"Cardápio com ID 999 não encontrado\", \"code\": \"CARDAPIO_NOT_FOUND\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/cardapio/999\"}"))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/problem+json", examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/internal-server-error\", \"title\": \"Erro interno do servidor\", \"status\": 500, \"detail\": \"Um erro inesperado ocorreu\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/cardapio/1\", \"errorId\": \"Exception\"}")))
    })
    ResponseEntity<CardapioDTO> atualizar(@PathVariable Long id, @RequestBody @Valid CardapioInput input);

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar item do cardápio", description = "Remove um item existente do cardápio.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado", content = @Content(mediaType = "application/problem+json", examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/not-found\", \"title\": \"Recurso não encontrado\", \"status\": 404, \"detail\": \"Cardápio com ID 999 não encontrado\", \"code\": \"CARDAPIO_NOT_FOUND\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/cardapio/999\"}"))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/problem+json", examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/internal-server-error\", \"title\": \"Erro interno do servidor\", \"status\": 500, \"detail\": \"Um erro inesperado ocorreu\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/cardapio/1\", \"errorId\": \"Exception\"}")))
    })
    ResponseEntity<Void> deletar(@PathVariable Long id);
}
