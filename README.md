# Restaurant Management API

API para gerenciamento de restaurantes, usuários, tipos de usuário, cardápios e expedientes de funcionamento.

O projeto foi desenvolvido em Java 21 com Spring Boot, Spring MVC, Spring Data JPA, Jakarta Validation, Lombok, MySQL e documentação interativa via OpenAPI/Swagger.

---

## Sumário

- [Descrição do Projeto](#descrição-do-projeto)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Arquitetura](#arquitetura)
- [Estrutura de Pastas](#estrutura-de-pastas)
- [Principais Domínios](#principais-domínios)
- [Endpoints da API](#endpoints-da-api)
  - [Tipos de Usuário](#tipos-de-usuário)
  - [Usuários](#usuários)
  - [Restaurantes](#restaurantes)
  - [Cardápio](#cardápio)
  - [Expediente do Restaurante](#expediente-do-restaurante)
- [Tratamento de Erros](#tratamento-de-erros)
- [Configuração do Ambiente](#configuração-do-ambiente)
- [Execução com Docker](#execução-com-docker)
- [Execução da Aplicação](#execução-da-aplicação)
- [Execução dos Testes](#execução-dos-testes)
- [Swagger/OpenAPI](#swaggeropenapi)
- [Coleções Postman](#coleções-postman)

---

## Descrição do Projeto

O **Restaurant Management API** é uma aplicação backend responsável por centralizar o cadastro e a manutenção de informações relacionadas a restaurantes.

A aplicação permite:

- Cadastrar, atualizar, consultar e excluir usuários;
- Cadastrar e gerenciar tipos de usuário;
- Associar usuários a tipos de usuário;
- Cadastrar, atualizar, consultar e excluir restaurantes;
- Associar restaurantes a usuários donos;
- Cadastrar e manter itens de cardápio;
- Cadastrar e manter horários de funcionamento dos restaurantes;
- Consultar expedientes vinculados a um restaurante.

O sistema segue uma separação clara entre regras de negócio, camada de infraestrutura, persistência e exposição REST.

---

## Tecnologias Utilizadas

- Java 21
- Spring Boot 4.1.0
- Spring MVC
- Spring Data JPA
- Jakarta Validation
- Lombok
- MySQL 8
- H2 Database para testes
- Maven
- Docker Compose
- Springdoc OpenAPI/Swagger
- JUnit
- Mockito
- JaCoCo

---

## Arquitetura

O projeto utiliza uma arquitetura em camadas inspirada em princípios de Clean Architecture, separando o domínio da aplicação dos detalhes de infraestrutura.

### Camadas principais

#### 1. Core

Contém as regras de negócio da aplicação.

Responsabilidades:

- Definir entidades de domínio;
- Validar regras de negócio;
- Declarar contratos de entrada e saída;
- Implementar casos de uso;
- Definir gateways para comunicação com infraestrutura.

Pacotes principais:
```
text
br.com.fiap.restaurant_management.core.domain
br.com.fiap.restaurant_management.core.dto
br.com.fiap.restaurant_management.core.mapper
br.com.fiap.restaurant_management.core.gateway
br.com.fiap.restaurant_management.core.usecase
br.com.fiap.restaurant_management.core.controller
br.com.fiap.restaurant_management.core.exception
```
#### 2. Infraestrutura Web

Responsável pela exposição da API REST.

Responsabilidades:

- Receber requisições HTTP;
- Validar payloads de entrada;
- Converter objetos de entrada para DTOs da aplicação;
- Delegar chamadas para os controllers da camada core;
- Documentar endpoints com OpenAPI.

Pacotes principais:
```
text
br.com.fiap.restaurant_management.infra.web.rest
br.com.fiap.restaurant_management.infra.web.dto
br.com.fiap.restaurant_management.infra.web.config
```
#### 3. Infraestrutura de Persistência

Responsável pela comunicação com o banco de dados.

Responsabilidades:

- Mapear entidades JPA;
- Definir repositories Spring Data JPA;
- Implementar gateways;
- Converter entidades JPA para objetos de domínio e vice-versa.

Pacotes principais:
```
text
br.com.fiap.restaurant_management.infra.database.jpa
br.com.fiap.restaurant_management.infra.database.jpa.entity
br.com.fiap.restaurant_management.infra.database.jpa.repository
br.com.fiap.restaurant_management.infra.database.mapper
```
#### 4. Configuração

Responsável por configurar beans, injeção de dependência, carga inicial de dados e tratamento global de exceções.

Pacote principal:
```
text
br.com.fiap.restaurant_management.infra.config
```
---

## Estrutura de Pastas
```
text
tech_challenge_2
├── docker
│   └── docker-compose.yml
├── postman
│   ├── cardapio.postman_collection.json
│   ├── restaurante-expediente.postman_collection.json
│   ├── restaurantes.postman_collection.json
│   ├── tipos-usuario.postman_collection.json
│   └── usuarios.postman_collection.json
├── src
│   ├── main
│   │   ├── java
│   │   │   └── br/com/fiap/restaurant_management
│   │   └── resources
│   │       └── application.yaml
│   └── test
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README-RESTAURANTES.md
```
---

## Principais Domínios

### Tipo de Usuário

Representa uma categoria de usuário do sistema.

Exemplos:

- DONO
- CLIENTE

### Usuário

Representa uma pessoa cadastrada no sistema.

Principais dados:

- Nome
- E-mail
- Login
- Senha
- Tipo de usuário

### Restaurante

Representa um restaurante cadastrado na plataforma.

Principais dados:

- Nome
- Endereço
- Tipo de cozinha
- Dono

### Cardápio

Representa um item disponível no cardápio de um restaurante.

Principais dados:

- Restaurante
- Nome
- Descrição
- Preço
- Disponibilidade
- URL da foto

### Expediente do Restaurante

Representa o horário de funcionamento de um restaurante.

Principais dados:

- Restaurante
- Dia da semana
- Hora de abertura
- Hora de fechamento

---

## Endpoints da API

A URL base da aplicação local é:
```
text
http://localhost:8080
```
---

## Tipos de Usuário

Base path:
```
text
/v1/tipo-usuario
```
### Criar tipo de usuário
```
http
POST /v1/tipo-usuario
```
Exemplo de requisição:
```
json
{
"nome": "DONO"
}
```
Resposta de sucesso:
```
json
1
```
### Listar tipos de usuário
```
http
GET /v1/tipo-usuario
```
Exemplo de resposta:
```
json
[
{
"id": 1,
"nome": "DONO"
},
{
"id": 2,
"nome": "CLIENTE"
}
]
```
### Atualizar tipo de usuário
```
http
PUT /v1/tipo-usuario/{id}
```
Exemplo de requisição:
```
json
{
"nome": "ADMINISTRADOR"
}
```
### Excluir tipo de usuário
```
http
DELETE /v1/tipo-usuario/{id}
```
---

## Usuários

Base path:
```
text
/v1/usuario
```
### Criar usuário
```
http
POST /v1/usuario
```
Exemplo de requisição:
```
json
{
"nome": "João Silva",
"email": "joao@email.com",
"login": "joao.silva",
"senha": "123456",
"tipoUsuarioId": 1
}
```
Resposta de sucesso:
```
json
1
```
### Listar usuários
```
http
GET /v1/usuario
```
Exemplo de resposta:
```
json
[
{
"id": 1,
"nome": "JOÃO SILVA",
"email": "JOAO@EMAIL.COM",
"login": "JOAO.SILVA",
"tipoUsuario": {
"id": 1,
"nome": "DONO"
},
"criadoEm": "2026-07-14T10:00:00",
"atualizadoEm": null
}
]
```
### Atualizar usuário
```
http
PUT /v1/usuario/{id}
```
Exemplo de requisição:
```
json
{
"nome": "João Silva Atualizado",
"email": "joao.atualizado@email.com",
"login": "joao.atualizado",
"senha": "novaSenha123",
"tipoUsuarioId": 1
}
```
### Atualizar tipo de usuário de um usuário
```
http
PATCH /v1/usuario/{usuarioId}
```
Exemplo de requisição:
```
json
{
"usuarioId": 1,
"tipoUsuarioId": 2
}
```
### Excluir usuário
```
http
DELETE /v1/usuario/{id}
```
---

## Restaurantes

Base path:
```
text
/v1/restaurantes
```
### Criar restaurante
```
http
POST /v1/restaurantes
```
Exemplo de requisição:
```
json
{
"nome": "Sabor Brasil",
"endereco": "Rua A, 10",
"tipoCozinha": "Brasileira",
"idDono": 1
}
```
Exemplo de resposta:
```
json
{
"id": 1,
"nome": "Sabor Brasil",
"endereco": "Rua A, 10",
"tipoCozinha": "Brasileira",
"idDono": 1
}
```
### Listar restaurantes
```
http
GET /v1/restaurantes
```
### Consultar restaurante por ID
```
http
GET /v1/restaurantes/{id}
```
### Atualizar restaurante
```
http
PUT /v1/restaurantes/{id}
```
Exemplo de requisição:
```
json
{
"nome": "Sabor Brasil Premium",
"endereco": "Rua B, 100",
"tipoCozinha": "Brasileira Contemporânea",
"idDono": 1
}
```
### Excluir restaurante
```
http
DELETE /v1/restaurantes/{id}
```
Resposta de sucesso:
```
http
204 No Content
```
---

## Cardápio

Base path:
```
text
/v1/cardapio
```
### Criar item de cardápio
```
http
POST /v1/cardapio
```
Exemplo de requisição:
```
json
{
"idRestaurante": 1,
"nome": "Pasta à Carbonara",
"descricao": "Massa fresca com molho cremoso de ovos, queijo parmesão e pancetta",
"preco": 45.90,
"disponibilidadeRestaurante": true,
"fotoUrl": "https://example.com/images/pasta-carbonara.jpg"
}
```
Exemplo de resposta:
```
json
{
"id": 1,
"idRestaurante": 1,
"nome": "Pasta à Carbonara",
"descricao": "Massa fresca com molho cremoso de ovos, queijo parmesão e pancetta",
"preco": 45.90,
"disponibilidadeRestaurante": true,
"fotoUrl": "https://example.com/images/pasta-carbonara.jpg"
}
```
### Listar itens de cardápio
```
http
GET /v1/cardapio
```
### Obter item de cardápio por ID
```
http
GET /v1/cardapio/{id}
```
### Atualizar item de cardápio
```
http
PUT /v1/cardapio/{id}
```
Exemplo de requisição:
```
json
{
"idRestaurante": 1,
"nome": "Pasta à Carbonara Premium",
"descricao": "Massa fresca com molho cremoso de ovos, queijo parmesão e pancetta de qualidade superior",
"preco": 52.90,
"disponibilidadeRestaurante": true,
"fotoUrl": "https://example.com/images/pasta-carbonara-premium.jpg"
}
```
### Excluir item de cardápio
```
http
DELETE /v1/cardapio/{id}
```
Resposta de sucesso:
```
http
204 No Content
```
---

## Expediente do Restaurante

Base path:
```
text
/v1/restaurante-expediente
```
### Criar expediente
```
http
POST /v1/restaurante-expediente
```
Exemplo de requisição:
```
json
{
"idRestaurante": 1,
"diaSemana": "SEGUNDA",
"horaAbertura": "08:00",
"horaFechamento": "18:00"
}
```
Exemplo de resposta:
```
json
{
"id": "550e8400-e29b-41d4-a716-446655440000",
"idRestaurante": 1,
"diaSemana": "SEGUNDA",
"horaAbertura": "08:00",
"horaFechamento": "18:00"
}
```
### Consultar expediente por ID
```
http
GET /v1/restaurante-expediente/{id}
```
### Listar expedientes por restaurante
```
http
GET /v1/restaurante-expediente?idRestaurante={idRestaurante}
```
Exemplo:
```
http
GET /v1/restaurante-expediente?idRestaurante=1
```
### Atualizar expediente
```
http
PUT /v1/restaurante-expediente/{id}
```
Exemplo de requisição:
```
json
{
"idRestaurante": 1,
"diaSemana": "TERCA",
"horaAbertura": "09:00",
"horaFechamento": "19:00"
}
```
### Excluir expediente
```
http
DELETE /v1/restaurante-expediente/{id}
```
Resposta de sucesso:
```
http
204 No Content
```
---

## Tratamento de Erros

A aplicação possui tratamento global de exceções e retorna erros em formato compatível com `application/problem+json`.

Exemplo de erro de regra de negócio:
```
json
{
"type": "https://api.restaurant-management.com/errors/business-rule-violation",
"title": "Violação de regra de negócio",
"status": 400,
"detail": "Nome do Tipo de Usuário não pode ser nulo ou vazio",
"code": "VALIDATION_ERROR",
"timestamp": "2026-07-14T10:00:00",
"path": "/v1/tipo-usuario"
}
```
Exemplo de recurso não encontrado:
```
json
{
"type": "https://api.restaurant-management.com/errors/not-found",
"title": "Recurso não encontrado",
"status": 404,
"detail": "Usuário com ID 999 não encontrado",
"code": "USUARIO_NOT_FOUND",
"timestamp": "2026-07-14T10:00:00",
"path": "/v1/usuario/999"
}
```
Principais status HTTP utilizados:

| Status | Descrição |
|---|---|
| 200 | Operação realizada com sucesso |
| 201 | Recurso criado com sucesso |
| 204 | Recurso excluído com sucesso, sem conteúdo na resposta |
| 400 | Dados inválidos ou violação de regra de negócio |
| 404 | Recurso não encontrado |
| 500 | Erro interno do servidor |

---

## Configuração do Ambiente

### Pré-requisitos

Para executar o projeto localmente, é necessário ter instalado:

- Java 21
- Maven ou Maven Wrapper
- Docker
- Docker Compose

### Banco de dados

A aplicação utiliza MySQL por padrão.

Configuração definida em `src/main/resources/application.yaml`:
```
yaml
spring:
datasource:
url: jdbc:mysql://localhost:3307/restaurant_management?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true
username: root
password: root
driver-class-name: com.mysql.cj.jdbc.Driver

jpa:
hibernate:
ddl-auto: create
show-sql: false
```
Dados padrão:

| Configuração | Valor |
|---|---|
| Banco | MySQL |
| Host | localhost |
| Porta externa | 3307 |
| Database | restaurant_management |
| Usuário | root |
| Senha | root |

---

## Execução com Docker

O projeto possui um arquivo `docker-compose.yml` para subir o banco MySQL.

Para iniciar o banco de dados:
```
bash
docker compose -f docker/docker-compose.yml up -d
```
Para verificar os containers em execução:
```
bash
docker ps
```
Para parar o banco de dados:
```
bash
docker compose -f docker/docker-compose.yml down
```
Caso queira remover também o volume com os dados:
```
bash
docker compose -f docker/docker-compose.yml down -v
```
---

## Execução da Aplicação

Com o banco de dados em execução, rode a aplicação usando o Maven Wrapper.

### Linux/macOS
```
bash
./mvnw spring-boot:run
```
### Windows
```
bash
mvnw.cmd spring-boot:run
```
A aplicação ficará disponível em:
```
text
http://localhost:8080
```
---

## Execução dos Testes

Para executar os testes automatizados:

### Linux/macOS
```
bash
./mvnw test
```
### Windows
```
bash
mvnw.cmd test
```
Para executar verificação completa com relatório JaCoCo:
```
bash
./mvnw verify
```
No Windows:
```
bash
mvnw.cmd verify
```
O relatório de cobertura é gerado em:
```
text
target/site/jacoco/index.html
```
---

## Swagger/OpenAPI

A aplicação possui documentação interativa com Swagger/OpenAPI.

Após iniciar a aplicação, acesse:
```
text
http://localhost:8080/swagger-ui/index.html
```
Também é possível acessar a especificação OpenAPI em:
```
text
http://localhost:8080/v3/api-docs
```
---

## Coleções Postman

O projeto contém coleções Postman para facilitar os testes manuais da API.

As coleções estão localizadas na pasta:
```
text
postman
```
Arquivos disponíveis:
```
text
cardapio.postman_collection.json
restaurante-expediente.postman_collection.json
restaurantes.postman_collection.json
tipos-usuario.postman_collection.json
usuarios.postman_collection.json
```
Para utilizar:

1. Abra o Postman;
2. Clique em `Import`;
3. Selecione uma das coleções da pasta `postman`;
4. Execute as requisições apontando para:
```
text
http://localhost:8080
```
---

## Fluxo básico recomendado para testes

Uma sequência recomendada para testar a API é:

1. Criar um tipo de usuário `DONO`;
2. Criar um usuário associado ao tipo `DONO`;
3. Criar um restaurante associado ao usuário dono;
4. Criar expedientes para o restaurante;
5. Criar itens de cardápio para o restaurante;
6. Consultar os recursos cadastrados;
7. Atualizar os dados;
8. Excluir os recursos.

---

## Observações

- O Hibernate está configurado com `ddl-auto: create`, portanto as tabelas são recriadas ao iniciar a aplicação.
- O banco de dados utilizado em ambiente local é MySQL.
- Para testes automatizados, o projeto utiliza H2.
- A API utiliza validações com Jakarta Validation.
- As regras de negócio ficam concentradas na camada `core`.
- A camada REST atua como adaptador de entrada para os casos de uso da aplicação.
```
