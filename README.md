# Game Deals App

Aplicativo Android desenvolvido em **Kotlin** com **Jetpack Compose** para a Prova 01 da disciplina
Programação para Dispositivos Móveis (IFTM). O app consome a **CheapShark API** e permite buscar
promoções de jogos dinamicamente pelo título, exibindo capa, loja, preço original, preço
promocional, percentual de desconto e avaliação da Steam.

## 📡 API utilizada

- **Nome:** CheapShark API (agregador de promoções de jogos digitais)
- **Documentação:** https://apidocs.cheapshark.com/
- **Base URL:** `https://www.cheapshark.com/api/1.0/`
- **Endpoints consumidos:**
  - `GET /deals?title={termo}` — busca dinâmica de promoções pelo título do jogo
  - `GET /stores` — lista de lojas, usada para resolver o `storeID` de cada oferta em um nome legível (ex.: "Steam", "GOG", "Epic Games Store")
- **Autenticação:** **nenhuma.** A CheapShark é uma API pública gratuita e não exige API key,
  token ou qualquer cadastro — por isso o projeto não tem `local.properties` com segredos nem
  campos de `BuildConfig` para chaves.

## 🏗️ Arquitetura do projeto

```
app/src/main/java/com/example/cheapshark/
├── MainActivity.kt                 # Ponto de entrada, monta o Compose
├── data/
│   ├── CheapSharkModels.kt         # Data classes do JSON (Deal, Store)
│   ├── CheapSharkApiService.kt     # Interface Retrofit (/deals e /stores)
│   ├── RetrofitInstance.kt         # Configuração do cliente HTTP (Retrofit + OkHttp)
│   └── GameDealsRepository.kt      # Busca deals + resolve nome da loja (com cache de /stores)
└── ui/
    ├── GameDealsViewModel.kt       # Estados de UI (Loading / Success / Error) + busca com debounce
    ├── GameDealsScreen.kt          # Tela Compose: campo de busca + lista + estados
    └── theme/                      # Cores, tipografia e tema Material3
```

### Fluxo de dados
`GameDealsScreen` (UI) → `GameDealsViewModel` (estado + debounce de 500ms) →
`GameDealsRepository` (busca `/deals`, resolve loja via `/stores` cacheado) →
`CheapSharkApiService` (Retrofit) → API da CheapShark.

### Estados tratados
- **Loading:** `CircularProgressIndicator` centralizado enquanto a requisição está em andamento.
- **Success (vazio):** mensagem de "nenhum jogo encontrado".
- **Success (com dados):** lista rolável (`LazyColumn`) com capa (Coil), título, loja, preço
  riscado/promocional, desconto e nota Steam (quando disponível).
- **Error:** mensagem de erro (rede, timeout, JSON inválido etc.) com botão "Tentar novamente".

## ▶️ Como executar

1. Abra o projeto no **Android Studio** (versão Koala/2024.1 ou mais recente recomendada).
2. Copie `local.properties.example` para `local.properties` e ajuste apenas o `sdk.dir` —
   **não é necessária nenhuma chave de API**.
3. Aguarde o Gradle sincronizar as dependências (Retrofit, Coil, Compose, etc.).
4. Rode em um emulador ou dispositivo físico com **Android 7.0 (API 24)** ou superior.
5. Digite um termo no campo de busca (ex.: "Zelda", "Halo", "GTA") para ver as promoções
   atualizarem dinamicamente. Deixe o campo vazio para ver as melhores ofertas do momento.

## 🛠️ Tecnologias e bibliotecas

- Kotlin + Jetpack Compose (Material 3)
- Retrofit2 + Gson (requisições HTTP e parse de JSON)
- OkHttp Logging Interceptor (debug das chamadas)
- Coil (carregamento de imagens das capas dos jogos)
- Kotlin Coroutines + StateFlow (gerenciamento assíncrono de estado)
- Arquitetura MVVM (ViewModel + Repository)

## 🎥 Vídeo demonstrativo

> Link do vídeo (YouTube, não listado): _adicionar aqui antes da entrega (prazo: 27/08)_

## 📸 Screenshots

> Adicionar aqui as imagens da interface (pasta `screenshots/`) antes do envio do repositório.
