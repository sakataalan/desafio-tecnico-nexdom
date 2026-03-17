# Sistema de Gestão de Estoque - Nexdom

Sistema completo de gestão de estoque desenvolvido como desafio técnico para a Nexdom, composto por uma API REST em Java/Spring Boot e uma interface web moderna em Vue.js 3.

## 📋 Índice

- [Visão Geral](#visão-geral)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Arquitetura do Sistema](#arquitetura-do-sistema)
- [Pré-requisitos](#pré-requisitos)
- [Instalação e Configuração](#instalação-e-configuração)
- [Como Executar](#como-executar)
- [Funcionalidades](#funcionalidades)
- [Documentação da API](#documentação-da-api)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Testes](#testes)
- [Docker](#docker)
- [Troubleshooting](#troubleshooting)
- [Documentação Técnica](#documentação-técnica)
- [Contribuição](#contribuição)

## 🎯 Visão Geral

Este projeto consiste em um sistema completo de gestão de estoque com as seguintes características:

### Backend (estoque-api)
- API RESTful desenvolvida em Java 21 com Spring Boot
- Banco de dados H2 em memória com migrations Flyway
- Documentação interativa com Swagger/OpenAPI
- Validações de negócio e tratamento de exceções

### Frontend (estoque-ui)
- Interface web moderna desenvolvida em Vue.js 3
- Design responsivo com Tailwind CSS
- Ícones modernos com Lucide Vue Next
- Comunicação com API via Axios

### Funcionalidades Principais
- ✅ CRUD completo de produtos
- ✅ Controle de movimentações de estoque (entrada/saída)
- ✅ Validação de estoque insuficiente
- ✅ Relatórios de produtos por tipo
- ✅ Cálculo de lucros por produto com filtros de período
- ✅ Histórico completo de movimentações
- ✅ Interface intuitiva e responsiva

## 🚀 Tecnologias Utilizadas

### Backend (estoque-api)
- **Java 21** - Linguagem de programação
- **Spring Boot 4.0.3** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Spring Web MVC** - API REST
- **H2 Database** - Banco de dados em memória
- **Flyway** - Versionamento do banco de dados
- **Lombok** - Redução de código boilerplate
- **SpringDoc OpenAPI** - Documentação da API
- **Maven** - Gerenciador de dependências

### Frontend (estoque-ui)
- **Vue.js 3.5.29** - Framework JavaScript progressivo
  - Composition API - API moderna do Vue
  - Vue Router 5.0.3 - Roteamento
  - Pinia 3.0.4 - Gerenciamento de estado
- **Tailwind CSS 4.2.1** - Framework CSS utility-first
- **Lucide Vue Next 0.577.0** - Biblioteca de ícones SVG
- **Axios 1.13.6** - Cliente HTTP
- **Vite 7.3.1** - Build tool e dev server
- **Vitest 4.0.18** - Framework de testes
- **ESLint 10.0.2** - Linter
- **Prettier 3.8.1** - Formatador de código

### DevOps
- **Docker** - Containerização
- **Docker Compose** - Orquestração de containers

## 🏗️ Arquitetura do Sistema

O sistema adota uma arquitetura de **separação clara entre frontend e backend**, comunicando-se através de uma API RESTful.

```
┌─────────────────────────────────────────────────────────────┐
│                        Cliente (Browser)                     │
│                     estoque-ui (Vue.js 3)                    │
│                    http://localhost:5173                     │
└──────────────────────────┬──────────────────────────────────┘
                           │ HTTP/REST
                           │ (Axios)
┌──────────────────────────▼──────────────────────────────────┐
│                    estoque-api (Spring Boot)                 │
│                    http://localhost:8080                     │
│  ┌────────────┐  ┌────────────┐  ┌────────────────────┐    │
│  │Controllers │→ │  Services  │→ │    Repositories    │    │
│  └────────────┘  └────────────┘  └──────────┬─────────┘    │
│                                              │               │
│  ┌────────────┐  ┌────────────┐  ┌──────────▼─────────┐    │
│  │    DTOs    │  │  Mappers   │  │   H2 Database      │    │
│  └────────────┘  └────────────┘  └────────────────────┘    │
└─────────────────────────────────────────────────────────────┘
```

### Padrão Arquitetural Backend: Arquitetura em Camadas

```
Controllers → Services → Repositories → Entities
     ↓           ↓
   DTOs      Mappers
```

**Vantagens:**
- Desacoplamento entre camadas de apresentação e lógica de negócio
- Possibilidade de múltiplos clientes consumindo a mesma API
- Escalabilidade independente de frontend e backend
- Facilidade de manutenção e testes isolados
- Separação clara de responsabilidades (SRP)

Para mais detalhes sobre decisões arquiteturais, consulte a [Documentação Arquitetural](docs/README.MD).

## 📦 Pré-requisitos

### Para Execução com Docker (Recomendado)
- **Docker** - Versão 20.10 ou superior
- **Docker Compose** - Versão 1.29 ou superior

### Para Execução Local

#### Backend
- **Java 21** - JDK 21 ou superior
- **Maven 3.6+** (ou usar o Maven Wrapper incluído)

#### Frontend
- **Node.js** - Versão 20.19.0 ou superior, ou versão 22.12.0 ou superior
- **npm** - Versão 10.x.x ou superior

### Instalação dos Pré-requisitos

#### Instalando Docker

**Windows/macOS:**
- Baixe o Docker Desktop em: https://www.docker.com/products/docker-desktop

**Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install docker.io docker-compose -y
sudo systemctl start docker
sudo systemctl enable docker
sudo usermod -aG docker $USER
```

**Verificar instalação:**
```bash
docker --version
docker-compose --version
```

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
sudo apt install openjdk-21-jdk -y

# Verifique a instalação
java -version
```

#### Instalando Node.js e npm

**Linux (Ubuntu/Debian):**
```bash
# Instalar Node.js 20 LTS (recomendado)
curl -fsSL https://deb.nodesource.com/setup_20.x | sudo -E bash -
sudo apt-get install -y nodejs

# Verificar instalação
node --version  # Deve mostrar v20.19.0 ou superior
npm --version   # Deve mostrar 10.x.x ou superior
```

**Alternativa - Usando nvm (Node Version Manager):**
```bash
# Instalar nvm
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.0/install.sh | bash

# Recarregar o shell
source ~/.bashrc

# Instalar Node.js 20
nvm install 20
nvm use 20

# Verificar instalação
node --version
npm --version
```

**Windows/macOS:**
- Baixe o instalador em: https://nodejs.org/

## 🔧 Instalação e Configuração

### 1. Clonar o Repositório

```bash
git clone <url-do-repositorio>
cd desafio-tecnico-nexdom
```

### 2. Configuração do Backend (estoque-api)

```bash
cd estoque-api

# Não é necessário instalar dependências manualmente
# O Maven Wrapper fará isso automaticamente
```

### 3. Configuração do Frontend (estoque-ui)

```bash
cd estoque-ui

# Instalar dependências
npm install
```

**Tempo estimado:** 1-3 minutos dependendo da sua conexão com a internet.

### 4. Configuração da API no Frontend

A aplicação está configurada para se conectar à API em `http://localhost:8080`.

Para alterar a URL da API, edite o arquivo `estoque-ui/src/api/index.js`:

```javascript
const api = axios.create({
  baseURL: 'http://localhost:8080'  // Altere aqui se necessário
})
```

**Ou use variável de ambiente:**

Crie um arquivo `.env` em `estoque-ui/`:

```bash
VITE_API_URL=http://localhost:8080
```

E atualize `src/api/index.js`:

```javascript
const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8080'
})
```

## 🏃 Como Executar

### 🚀 Opção 1: Usando Docker Compose (Recomendado)

Esta é a forma mais simples de executar a aplicação completa.

```bash
# Na raiz do projeto
docker-compose up --build
```

A aplicação estará disponível em:
- **Frontend (UI):** http://localhost:5173
- **Backend (API):** http://localhost:8080
- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **H2 Console:** http://localhost:8080/h2-console

Para parar a aplicação:
```bash
docker-compose down
```

### 💻 Opção 2: Execução Local (Desenvolvimento)

#### Passo 1: Iniciar o Backend

```bash
# Em um terminal, na pasta estoque-api
cd estoque-api

# Windows
./mvnw spring-boot:run

# Linux/macOS
./mvnw spring-boot:run
```

A API estará disponível em: http://localhost:8080

#### Passo 2: Iniciar o Frontend

```bash
# Em outro terminal, na pasta estoque-ui
cd estoque-ui

npm run dev
```

A interface estará disponível em: http://localhost:5173

**Características do modo desenvolvimento:**
- ✅ Hot Module Replacement (HMR) - Atualizações instantâneas
- ✅ Source maps para debugging
- ✅ Mensagens de erro detalhadas
- ✅ Servidor de desenvolvimento rápido

### 📦 Opção 3: Build de Produção

#### Backend

```bash
cd estoque-api

# Compilar o projeto
./mvnw clean package

# Executar o JAR gerado
java -jar target/estoque-api-0.0.1-SNAPSHOT.jar
```

#### Frontend

```bash
cd estoque-ui

# Compilar para produção
npm run build

# Visualizar o build localmente
npm run preview
```

Os arquivos estáticos estarão em `estoque-ui/dist/`

## 🎯 Funcionalidades

### 1. Gestão de Produtos

**Funcionalidades:**
- ✅ Criar novo produto (descrição, tipo, preço de fornecedor)
- ✅ Listar todos os produtos com tabela interativa
- ✅ Buscar produto por ID
- ✅ Atualizar informações do produto
- ✅ Excluir produto (com validação de movimentações)
- ✅ Listar tipos de produtos disponíveis
- ✅ Controle de estoque em tempo real

**Regras de Negócio:**
- Produto não pode ser excluído se tiver movimentações associadas
- Quantidade em estoque é calculada automaticamente com base nas movimentações

### 2. Movimentações de Estoque

**Funcionalidades:**
- ✅ Registrar entrada de estoque
- ✅ Registrar saída de estoque com preço de venda
- ✅ Listar todas as movimentações
- ✅ Buscar movimentação por ID
- ✅ Excluir movimentação
- ✅ Histórico completo de movimentações
- ✅ Filtros por data e tipo de movimentação

**Regras de Negócio:**
- Saída só é permitida se houver estoque suficiente
- Movimentações atualizam automaticamente a quantidade em estoque do produto
- Cada movimentação registra: tipo, quantidade, preço de venda (para saídas), data

### 3. Relatórios e Análises

**Funcionalidades:**
- ✅ Produtos agrupados por tipo com totais de movimentação
- ✅ Cálculo de lucratividade por produto em período específico
- ✅ Visualização de métricas e estatísticas
- ✅ Volume de saída por produto
- ✅ Filtros por período de data

**Cálculos:**
- Lucro = (Preço de Venda - Preço de Fornecedor) × Quantidade Vendida
- Agregações por período (data início/fim)

### 4. Interface do Usuário

- ✅ Design responsivo (mobile, tablet, desktop)
- ✅ Tema moderno com Tailwind CSS
- ✅ Navegação intuitiva com sidebar
- ✅ Ícones modernos com Lucide
- ✅ Feedback visual de ações (alerts, confirmações)
- ✅ Validação de formulários
- ✅ Loading states e estados vazios

## 📚 Documentação da API

### Swagger UI

Acesse a documentação interativa da API em: http://localhost:8080/swagger-ui.html

### Endpoints Principais

#### Produtos
- `GET /products` - Lista todos os produtos
- `GET /products/{id}` - Busca produto por ID
- `POST /products` - Cria novo produto
- `PUT /products` - Atualiza produto
- `DELETE /products/{id}` - Remove produto
- `GET /products/types` - Lista tipos de produtos
- `GET /products/by-type/{type}` - Lista produtos por tipo
- `GET /products/profit` - Calcula lucro por produto

#### Movimentações de Estoque
- `GET /stock-movement` - Lista todas as movimentações
- `GET /stock-movement/{id}` - Busca movimentação por ID
- `POST /stock-movement` - Registra nova movimentação
- `DELETE /stock-movement/{id}` - Remove movimentação

### Exemplos de Requisições

#### Criar um Produto
```bash
curl -X POST http://localhost:8080/products \
  -H "Content-Type: application/json" \
  -d '{
    "description": "Notebook Dell",
    "productType": "ELETRONICOS",
    "supplierPrice": 2500.00,
    "stockQuantity": 10
  }'
```

#### Registrar Movimentação de Entrada
```bash
curl -X POST http://localhost:8080/stock-movement \
  -H "Content-Type: application/json" \
  -d '{
    "productId": 1,
    "movementType": "ENTRADA",
    "movementQuantity": 5,
    "movementDate": "2024-03-16T10:00:00"
  }'
```

#### Registrar Movimentação de Saída
```bash
curl -X POST http://localhost:8080/stock-movement \
  -H "Content-Type: application/json" \
  -d '{
    "productId": 1,
    "movementType": "SAIDA",
    "movementQuantity": 2,
    "salePrice": 3000.00,
    "movementDate": "2024-03-16T14:00:00"
  }'
```

## 📁 Estrutura do Projeto

```
desafio-tecnico-nexdom/
├── estoque-api/                    # Backend Spring Boot
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/br/com/nexdom/estoque_api/
│   │   │   │   ├── controllers/   # Endpoints REST
│   │   │   │   ├── services/      # Lógica de negócio
│   │   │   │   ├── repositories/  # Acesso a dados
│   │   │   │   ├── entities/      # Modelos JPA
│   │   │   │   ├── dto/           # Data Transfer Objects
│   │   │   │   ├── mappers/       # Conversão Entity ↔ DTO
│   │   │   │   ├── enums/         # Enumerações
│   │   │   │   ├── exceptions/    # Exceções customizadas
│   │   │   │   └── configs/       # Configurações
│   │   │   └── resources/
│   │   │       ├── application.yml
│   │   │       └── db/migration/  # Scripts Flyway
│   │   └── test/                  # Testes
│   ├── Dockerfile
│   ├── pom.xml
│   └── README.md
│
├── estoque-ui/                     # Frontend Vue.js
│   ├── src/
│   │   ├── api/                   # Configuração Axios
│   │   ├── assets/                # CSS, imagens
│   │   ├── components/            # Componentes reutilizáveis
│   │   │   ├── AppLayout.vue
│   │   │   ├── AppSidebar.vue
│   │   │   └── ProductModal.vue
│   │   ├── router/                # Configuração de rotas
│   │   ├── stores/                # Stores Pinia
│   │   ├── views/                 # Páginas/Views
│   │   │   ├── ProductsView.vue
│   │   │   ├── MovementView.vue
│   │   │   ├── MovementHistView.vue
│   │   │   ├── ProductsByTypeView.vue
│   │   │   └── ProfitView.vue
│   │   ├── App.vue
│   │   └── main.js
│   ├── public/
│   ├── Dockerfile
│   ├── package.json
│   ├── vite.config.js
│   └── README.md
│
├── docs/                           # Documentação arquitetural
│   └── README.MD
│
├── docker-compose.yml              # Orquestração Docker
└── README.md                       # Este arquivo
```

## 🧪 Testes

### Backend

```bash
cd estoque-api

# Executar todos os testes
./mvnw test

# Executar testes com relatório de cobertura
./mvnw test jacoco:report
```

### Frontend

```bash
cd estoque-ui

# Executar todos os testes
npm run test:unit

# Executar testes em modo watch
npm run test:unit -- --watch

# Executar testes com cobertura
npm run test:unit -- --coverage
```

### Linting e Formatação

```bash
cd estoque-ui

# Verificar e corrigir problemas de lint
npm run lint

# Formatar código automaticamente
npm run format
```

## 🐳 Docker

### Estrutura Docker

O projeto inclui:
- **Dockerfile** para backend (multi-stage build)
- **Dockerfile** para frontend
- **docker-compose.yml** para orquestração

### Comandos Docker Úteis

```bash
# Construir e iniciar todos os serviços
docker-compose up --build

# Iniciar em background
docker-compose up -d

# Ver logs
docker-compose logs -f

# Ver logs de um serviço específico
docker-compose logs -f estoque-api
docker-compose logs -f estoque-ui

# Parar todos os serviços
docker-compose down

# Parar e remover volumes
docker-compose down -v

# Reconstruir apenas um serviço
docker-compose up --build estoque-api
```

### Executar Serviços Individualmente

**Backend:**
```bash
cd estoque-api
docker build -t estoque-api .
docker run -p 8080:8080 estoque-api
```

**Frontend:**
```bash
cd estoque-ui
docker build -t estoque-ui .
docker run -p 5173:5173 estoque-ui
```

## 🔍 Banco de Dados

### H2 Database Console

Acesse o console do H2 em: http://localhost:8080/h2-console

**Credenciais:**
- **URL JDBC**: `jdbc:h2:mem:estoqueDB`
- **Username**: `sa`
- **Password**: (deixe em branco)

### Estrutura das Tabelas

**products:**
- `id` (BIGINT, PK)
- `description` (VARCHAR)
- `product_type` (VARCHAR)
- `supplier_price` (DECIMAL)
- `stock_quantity` (INTEGER)
- `created_at` (TIMESTAMP)
- `updated_at` (TIMESTAMP)

**stock_movements:**
- `id` (BIGINT, PK)
- `product_id` (BIGINT, FK)
- `movement_type` (VARCHAR) - ENTRADA/SAIDA
- `movement_quantity` (INTEGER)
- `sale_price` (DECIMAL)
- `movement_date` (TIMESTAMP)
- `created_at` (TIMESTAMP)
- `updated_at` (TIMESTAMP)

### Dados Iniciais

O banco é populado automaticamente com dados de exemplo via Flyway migrations ao iniciar a aplicação.

## 🚨 Troubleshooting

### Problemas Comuns

#### 1. Porta 8080 em uso (Backend)

```bash
# Encontrar processo usando a porta
lsof -ti:8080

# Matar o processo
kill -9 $(lsof -ti:8080)

# Ou executar em outra porta
./mvnw spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
```

#### 2. Porta 5173 em uso (Frontend)

```bash
# Matar o processo
kill -9 $(lsof -ti:5173)

# Ou usar outra porta
npm run dev -- --port 3000
```

#### 3. Java não encontrado

```bash
# Verificar instalação
java -version

# Verificar JAVA_HOME
echo $JAVA_HOME

# Configurar JAVA_HOME (Linux/macOS)
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
```

#### 4. Node.js/npm não encontrado

```bash
# Verificar instalação
node --version
npm --version

# Reinstalar dependências
rm -rf node_modules package-lock.json
npm install
```

#### 5. Erro de permissão no Maven Wrapper (Linux/macOS)

```bash
chmod +x mvnw
```

#### 6. API não responde / CORS Error

Certifique-se de que:
1. A API está rodando em `http://localhost:8080`
2. A configuração de CORS na API permite requisições de `http://localhost:5173`
3. Verifique o console do navegador para erros específicos

```bash
# Testar se a API está respondendo
curl http://localhost:8080/products
```

#### 7. Build falha com erro de memória

```bash
# Backend - Aumentar memória da JVM
export MAVEN_OPTS="-Xmx1024m"
./mvnw spring-boot:run

# Frontend - Aumentar limite de memória do Node.js
NODE_OPTIONS=--max_old_space_size=4096 npm run build
```

#### 8. Hot reload não funciona (Frontend)

```bash
# Parar o servidor (Ctrl+C)
# Limpar cache do Vite
rm -rf node_modules/.vite

# Reiniciar
npm run dev
```

#### 9. Docker: "Cannot connect to the Docker daemon"

```bash
# Iniciar Docker
sudo systemctl start docker

# Adicionar usuário ao grupo docker
sudo usermod -aG docker $USER

# Fazer logout e login novamente
```

#### 10. Erro "EACCES: permission denied" (Frontend)

```bash
# Corrigir permissões do npm
sudo chown -R $USER:$USER ~/.npm
sudo chown -R $USER:$USER node_modules
```

## 📖 Documentação Técnica

### Documentação Arquitetural Completa

Para uma análise detalhada das decisões arquiteturais, padrões utilizados, limitações e recomendações de evolução, consulte:

📄 [Documentação Arquitetural Completa](docs/README.MD)

Este documento inclui:
- Decisões arquiteturais detalhadas
- Vantagens e limitações de cada escolha
- Análise de padrões utilizados
- Recomendações para evolução do sistema
- Boas práticas e convenções

### Links de Documentação das Tecnologias

#### Backend
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Spring Data JPA Documentation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Spring Web MVC Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html)
- [H2 Database Documentation](http://www.h2database.com/html/main.html)
- [Flyway Documentation](https://flywaydb.org/documentation/)
- [Lombok Documentation](https://projectlombok.org/features/all)
- [SpringDoc OpenAPI Documentation](https://springdoc.org/)
- [Maven Documentation](https://maven.apache.org/guides/)

#### Frontend
- [Vue.js 3 Documentation](https://vuejs.org/guide/introduction.html)
  - [Composition API](https://vuejs.org/guide/extras/composition-api-faq.html)
  - [Vue Router](https://router.vuejs.org/)
  - [Pinia](https://pinia.vuejs.org/)
- [Tailwind CSS Documentation](https://tailwindcss.com/docs)
- [Lucide Vue Next](https://lucide.dev/guide/packages/lucide-vue-next)
- [Vite Documentation](https://vitejs.dev/guide/)
- [Vitest Documentation](https://vitest.dev/guide/)
- [Axios Documentation](https://axios-http.com/docs/intro)
- [ESLint Documentation](https://eslint.org/docs/latest/)
- [Prettier Documentation](https://prettier.io/docs/en/)

#### DevOps
- [Docker Documentation](https://docs.docker.com/)
- [Docker Compose Documentation](https://docs.docker.com/compose/)

## 🚀 Quick Start (Início Rápido)

Para quem quer começar rapidamente:

```bash
# 1. Clone o repositório
git clone <url-do-repositorio>
cd desafio-tecnico-nexdom

# 2. Execute com Docker Compose (mais fácil)
docker-compose up --build
```

**OU**

```bash
# 2. Execute localmente

# Terminal 1 - Backend
cd estoque-api
./mvnw spring-boot:run

# Terminal 2 - Frontend
cd estoque-ui
npm install
npm run dev
```

**Acesse:**
- **Interface:** http://localhost:5173
- **API:** http://localhost:8080
- **Swagger:** http://localhost:8080/swagger-ui.html
- **H2 Console:** http://localhost:8080/h2-console

## 🔄 Workflow de Desenvolvimento

### 1. Desenvolvimento Local

```bash
# Terminal 1 - API
cd estoque-api
./mvnw spring-boot:run

# Terminal 2 - UI
cd estoque-ui
npm run dev
```

### 2. Antes de Commitar

```bash
# Backend
cd estoque-api
./mvnw test

# Frontend
cd estoque-ui
npm run lint
npm run test:unit
npm run format
```

### 3. Build de Produção

```bash
# Backend
cd estoque-api
./mvnw clean package

# Frontend
cd estoque-ui
npm run build
npm run preview
```

## 📝 Convenções de Código

### Backend (Java)

- **Nomenclatura de Classes**: PascalCase (`ProductService`, `ProductController`)
- **Nomenclatura de Métodos**: camelCase (`getById`, `createProduct`)
- **Nomenclatura de Constantes**: UPPER_SNAKE_CASE (`MAX_QUANTITY`)
- **Pacotes**: lowercase (`br.com.nexdom.estoque_api.controllers`)

### Frontend (Vue.js)

- **Componentes**: PascalCase (`ProductModal.vue`, `AppSidebar.vue`)
- **Uso em templates**: kebab-case (`<product-modal />`, `<app-sidebar />`)
- **Métodos e variáveis**: camelCase (`fetchProducts`, `isLoading`)
- **Constantes**: UPPER_SNAKE_CASE (`API_BASE_URL`)

### Estrutura de Componentes Vue

```vue
<script setup>
// 1. Imports
// 2. Props e Emits
// 3. Refs e Reactive
// 4. Computed
// 5. Methods
// 6. Lifecycle hooks
// 7. Watchers
</script>

<template>
  <!-- Template HTML -->
</template>

<style scoped>
/* Estilos do componente (se necessário) */
/* Prefira usar classes Tailwind no template */
</style>
```

