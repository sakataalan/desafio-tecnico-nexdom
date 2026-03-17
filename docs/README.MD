# Documentação Arquitetural - Sistema de Gestão de Estoque

## Visão Geral do Projeto

Este projeto consiste em um sistema completo de gestão de estoque, composto por duas aplicações principais:

- **estoque-api**: Backend REST API desenvolvido em Java com Spring Boot
- **estoque-ui**: Frontend desenvolvido em Vue.js 3

O sistema permite o gerenciamento de produtos, controle de movimentações de estoque (entradas e saídas), análise de produtos por tipo e cálculo de lucratividade.

---

## Arquitetura Geral

### Padrão Arquitetural: Cliente-Servidor com API REST

O sistema adota uma arquitetura de **separação clara entre frontend e backend**, comunicando-se através de uma API RESTful. Esta decisão proporciona:

**Vantagens:**
- Desacoplamento entre camadas de apresentação e lógica de negócio
- Possibilidade de múltiplos clientes (web, mobile, desktop) consumindo a mesma API
- Escalabilidade independente de frontend e backend
- Facilidade de manutenção e testes isolados

**Limitações:**
- Necessidade de gerenciar CORS (Cross-Origin Resource Sharing)
- Latência de rede entre cliente e servidor
- Complexidade adicional no deployment (dois serviços separados)

---

## estoque-api (Backend)

### Stack Tecnológica

- **Java 17+** com **Spring Boot 3.x**
- **Spring Data JPA** para persistência
- **PostgreSQL** como banco de dados relacional
- **Flyway** para versionamento de schema
- **Docker** para containerização
- **Maven** como gerenciador de dependências

### Decisões Arquiteturais

#### 1. Arquitetura em Camadas (Layered Architecture)

A API segue o padrão de camadas bem definidas:

```
Controllers → Services → Repositories → Entities
     ↓           ↓
   DTOs      Mappers
```

**Camadas:**
- **Controllers**: Endpoints REST, validação de entrada, serialização HTTP
- **Services**: Lógica de negócio, orquestração de operações
- **Repositories**: Acesso a dados, queries customizadas
- **Entities**: Modelos de domínio mapeados para tabelas do banco
- **DTOs (Request/Response)**: Objetos de transferência de dados
- **Mappers**: Conversão entre Entities e DTOs

**Vantagens:**
- Separação clara de responsabilidades (SRP - Single Responsibility Principle)
- Facilita testes unitários e de integração
- Código mais organizado e manutenível
- Reutilização de lógica de negócio

**Limitações:**
- Pode gerar boilerplate code (código repetitivo)
- Para operações simples, pode parecer over-engineering
- Necessidade de múltiplas conversões entre camadas (Entity ↔ DTO)

#### 2. Uso de DTOs (Data Transfer Objects)

Separação entre objetos de domínio (Entities) e objetos de transferência (DTOs):

- **ProductRequest/ProductResponse**: Para operações CRUD de produtos
- **StockMovementRequest/StockMovementResponse**: Para movimentações
- **ProductByTypeResponse**: Para consultas agregadas por tipo
- **ProductProfitResponse**: Para cálculos de lucratividade

**Vantagens:**
- Controle fino sobre o que é exposto na API
- Evita exposição de detalhes internos do modelo de domínio
- Permite diferentes representações para entrada e saída
- Facilita versionamento da API

**Limitações:**
- Duplicação de código (classes similares)
- Necessidade de mappers para conversão
- Mais classes para manter

#### 3. JPA/Hibernate para ORM

Uso do Spring Data JPA com Hibernate como provedor:

**Vantagens:**
- Abstração do SQL, reduzindo código boilerplate
- Suporte a múltiplos bancos de dados
- Lazy loading e cache de segundo nível
- Queries tipadas e type-safe

**Limitações:**
- Performance pode ser inferior a SQL nativo em queries complexas
- Curva de aprendizado para entender o ciclo de vida das entidades
- Problemas de N+1 queries se não usado corretamente
- Dificuldade em otimizar queries geradas automaticamente

#### 4. Queries Customizadas com JPQL

