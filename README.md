# Projeto com Spring Boot e Angular

Este projeto é um teste utilizando **Spring Boot (Java 21)** no backend e **Angular 19** no frontend.

## Tecnologias Utilizadas

- **Backend:** Java 21 com Spring Boot
- **Frontend:** Angular 19
- **Banco de Dados:** PostgreSQL
- **Ferramentas de Build:** Maven
- **Containerização:** Docker Compose

## Endpoints da API

A API expõe os seguintes endpoints:

### Autenticação

### Usuário (Login)

- **POST `/usuario`** - Realiza a autenticação do usuário.
  - **Credenciais Padrão:**
    - **Login:** `admin`
    - **Senha:** `admin123`
  - **Resposta:**
    ```json
      {
        "id": 9007199254740991,
        "loguin": "admin",
        "senha": "admin123"
      }
    ```

- **POST `/token`** - Gera um token JWT para autenticação.
  - **Resposta:**
    ```json
    {
      "sub": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJub3h0ZWMiLCJpYXQiOjE3Mzg1NDM5NjQsImV4cCI6MTczODU0NzU2NH0.cV9FJx2CJanSbNmrylaYC1MK8CoCpHzErzE6GbmZ6Io"
    }
    ```
  - O token JWT deve ser enviado no cabeçalho `Authorization` como `Bearer <token>` em requisições protegidas.

### Processos

- **POST `/processos`** - Cria um novo processo.
  - **Payload:**
    ```json
    {
        "id": 9007199254740991,
        "npu": "string",
        "dataCadastro": "2025-02-11T16:33:04.355Z",
        "dataVisualizacao": "2025-02-11T16:33:04.356Z",
        "municipio": "string",
        "uf": "string",
        "usuario_id": 9007199254740991,
        "documentosDto": [
            {
            "id": 9007199254740991,
            "path": "string",
            "documentoPdf": "string",
            "processo_id": 1073741824
            }
        ]
    }
    ```
  - **Campos obrigatórios:** `npu`, `municipio`, `uf`
- **PUT `/processos/{id}`** - Atualiza um processo existente.
  - **Payload:**
    ```json
    {
        "id": 9007199254740991,
        "npu": "string",
        "dataCadastro": "2025-02-11T16:33:04.355Z",
        "dataVisualizacao": "2025-02-11T16:33:04.356Z",
        "municipio": "string",
        "uf": "string",
        "usuario_id": 9007199254740991,
        "documentosDto": [
            {
            "id": 9007199254740991,
            "path": "string",
            "documentoPdf": "string",
            "processo_id": 1073741824
            }
        ]
    }
    ```
- **DELETE `/processos/{id}`** - Exclui um processo pelo ID.
- **GET `/processos/{id}`** - Retorna um processo específico pelo ID.
  - **Payload:**
    ```json
    {
        "id": 9007199254740991,
        "npu": "string",
        "dataCadastro": "2025-02-11T16:33:04.355Z",
        "dataVisualizacao": "2025-02-11T16:33:04.356Z",
        "municipio": "string",
        "uf": "string",
        "usuario_id": 9007199254740991,
        "documentosDto": [
            {
            "id": 9007199254740991,
            "path": "string",
            "documentoPdf": "string",
            "processo_id": 1073741824
            }
        ]
    }
    ```
## Documentação da API

A documentação da API pode ser acessada via Swagger na seguinte URL:

[Swagger UI] `http://localhost:8080/swagger-ui.html`

## Como Executar o Projeto

### Backend

1. Certifique-se de ter o Java 21 instalado.
2. Clone este repositório.
3. Navegue até a pasta do backend.
4. Execute o seguinte comando para iniciar o servidor:
   ```sh
   mvn spring-boot:run
   ```

### Frontend

1. Certifique-se de ter o Node.js instalado.
2. Navegue até a pasta do frontend (app-processo).
3. Instale as dependências:
   ```sh
   npm install
   ```
4. Execute o seguinte comando para iniciar o servidor de desenvolvimento:
   ```sh
   ng serve
   ```
5. Acesse o frontend pelo navegador em `http://localhost:4200`.
6. Na tela de login, utilize as credenciais padrão:
   - **Login:** `admin`
   - **Senha:** `admin123`

### Banco de Dados esta no Docker Compose

1. Certifique-se de ter o Docker e o Docker Compose instalados.
2. Na raiz do projeto, execute o seguinte comando para subir o banco de dados MYSQL:
   ```sh
   docker-compose up -d
   ```
3. O banco de dados estará disponível para conexão.