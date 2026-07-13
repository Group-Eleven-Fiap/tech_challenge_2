# Módulo de restaurantes

Este documento descreve somente a parte de restaurantes da branch `feature/restaurante`.

## Funcionalidades entregues

- cadastrar restaurante associado a um usuário dono existente;
- atualizar todos os dados do restaurante;
- consultar um restaurante por identificador;
- listar restaurantes;
- excluir restaurante;
- validar os campos obrigatórios e a existência do dono;
- documentar os endpoints no Swagger/OpenAPI;
- testar regras unitariamente e o fluxo completo com banco H2.

## Arquitetura e arquivos

O módulo segue Clean Architecture. O núcleo não depende de JPA nem de HTTP.

| Camada | Arquivo | Responsabilidade |
|---|---|---|
| Domain | `core/domain/Restaurante.java` | Modelo e regras obrigatórias do restaurante |
| Application | `core/usecase/RestauranteUseCase.java` | Porta de entrada dos casos de uso |
| Application | `core/usecase/RestauranteUseCaseImpl.java` | Cadastro, consulta, atualização, exclusão e validação do dono |
| Application | `core/gateway/RestauranteGateway.java` | Porta de saída para persistência |
| Interface adapter | `core/controller/RestauranteController.java` | Converte a chamada externa para o caso de uso |
| Interface adapter | `core/mapper/RestauranteMapper.java` | Conversão entre domínio e DTO |
| Interface adapter | `core/dto/RestauranteDTO.java` | Resposta da API sem dependência de JPA |
| Infrastructure/web | `infra/web/rest/RestauranteApiController.java` | Endpoints REST em `/v1/restaurantes` |
| Infrastructure/web | `infra/web/dto/RestauranteInput.java` | Entrada e validações Jakarta Validation |
| Infrastructure/database | `infra/database/jpa/RestauranteJpaGateway.java` | Implementação JPA da porta de persistência |
| Infrastructure/database | `infra/database/jpa/entity/RestauranteEntity.java` | Tabela `TB_RESTAURANTE` e chave estrangeira do dono |
| Infrastructure/database | `infra/database/jpa/repository/RestauranteRepository.java` | Repositório Spring Data |
| Infrastructure/database | `infra/database/mapper/RestauranteEntityMapper.java` | Conversão entre entidade JPA e domínio |
| Configuration | `infra/config/InjectionConfiguration.java` | Liga controller, caso de uso e gateway por injeção de dependência |

Fluxo de uma requisição:

`RestauranteApiController -> RestauranteController -> RestauranteUseCase -> RestauranteGateway -> RestauranteJpaGateway -> MySQL`

## Endpoints

| Método | Caminho | Resultado esperado |
|---|---|---|
| `POST` | `/v1/restaurantes` | `201 Created` e restaurante cadastrado |
| `GET` | `/v1/restaurantes` | `200 OK` e lista de restaurantes |
| `GET` | `/v1/restaurantes/{id}` | `200 OK` ou `404 Not Found` |
| `PUT` | `/v1/restaurantes/{id}` | `200 OK` e restaurante atualizado |
| `DELETE` | `/v1/restaurantes/{id}` | `204 No Content` |

Corpo de cadastro e atualização:

```json
{
  "nome": "Sabor Brasil",
  "endereco": "Rua das Flores, 100 - São Paulo/SP",
  "tipoCozinha": "Brasileira",
  "horarioFuncionamento": "Segunda a sábado, das 11h às 23h",
  "idDono": 1
}
```

O `idDono` precisa apontar para um registro previamente criado em `/v1/usuario`.

## Como executar

Pré-requisitos: Java 21 e Docker.

1. Suba o MySQL:

   ```powershell
   docker compose -f docker/docker-compose.yml up -d
   ```

2. Inicie a aplicação:

   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

3. Abra o Swagger em `http://localhost:8080/swagger-ui.html` e use a seção **Restaurantes**.

4. Para encerrar o banco:

   ```powershell
   docker compose -f docker/docker-compose.yml down
   ```

## Testes e cobertura

Execute tudo e gere o relatório JaCoCo:

```powershell
.\mvnw.cmd clean verify
```

O relatório HTML fica em `target/site/jacoco/index.html`. Na verificação desta branch foram executados 111 testes sem falhas; as classes cujo nome começa com `Restaurante` (sem incluir o módulo separado `RestauranteExpediente`) alcançaram 97,86% de cobertura de linhas.

Testes do módulo:

| Arquivo | Tipo | O que comprova |
|---|---|---|
| `core/domain/RestauranteTest.java` | unitário | campos obrigatórios e normalização |
| `core/usecase/RestauranteUseCaseImplTest.java` | unitário | regras, dono existente e CRUD |
| `infra/web/rest/RestauranteApiControllerTest.java` | unitário | respostas HTTP produzidas pelos endpoints |
| `RestauranteIntegrationTest.java` | integração | controller, caso de uso, gateway, JPA e H2 no fluxo completo |

## Collection

Importe `postman/restaurantes.postman_collection.json`. Ela contém cadastro, listagem, consulta, atualização e exclusão. Ajuste `idDono` para um usuário existente; a variável `idRestaurante` é preenchida automaticamente após o cadastro.

## Observação de integração com o grupo

`Usuario` e `Cardapio` usam identificadores `Long`, portanto o restaurante também usa `Long`. O módulo existente `RestauranteExpediente` usa UUID para `idRestaurante`; essa inconsistência pertence a outro módulo e não foi alterada nesta entrega para manter o commit restrito à parte de restaurante.