Para relatórios complexos (produtos por tipo, lucratividade), foram utilizadas queries JPQL customizadas no `ProductRepository`:

**Vantagens:**
- Queries otimizadas para casos específicos
- Retorno direto de DTOs (projeções)
- Melhor performance que carregar entidades completas

**Limitações:**
- Queries em string (não type-safe em tempo de compilação)
- Mais difícil de testar isoladamente
- Pode ficar complexo para joins múltiplos

#### 5. Flyway para Migrations

Versionamento de schema do banco de dados:

**Vantagens:**
- Histórico completo de mudanças no schema
- Migrations reproduzíveis em qualquer ambiente
- Rollback controlado
- Integração com CI/CD

**Limitações:**
- Migrations irreversíveis por padrão
- Necessidade de planejamento cuidadoso
- Conflitos em desenvolvimento paralelo

#### 6. Validação e Tratamento de Exceções

- Uso de `@EntityNotFoundException` para recursos não encontrados
- `InsufficientStockException` customizada para regras de negócio
- `@ResponseStatus` para mapear exceções para códigos HTTP

**Vantagens:**
- Respostas HTTP consistentes
- Separação entre exceções de negócio e técnicas
- Facilita debugging

**Limitações:**
- Falta de um handler global de exceções (@ControllerAdvice)
- Mensagens de erro poderiam ser mais estruturadas
- Sem internacionalização (i18n) de mensagens

#### 7. Auditoria com JPA Auditing

Uso de `@EntityListeners(AuditingEntityListener.class)` e `@EnableJpaAuditing`:

**Vantagens:**
- Rastreamento automático de criação/modificação
- Sem código manual para timestamps
- Consistência em todas as entidades

**Limitações:**
- Não rastreia quem fez a mudança (falta de user context)
- Não mantém histórico de versões anteriores
- Auditoria limitada (sem log de todas as operações)

#### 8. Transações com @Transactional

Uso de transações declarativas no service layer:

**Vantagens:**
- Garantia de atomicidade em operações complexas
- Rollback automático em caso de exceção
- Código mais limpo

**Limitações:**
- Pode causar problemas se não entendido corretamente (proxy, propagation)
- Transações longas podem causar locks no banco
- Dificulta testes unitários puros

---

## estoque-ui (Frontend)

### Stack Tecnológica

- **Vue.js 3** com Composition API
- **Vue Router** para roteamento
- **Pinia** para gerenciamento de estado
- **Vite** como build tool
- **Axios** para requisições HTTP
- **PrimeVue** (aparentemente) para componentes UI
- **Docker** para containerização

### Decisões Arquiteturais

#### 1. Vue.js 3 com Composition API

Uso da Composition API ao invés da Options API:

**Vantagens:**
- Melhor organização de lógica relacionada
- Reutilização de código com composables
- Melhor suporte a TypeScript (embora não usado aqui)
- Performance ligeiramente melhor

**Limitações:**
- Curva de aprendizado para desenvolvedores acostumados com Options API
- Pode ser verboso para componentes simples
- Requer disciplina para não criar componentes monolíticos

#### 2. Estrutura de Componentes

Organização em:
- **Views**: Páginas completas (ProductsView, MovementView, etc.)
- **Components**: Componentes reutilizáveis (AppLayout, AppSidebar, ProductModal)

**Vantagens:**
- Separação clara entre páginas e componentes
- Reutilização de componentes
- Facilita navegação no código

**Limitações:**
- Falta de uma camada de componentes mais granular (atoms, molecules, organisms)
- Componentes podem crescer e ficar complexos
- Sem padrão de design system estabelecido

#### 3. Centralização de API com api/index.js

Todas as chamadas HTTP centralizadas em um único arquivo:

**Vantagens:**
- Ponto único para configuração do Axios
- Facilita mudanças de endpoints
- Reutilização de configurações (baseURL, headers)

**Limitações:**
- Arquivo pode crescer muito com muitos endpoints
- Falta de organização por domínio/recurso
- Sem interceptors para tratamento global de erros
- Sem cache de requisições

#### 4. Pinia para State Management

Uso do Pinia (sucessor do Vuex):

