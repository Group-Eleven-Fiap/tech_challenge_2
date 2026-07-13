# Módulo de restaurantes

Este documento descreve somente a parte de restaurantes da branch `feature/restaurante`.

## Funcionalidades entregues

- cadastrar restaurante associado a um usuário dono existente;
- atualizar todos os dados do restaurante;
- consultar um restaurante por identificador;
- listar restaurantes;
- excluir restaurante;
- cadastrar os horários por dia da semana em `TB_RESTAURANTE_EXPEDIENTE`;
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
| Infrastructure/database | `infra/database/jpa/entity/RestauranteEntity.java` | Tabela `TB_RESTAURANTE`, dono e relacionamento com expedientes |
| Infrastructure/database | `infra/database/jpa/entity/RestauranteExpedienteEntity.java` | Horários e chave estrangeira `id_restaurante` |
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
| `POST` | `/v1/restaurante-expediente` | Cadastra um horário para um dia da semana |
| `GET` | `/v1/restaurante-expediente?idRestaurante={id}` | Lista os horários do restaurante |

Corpo de cadastro e atualização:

```json
{
  "nome": "Sabor Brasil",
  "endereco": "Rua das Flores, 100 - São Paulo/SP",
  "tipoCozinha": "Brasileira",
  "idDono": 1
}
```

O `idDono` precisa apontar para um registro previamente criado em `/v1/usuario`.

O horário é cadastrado separadamente e fica vinculado por chave estrangeira:

```json
{
  "idRestaurante": 1,
  "diaSemana": "SEGUNDA",
  "horaAbertura": "11:00:00",
  "horaFechamento": "22:00:00"
}
```

Cada restaurante pode ter um expediente por dia da semana. Não existe mais a coluna textual `horario_funcionamento` em `TB_RESTAURANTE`.

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

O relatório HTML fica em `target/site/jacoco/index.html`. Na verificação desta branch foram executados 112 testes sem falhas. O CRUD de `Restaurante` alcançou 98,51% e o conjunto `Restaurante` + `RestauranteExpediente` alcançou 98,01% de cobertura de linhas.

Testes do módulo:

| Arquivo | Tipo | O que comprova |
|---|---|---|
| `core/domain/RestauranteTest.java` | unitário | campos obrigatórios e normalização |
| `core/usecase/RestauranteUseCaseImplTest.java` | unitário | regras, dono existente e CRUD |
| `infra/web/rest/RestauranteApiControllerTest.java` | unitário | respostas HTTP produzidas pelos endpoints |
| `RestauranteIntegrationTest.java` | integração | CRUD, chave estrangeira restaurante-expediente e exclusão em cascata com H2 |

## Collection

Importe `postman/restaurantes.postman_collection.json`. Ela contém CRUD do restaurante e cadastro/consulta de expediente. Ajuste `idDono` para um usuário existente; a variável `idRestaurante` é preenchida automaticamente após o cadastro.

## Observação de integração com o grupo

`Usuario`, `Cardapio` e `Restaurante` usam identificadores `Long`. O identificador do próprio expediente continua sendo UUID, enquanto sua chave estrangeira `id_restaurante` é `Long` e referencia `TB_RESTAURANTE.id`.
