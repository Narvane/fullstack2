# JTech Tasklist

Aplicação fullstack de gerenciamento de tarefas multiusuário construída para demonstrar domínio de arquitetura limpa no backend e organização modular no frontend.

## Visão Geral da Arquitetura

- Mesmo com o enunciado sugerindo a cadeia clássica Controller → Service → Repository → Domain, optei por seguir o estilo já adotado no código base de Clean Architecture, expandindo apenas onde fazia sentido (ex.: presenters, qualifiers dedicados e escopos específicos).
- Os módulos Java foram divididos em `adapters` (entrada/saída), `application` (domínio e use cases) e `config` (injeções, presenters, qualifiers). Essa separação manteve o domínio isolado de frameworks e facilitou a troca de implementações externas.
- No frontend organizei o fluxo em camadas claras: stores (Pinia) concentram estado/efeitos, services encapsulam comunicação HTTP e componentes/páginas apenas orquestram interações.
- Escolhi conscientemente uma arquitetura robusta para provar proficiência. Em um projeto de menor porte eu simplificaria camadas, mas mantive o padrão completo para evidenciar boas práticas de produção.

## Stack Tecnológica

- **Backend**: Java 17, Spring Boot 3, Spring Security, Spring Data JPA, PostgreSQL, Flyway, JJWT, Jakarta Validation. Preferi documentar a API via Swagger/OpenAPI usando apenas o arquivo `openapi.yaml` para manter as classes limpas.
- **Frontend**: Vue 3 (Composition API + TypeScript), Vue Router 4, Pinia 3, Bootstrap 5 (com Bootstrap Vue Next) para UI, Axios para HTTP, Vite para build e Vitest/vue-test-utils para testes.
- **Observações**: A stack original sugeria uma lib Material Design, mas optei por Bootstrap por domínio prévio. Nunca havia usado Vue; utilizei assistência de IA para mapear conceitos que já dominava em React e acelerar o aprendizado sem abrir mão dos requisitos.

## Como Rodar Localmente

1. **Pré-requisitos**: Docker/Docker Compose, Java 17+, Node 20+, npm.
2. **Banco/Postgres**  
   ```bash
   cd jtech-tasklist-backend/composer
   docker compose up -d
   ```
3. **Backend**  
   ```bash
   cd jtech-tasklist-backend
   ./gradlew bootRun
   ```  
   API disponível em `http://localhost:8080`. Documentação em `http://localhost:8080/doc/tasklist/v1/swagger-ui/index.html`.
4. **Frontend**  
   ```bash
   cd jtech-tasklist-frontend
   npm install
   npm run dev
   ```  
   Interface em `http://localhost:5173`. Basta acessar as URLs informadas após subir o compose.

## Como Rodar os Testes

- **Backend**: `cd jtech-tasklist-backend && ./gradlew test`
- **Frontend**: `cd jtech-tasklist-frontend && npm run test:unit`

## Estrutura de Pastas Detalhada

```
.
├── jtech-tasklist-backend
│   ├── composer/                 # docker-compose com Postgres local
│   ├── src/main/java/br/com/jtech/tasklist
│   │   ├── adapters/input        # controllers REST + protocolos de entrada
│   │   ├── adapters/output       # presenters, repositories e gateways externos
│   │   ├── application/core      # domínios e use cases (regras puras)
│   │   ├── application/ports     # contratos input/output e protocolos
│   │   └── config                # configs de DI, qualifiers, presenters, segurança e swagger
│   ├── src/main/resources        # Flyway migrations, application.yml, openapi.yaml
│   └── src/test/java             # testes de unidade e integração fatiados por adapters/core
├── jtech-tasklist-frontend
│   ├── src
│   │   ├── components            # componentes reutilizáveis (cards, colunas)
│   │   ├── views                 # páginas (Login, Register, Board)
│   │   ├── stores                # Pinia stores (auth, tasks, UI)
│   │   ├── services              # camada de API (axios)
│   │   ├── router                # rotas + guards
│   │   └── types                 # contratos compartilhados
│   └── public / config           # assets, Vite, ESLint, TS configs
└── README.md                     # este documento
```

## Decisões Técnicas Aprofundadas

- **Clean Architecture com presenters**: cada use case retorna modelos de domínio puros; presenters no pacote `adapters/output/presenters` traduzem para DTOs sob request scope, evitando retornos diretos nas ações REST e reforçando separação de responsabilidades.
- **Request scope consciente**: controllers permanecem stateless enquanto presenters mantêm estado apenas durante o ciclo da requisição, reduzindo acoplamento e permitindo reutilização entre diferentes endpoints com respostas específicas.
- **Qualifiers explícitos**: utilizei qualifiers nomeados em `config/qualifiers` para tornar legível qual implementação concreta está sendo injetada (ex.: repositórios distintos ou diferentes strategies). Isso evita ambiguidades do Spring e documenta a intenção no próprio código.
- **Swagger via YAML**: a especificação OpenAPI mora em `src/main/resources/openapi.yaml`, carregada pela configuração customizada, deixando classes Java sem anotações ruidosas e permitindo versionamento claro da documentação.
- **Frontend opinado**: Bootstrap substituiu a biblioteca Material sugerida para ganhar velocidade na entrega sem sacrificar responsividade. Mesmo sendo novo em Vue, mantive boas práticas (Composition API, stores tipadas, components desacoplados) e consultei IA apenas para acelerar o mapeamento dos conceitos que já aplicava em React.

## Melhorias e Roadmap de Escalabilidade

- **Isolamento de domínios sensíveis**: em um cenário de maior escala, migraria o módulo de usuários/dados pessoais para um microserviço dedicado, mantendo o tasklist separado e reduzindo o raio de impacto de mudanças.
- **Autenticação enterprise**: substituiria o fluxo atual por OAuth2/OIDC com Authorization Server externo (ex.: Keycloak ou Cognito) e tokens de acesso/refresh robustos para suportar integrações com parceiros.
