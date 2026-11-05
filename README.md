# API de Tarefas — Spring Boot

Atividade prática do Projeto Bimestral (IFPR Cascavel). API REST de tarefas com
Controller, Service, Repository e DTOs. Banco H2 em memória.

## Como executar

Requisitos: Java 17+ e Maven.

```bash
mvn spring-boot:run
```

A API sobe em `http://localhost:8080`.

## Organização

| Camada | Classe | Responsabilidade |
|---|---|---|
| Controller | `TodoController` | Recebe as requisições e define as respostas HTTP (201, 204, 200) |
| Service | `TodoService` | Aplica as regras: gera data de criação, deixa `finishedAt` nulo, atualiza só `finishedAt` |
| Repository | `TodoRepository` | Acesso ao banco (JPA), incluindo as consultas por `finishedAt` |
| Model | `Todo` | Entidade com `id`, `title`, `deadLine`, `createdAt`, `finishedAt` |
| DTO | `TodoRequestDTO`, `TodoConclusaoDTO`, `TodoResponseDTO` | Entrada e saída da API |

Caminho de uma requisição: Thunder Client → `TodoController` → `TodoService` →
`TodoRepository` → banco, e o resultado volta pelo mesmo caminho convertido em DTO.

## Solução adotada para os DTOs (Desafio)

O DTO de resposta original não tinha `finishedAt`. Em vez de colocar tudo em um
único DTO, foram separados por finalidade:

- **`TodoRequestDTO`** (`title`, `deadLine`): usado no cadastro. O cliente não
  envia `id`, `createdAt` nem `finishedAt`, pois são responsabilidade do sistema.
- **`TodoConclusaoDTO`** (`finishedAt`): usado só no PATCH de conclusão. Assim é
  possível receber `finishedAt` sem mexer nos outros campos.
- **`TodoResponseDTO`** (`id`, `title`, `deadLine`, `createdAt`, `finishedAt`):
  usado em todas as respostas. Agora inclui `finishedAt`, então o cliente
  consegue conferir a data registrada (aparece `null` enquanto pendente).

A conversão entidade → DTO de resposta é feita por `TodoResponseDTO.from(todo)`.

## Exemplos de requisições

### 1. Inserir tarefa — `POST /api/todos`

```json
{
  "title": "Estudar Spring Boot",
  "deadLine": "2026-10-30T18:00:00"
}
```

Resposta: `201 Created`

```json
{
  "id": 1,
  "title": "Estudar Spring Boot",
  "deadLine": "2026-10-30T18:00:00",
  "createdAt": "2026-11-05T07:50:12.123",
  "finishedAt": null
}
```

### 2. Excluir tarefa — `DELETE /api/todos/1`

Resposta: `204 No Content` (sem corpo).

### 3. Informar conclusão — `PATCH /api/todos/1/conclusao`

```json
{
  "finishedAt": "2026-10-22T10:30:00"
}
```

Resposta: `200 OK`

```json
{
  "id": 1,
  "title": "Estudar Spring Boot",
  "deadLine": "2026-10-30T18:00:00",
  "createdAt": "2026-11-05T07:50:12.123",
  "finishedAt": "2026-10-22T10:30:00"
}
```

### 4. Listar concluídas — `GET /api/todos/concluidas`

Retorna `200` com as tarefas que têm `finishedAt` preenchido, ou `[]`.

### 5. Listar pendentes — `GET /api/todos/pendentes`

Retorna `200` com as tarefas em que `finishedAt` é `null`, ou `[]`.

## Roteiro de testes (Thunder Client)

1. `POST /api/todos` duas vezes.
2. `PATCH /api/todos/1/conclusao` com um `finishedAt`.
3. `GET /api/todos/concluidas` e `GET /api/todos/pendentes` e verificar a separação.
4. Conferir `finishedAt` na resposta do PATCH e da lista de concluídas.
5. `DELETE /api/todos/2` e consultar as listagens novamente.

> Nesta etapa não há tratamento de exceções: use IDs existentes e dados válidos.