**Vantagens:**
- API mais simples que Vuex
- Melhor suporte a TypeScript
- DevTools integrado
- Stores modulares

**Limitações:**
- Atualmente só tem um store (counter) que parece não ser usado
- Estado não está sendo aproveitado (dados buscados diretamente nos componentes)
- Sem persistência de estado (localStorage/sessionStorage)

#### 5. Vue Router para SPA

Single Page Application com roteamento client-side:

**Vantagens:**
- Navegação rápida sem reload de página
- Experiência de usuário fluida
- Facilita deep linking

**Limitações:**
- SEO mais complexo (embora não seja crítico para sistema interno)
- Tamanho inicial do bundle pode ser grande
- Necessidade de configuração no servidor para fallback de rotas

#### 6. Vite como Build Tool

Uso do Vite ao invés de Webpack:

**Vantagens:**
- Desenvolvimento extremamente rápido (HMR instantâneo)
- Build otimizado com Rollup
- Configuração mais simples

**Limitações:**
- Ecossistema de plugins menor que Webpack
- Algumas bibliotecas antigas podem ter problemas de compatibilidade

---

## Funcionalidades do Sistema

### 1. Gestão de Produtos

**Funcionalidades:**
- Criar novo produto (descrição, tipo, preço de fornecedor)
- Listar todos os produtos
- Buscar produto por ID
- Atualizar informações do produto
- Excluir produto (com validação de movimentações)
- Listar tipos de produtos disponíveis

**Regras de Negócio:**
- Produto não pode ser excluído se tiver movimentações associadas
- Quantidade em estoque é calculada com base nas movimentações

### 2. Movimentação de Estoque

**Funcionalidades:**
- Registrar entrada de estoque
- Registrar saída de estoque
- Listar todas as movimentações
- Buscar movimentação por ID
- Excluir movimentação

**Regras de Negócio:**
- Saída só é permitida se houver estoque suficiente
- Movimentações atualizam automaticamente a quantidade em estoque do produto
- Cada movimentação registra: tipo, quantidade, preço de venda (para saídas), data

### 3. Relatórios e Análises

**Funcionalidades:**
- Produtos agrupados por tipo com totais de movimentação
- Cálculo de lucratividade por produto em período específico
- Histórico completo de movimentações

**Cálculos:**
- Lucro = (Preço de Venda - Preço de Fornecedor) × Quantidade Vendida
- Agregações por período (data início/fim)

---

## Limitações Arquiteturais e Técnicas

### Backend (estoque-api)

1. **Ausência de Autenticação/Autorização**
   - Sistema completamente aberto
   - Sem controle de acesso por usuário ou role
   - Não há rastreamento de quem fez cada operação

2. **Falta de Paginação**
   - Endpoints `getAll()` retornam todos os registros
   - Pode causar problemas de performance com muitos dados
   - Alto consumo de memória e banda

3. **Validação Limitada**
   - Falta de validações com Bean Validation (@Valid, @NotNull, etc.)
   - Validações de regras de negócio poderiam ser mais robustas
   - Sem validação de formato de dados (ex: preços negativos)

4. **Tratamento de Erros Básico**
   - Sem handler global de exceções
   - Mensagens de erro não padronizadas
   - Falta de códigos de erro específicos para o cliente

5. **Ausência de Cache**
   - Queries repetidas executam sempre no banco
   - Sem cache de segundo nível do Hibernate configurado
   - Endpoints de leitura poderiam se beneficiar de cache

6. **Falta de Testes**
   - Apenas teste de contexto básico
   - Sem testes unitários de services
   - Sem testes de integração de controllers
   - Sem testes de repositories

7. **Logging Insuficiente**
   - Sem logs estruturados
   - Dificulta debugging em produção
   - Sem rastreamento de requisições (correlation ID)

8. **Soft Delete Não Implementado**
   - Exclusões são físicas (DELETE)
   - Perda de histórico
   - Impossível recuperar dados excluídos acidentalmente

9. **Concorrência Não Tratada**
   - Sem controle de versão otimista (@Version)
   - Possíveis race conditions em movimentações simultâneas
   - Risco de estoque negativo em alta concorrência

