# Estoque API - Sistema de Gerenciamento de Estoque

API REST para gerenciamento de estoque de produtos com controle de movimentações de entrada e saída.

## 📋 Índice

- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Pré-requisitos](#pré-requisitos)
- [Instalação das Dependências](#instalação-das-dependências)
- [Configuração do Projeto](#configuração-do-projeto)
- [Como Executar](#como-executar)
- [Endpoints da API](#endpoints-da-api)
- [Documentação](#documentação)

## 🚀 Tecnologias Utilizadas

- **Java 21** - Linguagem de programação
- **Spring Boot 4.0.3** - Framework para desenvolvimento de aplicações Java
  - Spring Web MVC - Para criação de APIs REST
  - Spring Data JPA - Para persistência de dados
  - Spring Boot DevTools - Para desenvolvimento com hot reload
- **H2 Database** - Banco de dados em memória para desenvolvimento
- **Flyway** - Controle de versionamento de banco de dados
- **Lombok** - Redução de código boilerplate
- **SpringDoc OpenAPI** - Documentação automática da API (Swagger)
- **Maven** - Gerenciador de dependências e build
- **Docker & Docker Compose** - Containerização da aplicação

## 📦 Pré-requisitos

Antes de começar, você precisará ter as seguintes ferramentas instaladas em sua máquina:

### 1. Java Development Kit (JDK) 21

**Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install openjdk-21-jdk -y
```

**Verificar instalação:**
```bash
java -version
```

Você deve ver algo como: `openjdk version "21.x.x"`

### 2. Maven

**Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install maven -y
```

**Verificar instalação:**
```bash
mvn -version
```

### 3. Docker e Docker Compose

**Linux (Ubuntu/Debian):**
```bash
# Instalar Docker
sudo apt update
sudo apt install docker.io -y
sudo systemctl start docker
sudo systemctl enable docker

# Adicionar seu usuário ao grupo docker (para executar sem sudo)
sudo usermod -aG docker $USER

# Instalar Docker Compose
sudo apt install docker-compose -y
```

**Verificar instalação:**
```bash
docker --version
docker-compose --version
```

**Nota:** Após adicionar seu usuário ao grupo docker, faça logout e login novamente para que as alterações tenham efeito.

### 4. Git

**Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install git -y
```

**Verificar instalação:**
```bash
git --version
```

## 🔧 Instalação das Dependências

### 1. Clonar o Repositório

```bash
git clone <url-do-repositorio>
cd desafio-tecnico-nexdom
```

### 2. Instalar Dependências do Maven

```bash
cd estoque-api
mvn clean install
```

Este comando irá:
- Baixar todas as dependências do projeto
- Compilar o código
- Executar os testes
- Gerar o arquivo JAR da aplicação

## ⚙️ Configuração do Projeto

### Configuração do Banco de Dados

O projeto utiliza **H2 Database** (banco de dados em memória) para desenvolvimento, o que significa que:
- ✅ Não é necessário instalar ou configurar nenhum banco de dados externo
- ✅ O banco é criado automaticamente ao iniciar a aplicação
- ✅ Os dados são populados automaticamente via Flyway migrations
- ⚠️ Os dados são perdidos quando a aplicação é encerrada (banco em memória)

A configuração está no arquivo `estoque-api/src/main/resources/application.yml`.

### Console H2

Você pode acessar o console web do H2 para visualizar e consultar o banco de dados:

**URL:** `http://localhost:8080/h2-console`

**Credenciais:**
- JDBC URL: `jdbc:h2:mem:estoqueDB`
- Username: `sa`
- Password: (deixe em branco)

## 🏃 Como Executar

### Opção 1: Usando Docker Compose (Recomendado)

Esta é a forma mais simples de executar a aplicação completa (API + UI).

```bash
# Na raiz do projeto
docker-compose up --build
```

A aplicação estará disponível em:
- **API:** `http://localhost:8080`
- **UI:** `http://localhost:5173`
- **H2 Console:** `http://localhost:8080/h2-console`

Para visualizar os logs:
```bash
docker-compose logs -f estoque-api
```

Para parar a aplicação:
```bash
docker-compose down
```

### Opção 2: Executando Localmente com Maven

**1. Executar a aplicação:**
```bash
cd estoque-api
mvn spring-boot:run
```

Ou usando o wrapper do Maven:
```bash
cd estoque-api
./mvnw spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

### Opção 3: Executando o JAR Compilado

**1. Compilar o projeto:**
```bash
cd estoque-api
mvn clean package -DskipTests
```

**2. Executar o JAR:**
```bash
java -jar target/estoque-api-0.0.1-SNAPSHOT.jar
```

A aplicação estará disponível em: `http://localhost:8080`

## 🔌 Endpoints da API

### Produtos

- `POST /products` - Criar novo produto
- `GET /products` - Listar todos os produtos
- `GET /products/{id}` - Buscar produto por ID
- `PUT /products` - Atualizar produto
- `DELETE /products/{id}` - Deletar produto
- `GET /products/types` - Listar tipos de produtos
- `GET /products/by-type/{type}` - Listar produtos por tipo
- `GET /products/profit` - Relatório de lucro por produto (com filtro de data)

### Movimentações de Estoque

- `POST /stock-movement` - Registrar movimentação (entrada/saída)
- `GET /stock-movement` - Listar todas as movimentações
- `GET /stock-movement/{id}` - Buscar movimentação por ID
- `DELETE /stock-movement/{id}` - Deletar movimentação

## 📚 Documentação

### Documentação da API (Swagger)

Após iniciar a aplicação, acesse a documentação interativa da API:

**Swagger UI:** `http://localhost:8080/swagger-ui.html`

Aqui você pode:
- Visualizar todos os endpoints disponíveis
- Testar as requisições diretamente pelo navegador
- Ver os schemas de request/response
- Entender os parâmetros necessários para cada endpoint

### Documentação das Tecnologias

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [H2 Database Documentation](https://www.h2database.com/html/main.html)
- [Flyway Documentation](https://flywaydb.org/documentation/)
- [Docker Documentation](https://docs.docker.com/)
- [Maven Documentation](https://maven.apache.org/guides/)
- [Lombok Documentation](https://projectlombok.org/features/)
- [SpringDoc OpenAPI Documentation](https://springdoc.org/)

### Estrutura do Projeto

```
estoque-api/
├── src/
│   ├── main/
│   │   ├── java/br/com/nexdom/estoque_api/
│   │   │   ├── configs/          # Configurações (CORS, etc)
│   │   │   ├── controllers/      # Controllers REST
│   │   │   ├── dto/              # Data Transfer Objects
│   │   │   │   ├── request/      # DTOs de requisição
│   │   │   │   └── response/     # DTOs de resposta
│   │   │   ├── entities/         # Entidades JPA
│   │   │   ├── enums/            # Enumerações
│   │   │   ├── exceptions/       # Exceções customizadas
│   │   │   ├── mappers/          # Mapeadores Entity <-> DTO
│   │   │   ├── repositories/     # Repositórios JPA
│   │   │   └── services/         # Lógica de negócio
│   │   └── resources/
│   │       ├── application.yml   # Configurações da aplicação
│   │       └── db/migration/     # Scripts Flyway
│   └── test/                     # Testes unitários
├── Dockerfile                    # Configuração Docker
├── pom.xml                       # Dependências Maven
└── mvnw                          # Maven Wrapper
```

### Migrações do Banco de Dados

O projeto utiliza Flyway para controle de versão do banco de dados. As migrações estão localizadas em:
```
estoque-api/src/main/resources/db/migration/
```

As migrações são executadas automaticamente ao iniciar a aplicação.

## 🧪 Executando os Testes

```bash
cd estoque-api
mvn test
```

## 🐛 Troubleshooting

### Porta 8080 já em uso

Se a porta 8080 já estiver em uso, você pode alterá-la no arquivo `application.yml`:
```yaml
server:
  port: 8081
```

Ou definir via variável de ambiente:
```bash
SERVER_PORT=8081 mvn spring-boot:run
```

### Erro ao compilar o projeto

Se encontrar erros de compilação, certifique-se de que:
1. Está usando Java 21: `java -version`
2. O Maven está instalado corretamente: `mvn -version`
3. Limpe o cache do Maven: `mvn clean`

### Permissão negada ao executar Docker

Certifique-se de que seu usuário está no grupo docker:
```bash
sudo usermod -aG docker $USER
```

Faça logout e login novamente para aplicar as mudanças.

### Erro "mvnw: Permission denied"

Se o Maven Wrapper não tiver permissão de execução:
```bash
chmod +x mvnw
```

### Dados não aparecem no H2

Verifique se as migrations do Flyway foram executadas:
1. Acesse o console H2: `http://localhost:8080/h2-console`
2. Verifique se as tabelas `products` e `stock_movements` existem
3. Verifique os logs da aplicação para erros do Flyway

## 🎯 Funcionalidades

- ✅ Cadastro, listagem, atualização e exclusão de produtos
- ✅ Controle de tipos de produtos (ELETRONICO, ALIMENTO, VESTUARIO, etc)
- ✅ Registro de movimentações de estoque (entrada e saída)
- ✅ Controle automático de quantidade em estoque
- ✅ Validação de estoque insuficiente em saídas
- ✅ Relatório de produtos por tipo
- ✅ Relatório de lucro por produto com filtro de período
- ✅ Auditoria automática (createdAt, updatedAt)
- ✅ Documentação interativa com Swagger
- ✅ Console H2 para visualização do banco de dados

## 🚀 Primeiros Passos (Quick Start)

Para quem quer começar rapidamente:

```bash
# 1. Clone o repositório
git clone <url-do-repositorio>
cd desafio-tecnico-nexdom

# 2. Execute com Docker (mais fácil)
docker-compose up --build

# OU execute localmente
cd estoque-api
./mvnw spring-boot:run
```

Acesse:
- API: http://localhost:8080
- Swagger: http://localhost:8080/swagger-ui.html
- H2 Console: http://localhost:8080/h2-console

## 📝 Licença

Este projeto foi desenvolvido como parte de um desafio técnico para a Nexdom.

## 👥 Contato

Para dúvidas ou sugestões, entre em contato com a equipe de desenvolvimento.
