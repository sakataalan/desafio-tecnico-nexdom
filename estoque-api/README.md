# Estoque API

API RESTful para controle de estoque desenvolvida como teste técnico para a Nexdom.

## 📋 Sobre o Projeto

Esta é uma aplicação Spring Boot que oferece funcionalidades completas para gerenciamento de estoque de produtos, incluindo:

- ✅ CRUD completo de produtos
- ✅ Controle de movimentações de estoque (entrada/saída)
- ✅ Relatórios de produtos por tipo
- ✅ Cálculo de lucros por produto
- ✅ Validações de estoque insuficiente
- ✅ Documentação interativa com Swagger/OpenAPI

## 🛠️ Tecnologias Utilizadas

- **Java 21** - Linguagem de programação
- **Spring Boot 4.0.3** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Spring Web MVC** - API REST
- **H2 Database** - Banco de dados em memória
- **Flyway** - Versionamento do banco de dados
- **Lombok** - Redução de código boilerplate
- **SpringDoc OpenAPI** - Documentação da API
- **Maven** - Gerenciador de dependências
- **Docker** - Containerização

## 📋 Pré-requisitos

Para rodar a aplicação, você precisa ter instalado:

### Opção 1: Execução Local
- Java 21
- Maven 3.6+ (ou usar o Maven Wrapper incluído)

### Opção 2: Execução via Docker
- Docker
- Docker Compose (opcional)

## 🚀 Como Instalar e Executar

### 📦 Instalação das Dependências

#### Instalando Java 21

**Windows:**
1. Baixe o OpenJDK 21 em: https://adoptium.net/
2. Execute o instalador e siga as instruções
3. Verifique a instalação:
```cmd
java -version
```

**macOS:**
```bash
# Usando Homebrew
brew install openjdk@21

# Adicione ao PATH
echo 'export PATH="/usr/local/opt/openjdk@21/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
```

**Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install openjdk-21-jdk

# Verifique a instalação
java -version
```

#### Instalando Maven (Opcional)

**Windows:**
1. Baixe em: https://maven.apache.org/download.cgi
2. Extraia para `C:\Program Files\Apache\maven`
3. Adicione `C:\Program Files\Apache\maven\bin` ao PATH

**macOS:**
```bash
brew install maven
```

**Linux:**
```bash
sudo apt install maven
```

**Nota:** O projeto inclui o Maven Wrapper, então não é necessário instalar o Maven separadamente.

#### Instalando Docker (Para execução via container)

**Windows/macOS:**
- Baixe o Docker Desktop em: https://www.docker.com/products/docker-desktop

**Linux (Ubuntu):**
```bash
sudo apt update
sudo apt install docker.io docker-compose
sudo systemctl start docker
sudo systemctl enable docker
sudo usermod -aG docker $USER
```

### 🏃‍♂️ Executando a Aplicação

#### Método 1: Execução Local com Maven Wrapper (Recomendado)

1. Clone o repositório:
```bash
git clone <url-do-repositorio>
cd estoque-api
```

2. Execute a aplicação:
```bash
# Windows
./mvnw.cmd spring-boot:run

# Linux/macOS
./mvnw spring-boot:run
```

#### Método 2: Execução Local com Maven Instalado

```bash
mvn clean install
mvn spring-boot:run
```

#### Método 3: Executando o JAR

```bash
# Compile o projeto
./mvnw clean package

# Execute o JAR gerado
java -jar target/estoque-api-0.0.1-SNAPSHOT.jar
```

#### Método 4: Execução via Docker

```bash
# Construa a imagem
docker build -t estoque-api .