10. **API Não Versionada**
    - Mudanças quebram compatibilidade
    - Dificulta evolução da API
    - Sem estratégia de deprecação

### Frontend (estoque-ui)

1. **Gerenciamento de Estado Subutilizado**
   - Pinia instalado mas não usado efetivamente
   - Dados buscados repetidamente em cada componente
   - Sem cache de dados no cliente

2. **Tratamento de Erros Inconsistente**
   - Sem interceptor global para erros HTTP
   - Tratamento de erro em cada componente (duplicação)
   - Feedback ao usuário pode ser inconsistente

3. **Sem Loading States Globais**
   - Cada componente gerencia seu próprio loading
   - Experiência de usuário pode ser inconsistente
   - Sem indicador global de requisições pendentes

4. **Validação de Formulários Limitada**
   - Validações básicas ou ausentes
   - Sem feedback visual consistente
   - Possível envio de dados inválidos para API

5. **Sem Testes**
   - Apenas teste de exemplo
   - Sem testes de componentes
   - Sem testes E2E
   - Dificulta refatoração com confiança

6. **Responsividade Não Garantida**
   - Sem evidência de design responsivo
   - Pode não funcionar bem em mobile
   - Sem breakpoints definidos

7. **Acessibilidade Não Considerada**
   - Sem atributos ARIA
   - Navegação por teclado pode não funcionar
   - Sem suporte a leitores de tela

8. **Internacionalização Ausente**
   - Textos hardcoded em português
   - Impossível suportar múltiplos idiomas
   - Datas/números sem formatação localizada

9. **Performance Não Otimizada**
   - Sem lazy loading de rotas
   - Sem code splitting
   - Bundle pode ser grande
   - Sem otimização de imagens

10. **Sem Offline Support**
    - Aplicação não funciona sem internet
    - Sem Service Workers
    - Sem estratégia de cache

### Infraestrutura e DevOps

1. **Configurações Hardcoded**
   - URLs de API podem estar fixas
   - Sem variáveis de ambiente adequadas
   - Dificulta deployment em múltiplos ambientes

2. **Sem CI/CD**
   - Builds manuais
   - Sem testes automatizados no pipeline
   - Sem deployment automatizado

3. **Monitoramento Ausente**
   - Sem métricas de aplicação
   - Sem alertas
   - Sem APM (Application Performance Monitoring)

4. **Backup Não Definido**
   - Sem estratégia de backup do banco
   - Risco de perda de dados
   - Sem plano de disaster recovery

5. **Escalabilidade Limitada**
   - Aplicação stateful (sessões, se houver)
   - Sem load balancing configurado
   - Banco de dados single point of failure

---

## Recomendações para Evolução

### Curto Prazo (Quick Wins)

1. Implementar paginação nos endpoints de listagem
2. Adicionar validações com Bean Validation
3. Criar handler global de exceções
4. Implementar loading states no frontend
5. Adicionar logs estruturados

### Médio Prazo

1. Implementar autenticação e autorização (Spring Security + JWT)
2. Adicionar testes unitários e de integração
3. Implementar cache (Redis)
4. Versionar a API
5. Melhorar tratamento de erros no frontend
6. Utilizar Pinia efetivamente para state management

### Longo Prazo

1. Implementar soft delete e auditoria completa
2. Adicionar monitoramento e observabilidade (Prometheus, Grafana)
3. Implementar CI/CD completo
4. Adicionar suporte a múltiplos idiomas
5. Otimizar performance (lazy loading, code splitting)
6. Implementar testes E2E
7. Considerar migração para arquitetura de microserviços se necessário

---

## Conclusão

O sistema apresenta uma arquitetura sólida e bem estruturada para um MVP (Minimum Viable Product), com separação clara de responsabilidades e uso de tecnologias modernas e consolidadas. As decisões arquiteturais tomadas favorecem manutenibilidade e clareza de código.

No entanto, para um sistema de produção, seria necessário endereçar as limitações identificadas, especialmente em áreas críticas como segurança, testes, performance e observabilidade. A arquitetura atual fornece uma base sólida para evolução incremental do sistema.
