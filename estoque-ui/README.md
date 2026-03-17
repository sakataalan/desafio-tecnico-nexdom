# Estoque UI - Interface de Gerenciamento de Estoque

Interface web moderna para gerenciamento de estoque de produtos, desenvolvida com Vue.js 3 e Tailwind CSS.

## 📋 Índice

- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Pré-requisitos](#pré-requisitos)
- [Instalação das Dependências](#instalação-das-dependências)
- [Configuração do Projeto](#configuração-do-projeto)
- [Como Executar](#como-executar)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Funcionalidades](#funcionalidades)
- [Documentação](#documentação)

## 🚀 Tecnologias Utilizadas

- **Vue.js 3.5.29** - Framework JavaScript progressivo para construção de interfaces
  - Composition API - API moderna do Vue para composição de lógica
  - Vue Router 5.0.3 - Roteamento oficial do Vue.js
  - Pinia 3.0.4 - Gerenciamento de estado oficial do Vue
- **Tailwind CSS 4.2.1** - Framework CSS utility-first para estilização
  - @tailwindcss/vite 4.2.1 - Plugin Vite para Tailwind CSS
  - @tailwindcss/postcss 4.2.1 - Plugin PostCSS para Tailwind CSS
- **Lucide Vue Next 0.577.0** - Biblioteca de ícones SVG modernos
- **Axios 1.13.6** - Cliente HTTP para requisições à API
- **Vite 7.3.1** - Build tool e dev server extremamente rápido
- **Vitest 4.0.18** - Framework de testes unitários
- **ESLint 10.0.2** - Linter para qualidade de código
- **Prettier 3.8.1** - Formatador de código
- **Docker & Docker Compose** - Containerização da aplicação

## 📦 Pré-requisitos

Antes de começar, você precisará ter as seguintes ferramentas instaladas em sua máquina:

### 1. Node.js e npm

O projeto requer **Node.js versão 20.19.0 ou superior, ou versão 22.12.0 ou superior**.

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

### 2. Git

**Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install git -y
```

**Verificar instalação:**
```bash
git --version
```

### 3. Docker e Docker Compose (Opcional)

**Linux (Ubuntu/Debian):**
```bash
# Instalar Docker
sudo apt update
sudo apt install docker.io -y
sudo systemctl start docker
sudo systemctl enable docker

# Adicionar seu usuário ao grupo docker
sudo usermod -aG docker $USER

# Instalar Docker Compose
sudo apt install docker-compose -y
```

**Verificar instalação:**
```bash
docker --version
docker-compose --version
```

**Nota:** Após adicionar seu usuário ao grupo docker, faça logout e login novamente.

## 🔧 Instalação das Dependências

### 1. Clonar o Repositório

```bash
git clone <url-do-repositorio>
cd desafio-tecnico-nexdom/estoque-ui
```

### 2. Instalar Dependências do Projeto

```bash
npm install
```

Este comando irá:
- Baixar todas as dependências listadas no `package.json`
- Criar a pasta `node_modules` com todos os pacotes necessários
- Gerar o arquivo `package-lock.json` (se não existir)

**Tempo estimado:** 1-3 minutos dependendo da sua conexão com a internet.

### 3. Verificar Instalação

```bash
# Verificar se as dependências foram instaladas corretamente
npm list --depth=0
```

## ⚙️ Configuração do Projeto

### Configuração da API

A aplicação está configurada para se conectar à API REST em `http://localhost:8080`.

A configuração está no arquivo `src/api/index.js`:

```javascript
const api = axios.create({
  baseURL: 'http://localhost:8080'
})
```

**Para alterar a URL da API:**

1. Edite o arquivo `src/api/index.js`
2. Modifique o valor de `baseURL` para a URL desejada

**Ou use variável de ambiente:**

Crie um arquivo `.env` na raiz do projeto `estoque-ui`:

```bash
VITE_API_URL=http://localhost:8080
```

E atualize `src/api/index.js` para usar:

```javascript
const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8080'
})
```

### Configuração de Porta

Por padrão, a aplicação roda na porta **5173**.

Para alterar, edite o arquivo `vite.config.js`:

```javascript
export default defineConfig({
  server: {
    port: 3000 // Altere para a porta desejada
  }
})
```

## 🏃 Como Executar

### ⚠️ Importante: A API deve estar rodando!

Antes de iniciar a interface, certifique-se de que a API REST está rodando em `http://localhost:8080`.

Consulte o README da API em `../estoque-api/README.md` para instruções de como iniciar a API.

### Opção 1: Usando Docker Compose (Recomendado)

Esta é a forma mais simples de executar a aplicação completa (API + UI).

```bash
# Na raiz do projeto (pasta pai de estoque-ui)
cd ..
docker-compose up --build
```

A aplicação estará disponível em:
- **UI:** `http://localhost:5173`
- **API:** `http://localhost:8080`

Para parar a aplicação:
```bash
docker-compose down
```

### Opção 2: Modo Desenvolvimento (Recomendado para desenvolvimento)

```bash
# Na pasta estoque-ui
npm run dev
```

A aplicação estará disponível em: `http://localhost:5173`

**Características do modo desenvolvimento:**
- ✅ Hot Module Replacement (HMR) - Atualizações instantâneas sem recarregar a página
- ✅ Source maps para debugging
- ✅ Mensagens de erro detalhadas
- ✅ Servidor de desenvolvimento rápido com Vite

**Saída esperada:**
```
VITE v7.3.1  ready in 500 ms

➜  Local:   http://localhost:5173/
➜  Network: use --host to expose
➜  press h + enter to show help
```

### Opção 3: Build de Produção

**1. Compilar para produção:**
```bash
npm run build
```

Este comando irá:
- Compilar e otimizar o código
- Minificar JavaScript e CSS
- Gerar os arquivos estáticos na pasta `dist/`

**2. Visualizar o build de produção localmente:**
```bash
npm run preview
```

A aplicação estará disponível em: `http://localhost:4173`

**3. Servir os arquivos estáticos (produção):**

Você pode usar qualquer servidor web estático. Exemplos:

**Usando serve (npm):**
```bash
# Instalar serve globalmente
npm install -g serve

# Servir a pasta dist
serve -s dist -l 5173
```

**Usando Python:**
```bash
cd dist
python3 -m http.server 5173
```

**Usando nginx:**
```bash
# Copiar arquivos para o diretório do nginx
sudo cp -r dist/* /var/www/html/

# Reiniciar nginx
sudo systemctl restart nginx
```

## 📁 Estrutura do Projeto

```
estoque-ui/
├── public/                      # Arquivos estáticos públicos
│   └── favicon.ico             # Ícone da aplicação
├── src/
│   ├── api/                    # Configuração e chamadas à API
│   │   └── index.js           # Cliente Axios configurado
│   ├── assets/                 # Assets (CSS, imagens, etc)
│   │   └── main.css           # Estilos globais com Tailwind
│   ├── components/             # Componentes Vue reutilizáveis
│   │   ├── AppLayout.vue      # Layout principal da aplicação
│   │   ├── AppSidebar.vue     # Barra lateral de navegação
│   │   └── ProductModal.vue   # Modal de cadastro/edição de produtos
│   ├── router/                 # Configuração de rotas
│   │   └── index.js           # Definição das rotas
│   ├── stores/                 # Stores Pinia (gerenciamento de estado)
│   │   └── counter.js         # Store de exemplo
│   ├── views/                  # Componentes de páginas/views
│   │   ├── MovementHistView.vue      # Histórico de movimentações
│   │   ├── MovementView.vue          # Registro de movimentações
│   │   ├── ProductsByTypeView.vue    # Produtos por tipo
│   │   ├── ProductsView.vue          # Listagem de produtos
│   │   └── ProfitView.vue            # Relatório de lucros
│   ├── App.vue                 # Componente raiz
│   └── main.js                 # Ponto de entrada da aplicação
├── .editorconfig               # Configuração do editor
├── .eslintrc.json             # Configuração do ESLint
├── .prettierrc.json           # Configuração do Prettier
├── Dockerfile                  # Configuração Docker
├── index.html                  # HTML principal
├── jsconfig.json              # Configuração JavaScript
├── package.json               # Dependências e scripts
├── vite.config.js             # Configuração do Vite
└── vitest.config.js           # Configuração dos testes
```

## 🎯 Funcionalidades

### Gestão de Produtos
- ✅ Listagem de produtos com tabela interativa
- ✅ Cadastro de novos produtos
- ✅ Edição de produtos existentes
- ✅ Exclusão de produtos (com validação de movimentações)
- ✅ Visualização de tipos de produtos
- ✅ Controle de estoque em tempo real

### Movimentações de Estoque
- ✅ Registro de entrada de produtos
- ✅ Registro de saída de produtos com preço de venda
- ✅ Histórico completo de movimentações
- ✅ Validação de estoque disponível
- ✅ Cálculo automático de estoque
- ✅ Filtros por data e tipo de movimentação

### Relatórios
- ✅ Produtos agrupados por tipo
- ✅ Relatório de lucro por produto
- ✅ Filtros por período de data
- ✅ Visualização de métricas e estatísticas
- ✅ Volume de saída por produto

### Interface
- ✅ Design responsivo (mobile, tablet, desktop)
- ✅ Tema moderno com Tailwind CSS
- ✅ Navegação intuitiva com sidebar
- ✅ Ícones modernos com Lucide
- ✅ Feedback visual de ações (alerts, confirmações)
- ✅ Validação de formulários
- ✅ Loading states e estados vazios

## 🧪 Executando os Testes

### Testes Unitários

```bash
# Executar todos os testes
npm run test:unit

# Executar testes em modo watch (desenvolvimento)
npm run test:unit -- --watch

# Executar testes com cobertura
npm run test:unit -- --coverage
```

### Linting e Formatação

```bash
# Verificar e corrigir problemas de lint
npm run lint

# Formatar código automaticamente
npm run format
```

## 🐛 Troubleshooting

### Erro: "Cannot find module"

Se você encontrar erros de módulos não encontrados:

```bash
# Limpar cache do npm
npm cache clean --force

# Remover node_modules e reinstalar
rm -rf node_modules package-lock.json
npm install
```

### Erro: "Port 5173 is already in use"

Se a porta 5173 já estiver em uso:

**Opção 1:** Matar o processo que está usando a porta:
```bash
# Encontrar o processo
lsof -ti:5173

# Matar o processo
kill -9 $(lsof -ti:5173)
```

**Opção 2:** Usar outra porta:
```bash
npm run dev -- --port 3000
```

### Erro: "EACCES: permission denied"

Se você encontrar erros de permissão:

```bash
# Corrigir permissões do npm
sudo chown -R $USER:$USER ~/.npm
sudo chown -R $USER:$USER node_modules
```

### API não responde / CORS Error

Certifique-se de que:
1. A API está rodando em `http://localhost:8080`
2. A configuração de CORS na API permite requisições de `http://localhost:5173`
3. Verifique o console do navegador para erros específicos

```bash
# Testar se a API está respondendo
curl http://localhost:8080/products
```

### Build falha com erro de memória

Se o build falhar por falta de memória:

```bash
# Aumentar limite de memória do Node.js
NODE_OPTIONS=--max_old_space_size=4096 npm run build
```

### Hot reload não funciona

Se as mudanças não aparecerem automaticamente:

```bash
# Parar o servidor (Ctrl+C)
# Limpar cache do Vite
rm -rf node_modules/.vite

# Reiniciar
npm run dev
```

### Erro com Tailwind CSS

Se os estilos do Tailwind não aparecerem:

```bash
# Verificar se o arquivo main.css está importado no main.js
# Limpar cache e reconstruir
rm -rf node_modules/.vite dist
npm install
npm run dev
```

## 📚 Documentação

### Documentação das Tecnologias

- **Vue.js 3:** [https://vuejs.org/guide/introduction.html](https://vuejs.org/guide/introduction.html)
  - Composition API: [https://vuejs.org/guide/extras/composition-api-faq.html](https://vuejs.org/guide/extras/composition-api-faq.html)
  - Vue Router: [https://router.vuejs.org/](https://router.vuejs.org/)
  - Pinia: [https://pinia.vuejs.org/](https://pinia.vuejs.org/)

- **Tailwind CSS:** [https://tailwindcss.com/docs](https://tailwindcss.com/docs)
  - Installation: [https://tailwindcss.com/docs/installation](https://tailwindcss.com/docs/installation)
  - Utility Classes: [https://tailwindcss.com/docs/utility-first](https://tailwindcss.com/docs/utility-first)
  - Customization: [https://tailwindcss.com/docs/configuration](https://tailwindcss.com/docs/configuration)

- **Lucide Vue Next:** [https://lucide.dev/guide/packages/lucide-vue-next](https://lucide.dev/guide/packages/lucide-vue-next)
  - Icons: [https://lucide.dev/icons/](https://lucide.dev/icons/)

- **Vite:** [https://vitejs.dev/guide/](https://vitejs.dev/guide/)
  - Configuração: [https://vitejs.dev/config/](https://vitejs.dev/config/)
  - Plugins: [https://vitejs.dev/plugins/](https://vitejs.dev/plugins/)

- **Vitest:** [https://vitest.dev/guide/](https://vitest.dev/guide/)
  - API: [https://vitest.dev/api/](https://vitest.dev/api/)

- **Axios:** [https://axios-http.com/docs/intro](https://axios-http.com/docs/intro)

- **ESLint:** [https://eslint.org/docs/latest/](https://eslint.org/docs/latest/)

- **Prettier:** [https://prettier.io/docs/en/](https://prettier.io/docs/en/)

### Guias e Tutoriais

- **Vue.js Tutorial:** [https://vuejs.org/tutorial/](https://vuejs.org/tutorial/)
- **Vue.js Examples:** [https://vuejs.org/examples/](https://vuejs.org/examples/)
- **Tailwind CSS Tutorial:** [https://tailwindcss.com/docs/installation](https://tailwindcss.com/docs/installation)
- **Vite Guide:** [https://vitejs.dev/guide/why.html](https://vitejs.dev/guide/why.html)

### Scripts Disponíveis

```json
{
  "dev": "CHOKIDAR_USEPOLLING=true vite --host",  // Inicia servidor de desenvolvimento
  "build": "vite build",                          // Compila para produção
  "preview": "vite preview",                      // Visualiza build de produção
  "test:unit": "vitest",                          // Executa testes unitários
  "lint": "run-s lint:*",                         // Executa todos os linters
  "lint:oxlint": "oxlint . --fix",               // Linter Oxlint
  "lint:eslint": "eslint . --fix --cache",       // Linter ESLint
  "format": "prettier --write --experimental-cli src/"  // Formata código
}
```

## 🚀 Primeiros Passos (Quick Start)

Para quem quer começar rapidamente:

```bash
# 1. Clone o repositório
git clone <url-do-repositorio>
cd desafio-tecnico-nexdom

# 2. Inicie a API (em um terminal)
cd estoque-api
./mvnw spring-boot:run

# 3. Inicie a UI (em outro terminal)
cd estoque-ui
npm install
npm run dev
```

Acesse:
- **UI:** http://localhost:5173
- **API:** http://localhost:8080
- **Swagger:** http://localhost:8080/swagger-ui.html

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
# Verificar lint
npm run lint

# Executar testes
npm run test:unit

# Formatar código
npm run format
```

### 3. Build de Produção

```bash
# Compilar
npm run build

# Testar build localmente
npm run preview
```

## 📝 Convenções de Código

### Nomenclatura de Componentes

- **PascalCase** para nomes de componentes: `ProductModal.vue`, `AppSidebar.vue`
- **kebab-case** para uso em templates: `<product-modal />`, `<app-sidebar />`

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

### Tailwind CSS

- Use classes utilitárias do Tailwind diretamente no template
- Evite estilos customizados quando possível
- Para componentes reutilizáveis, considere usar `@apply` no CSS

```vue
<!-- Bom: Usando classes Tailwind -->
<button class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700">
  Salvar
</button>

<!-- Evite: CSS customizado -->
<button class="custom-button">Salvar</button>
<style>
.custom-button {
  background: blue;
  color: white;
}
</style>
```

### Commits

Use mensagens de commit descritivas:
- `feat: adiciona filtro de produtos por tipo`
- `fix: corrige cálculo de estoque`
- `docs: atualiza README com instruções`
- `style: formata código com prettier`
- `refactor: reorganiza estrutura de pastas`
- `test: adiciona testes para ProductModal`

## 🎨 Customização do Tema

### Cores do Tailwind

As cores principais usadas no projeto:

- **Primary (Blue):** `blue-600`, `blue-700`, `blue-500`
- **Background:** `slate-50`, `slate-100`, `slate-900`
- **Text:** `slate-600`, `slate-700`, `slate-900`
- **Success:** `emerald-600`, `emerald-500`
- **Error:** `red-600`, `rose-600`

### Ícones Lucide

Exemplos de ícones usados:

```vue
<script setup>
import { Package, ArrowLeftRight, ClipboardList, Search, TrendingUp } from 'lucide-vue-next'
</script>

<template>
  <Package class="w-5 h-5" />
  <ArrowLeftRight class="w-5 h-5" />
</template>
```

Veja todos os ícones disponíveis em: [https://lucide.dev/icons/](https://lucide.dev/icons/)

## 🤝 Contribuindo

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'feat: Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📝 Licença

Este projeto foi desenvolvido como parte de um desafio técnico para a Nexdom.

## 👥 Contato

Para dúvidas ou sugestões, entre em contato com a equipe de desenvolvimento.

---

**Desenvolvido com ❤️ usando Vue.js 3 e Tailwind CSS**