# Execute o container
docker run -p 8080:8080 estoque-api
```

## 🌐 Acessando a Aplicação

Após iniciar a aplicação, ela estará disponível em:

- **API REST**: http://localhost:8080
- **Documentação Swagger**: http://localhost:8080/swagger-ui.html
- **Console H2 Database**: http://localhost:8080/h2-console
  - **URL JDBC**: `jdbc:h2:mem:estoqueDB`
  - **Username**: `sa`
  - **Password**: (deixe em branco)

## 📚 Documentação da API

### Endpoints Principais

#### Produtos
- `GET /api/products` - Lista todos os produtos
- `GET /api/products/{id}` - Busca produto por ID
- `POST /api/products` - Cria novo produto
- `PUT /api/products/{id}` - Atualiza produto
- `DELETE /api/products/{id}` - Remove produto
- `GET /api/products/by-type` - Lista produtos agrupados por tipo
- `GET /api/products/{id}/profit` - Calcula lucro de um produto

#### Movimentações de Estoque
- `GET /api/stock-movements` - Lista todas as movimentações
- `GET /api/stock-movements/{id}` - Busca movimentação por ID
- `POST /api/stock-movements` - Registra nova movimentação

### Exemplo de Requisições

#### Criar um Produto
```json
POST /api/products
Content-Type: application/json

{
  "name": "Produto Teste",
  "description": "Descrição do produto",
  "quantity": 100,
  "unitPrice": 29.90,
  "type": "ELECTRONICS"
}
```

#### Registrar Movimentação de Estoque
```json
POST /api/stock-movements
Content-Type: application/json

{
  "productId": 1,
  "quantity": 10,
  "movementType": "IN"
}
```

## 🧪 Executando os Testes

```bash
# Executar todos os testes
./mvnw test

# Executar testes com relatório de cobertura
./mvnw test jacoco:report
```

## 📊 Banco de Dados

A aplicação utiliza H2 Database em memória com dados iniciais carregados automaticamente via Flyway migrations.

### Estrutura das Tabelas

- **products**: Armazena informações dos produtos
- **stock_movements**: Registra movimentações de entrada/saída

### Dados Iniciais

O banco é populado automaticamente com produtos de exemplo ao iniciar a aplicação.

## 🐳 Docker

### Dockerfile

O projeto inclui um Dockerfile multi-stage para otimizar a imagem:
- Stage 1: Compila a aplicação usando Maven
- Stage 2: Executa a aplicação com JRE mínima

### Comandos Docker Úteis

```bash
# Construir imagem
docker build -t estoque-api .

# Executar container
docker run -p 8080:8080 estoque-api

# Executar em background
docker run -d -p 8080:8080 --name estoque-api-container estoque-api

# Ver logs
docker logs estoque-api-container

# Parar container
docker stop estoque-api-container

# Remover container
docker rm estoque-api-container
```

## 📖 Links da Documentação

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Spring Data JPA Documentation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Spring Web MVC Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html)
- [H2 Database Documentation](http://www.h2database.com/html/main.html)
- [Flyway Documentation](https://flywaydb.org/documentation/)
- [Lombok Documentation](https://projectlombok.org/features/all)
- [SpringDoc OpenAPI Documentation](https://springdoc.org/)
- [Maven Documentation](https://maven.apache.org/guides/)
- [Docker Documentation](https://docs.docker.com/)

## 🔧 Configurações Avançadas

### Variáveis de Ambiente

Você pode configurar a aplicação usando variáveis de ambiente:

```bash
# Porta da aplicação (padrão: 8080)
export SERVER_PORT=8080

# Perfil ativo (padrão: default)
export SPRING_PROFILES_ACTIVE=dev
```

### Profiles

- **default**: Configuração padrão com H2 em memória
- **dev**: Configuração para desenvolvimento (pode ser customizada)
- **prod**: Configuração para produção (pode ser customizada)

## 🚨 Troubleshooting

### Problemas Comuns

1. **Porta 8080 em uso**:
   ```bash
   # Execute em outra porta
   ./mvnw spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
   ```

2. **Java não encontrado**:
   - Verifique se o Java 21 está instalado: `java -version`
   - Verifique se o JAVA_HOME está configurado

3. **Erro de permissão no Maven Wrapper (Linux/macOS)**:
   ```bash
   chmod +x mvnw
   ```

4. **Out of Memory Error**:
   ```bash
   # Aumentar memória da JVM
   export MAVEN_OPTS="-Xmx1024m"
   ./mvnw spring-boot:run
   ```

## 🤝 Contribuição

Para contribuir com o projeto:

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto foi desenvolvido como teste técnico para a Nexdom.

---

**Desenvolvido por [Seu Nome]** - Teste Técnico Nexdom