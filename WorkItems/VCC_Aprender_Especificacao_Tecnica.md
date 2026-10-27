# VCC Aprender — Especificação funcional e técnica

**Versão:** 1.0  
**Plataforma:** Android  
**Público-alvo:** Crianças dos 3 aos 8 anos; pais e encarregados de educação  
**Idioma inicial:** Português de Portugal (pt-PT)  
**Tecnologia:** Kotlin, Jetpack Compose, Room, DataStore  
**Princípio:** Offline-first, sem publicidade e sem contas obrigatórias

## 1. Visão do produto

O **VCC Aprender** é uma aplicação educativa Android para crianças entre os 3 e os 8 anos. Combina aprendizagem de letras, números, cores, formas, animais, sons e palavras com exercícios interativos, leitura em voz alta e acompanhamento da evolução. Inclui o jogo **Descobre a Resposta**, inspirado em cartões físicos com uma pergunta, três imagens (A, B e C) e três quadrados escuros que revelam smiles quando tocados.

### 1.1 Objetivos

- Incentivar a aprendizagem por exploração, repetição e feedback positivo.
- Adaptar conteúdos à faixa etária e ao progresso da criança.
- Disponibilizar conteúdos essenciais sem ligação à Internet.
- Permitir que os adultos acompanhem a evolução sem expor dados da criança.
- Possibilitar a criação e importação de novos conjuntos de cartões.

### 1.2 Princípios de UX infantil

- Botões grandes, pouco texto e navegação previsível.
- Instruções áudio em português de Portugal, com opção de repetição.
- Evitar publicidade, compras dentro da aplicação e mecanismos de pressão.
- Feedback encorajador mesmo em respostas erradas; sem penalizações no modo livre.
- Área de pais protegida por uma barreira adequada à idade (por exemplo, pergunta de adulto).
- Suporte a acessibilidade, contraste e controlo de sons/animações.

## 2. Módulos educativos

| Módulo | Conteúdo | Exemplos de atividades |
|---|---|---|
| Letras | Alfabeto, maiúsculas/minúsculas, fonemas | Identificar letras, associar letra e imagem |
| Números | 0–100, contagem, comparação | Contar objetos, ordenar números |
| Cores | Cores básicas e combinações | Selecionar objeto da cor pedida |
| Formas | Círculo, quadrado, triângulo e outras | Associar silhuetas, encontrar formas |
| Animais | Nomes, habitats e sons | Reconhecer animal pela imagem ou som |
| Sons | Sons do quotidiano, animais e instrumentos | Ouvir e escolher a fonte do som |
| Palavras | Vocabulário, sílabas e associação imagem/palavra | Completar palavras, associar conceitos |
| Exercícios | Mistura de conteúdos, dificuldade progressiva | Quiz, correspondências e sequências |
| Descobre a Resposta | Cartões com 3 imagens e revelação de smiles | Responder A/B/C e revelar resultado |

### 2.1 Faixas etárias sugeridas

- **3–4 anos:** reconhecimento visual e auditivo; instruções curtas; atividades sem limite de tempo.
- **5–6 anos:** letras, sílabas simples, contagem e relações lógicas.
- **7–8 anos:** leitura inicial, operações básicas, desafios e explicações.

A faixa etária é apenas um ponto de partida; a dificuldade pode ser ajustada manualmente.

## 3. Jogo «Descobre a Resposta»

### 3.1 Conceito original

Cada cartão apresenta uma pergunta e três imagens identificadas pelas letras **A**, **B** e **C**. Na parte inferior existem três quadrados escuros, correspondentes às respostas. Nos cartões físicos, o calor do dedo revela um smile: na resposta certa, um smile a rir; nas erradas, smiles que não riem.

**Versão digital:** o ecrã tátil Android não mede a temperatura do dedo. A aplicação **simula o efeito térmico** com uma animação de revelação gradual, sem afirmar que deteta calor real.

### 3.2 Layout do cartão

```text
┌──────────────────────────────────────┐
│ Qual destes animais é um gato?       │
│                                      │
│     A          B          C          │
│    [🐶]       [🐱]       [🐰]         │
│                                      │
│   [■■■]      [■■■]      [■■■]         │
│                                      │
│  Ouvir pergunta     Próximo cartão   │
└──────────────────────────────────────┘
```

Os quadrados devem ficar alinhados com as respetivas imagens; o layout deve adaptar-se a telemóveis e tablets.

### 3.3 Fluxo do jogo

1. A criança escolhe um conjunto de cartões.
2. A aplicação apresenta uma pergunta escrita e, opcionalmente, narrada.
3. São mostradas três imagens com as letras A, B e C.
4. Os três quadrados começam escuros, sem revelar o resultado.
5. A criança toca ou mantém o dedo num quadrado.
6. Uma animação simula a superfície escura a desaparecer.
7. A opção correta revela **😄**; as incorretas revelam **😟** (configurável).
8. Uma resposta correta recebe feedback positivo; uma incorreta permite tentar novamente.
9. O cartão termina quando a criança encontra a resposta correta ou escolhe avançar, de acordo com o modo.
10. O progresso é guardado localmente.

### 3.4 Modos de revelação

| Modo | Comportamento |
|---|---|
| Toque | Revelação imediata ao tocar no quadrado |
| Pressionar | Revelação após manter o dedo por um período configurável (ex.: 1,5 s) |
| Térmico simulado | Revelação gradual com máscara/opacidade e animação visual |

No MVP, implementar **Toque** e **Térmico simulado**; o modo Pressionar pode ser acrescentado depois. O efeito térmico é exclusivamente visual.

### 3.5 Modos de jogo

- **Livre:** sem cronómetro, sem penalizações e com tentativas ilimitadas.
- **Desafio:** sequência de cartões, pontuação opcional e estrelas.
- **Praticar erros:** repetir cartões com dificuldades anteriores.
- **Aleatório:** baralhar cartões e posição da resposta correta para evitar memorização posicional.

### 3.6 Tipos de perguntas

- Identificar um animal, cor, forma, letra ou número.
- Escolher a imagem que corresponde a uma palavra ou som.
- Contar objetos e escolher a quantidade certa.
- Selecionar a palavra com a sílaba correta.
- Descobrir o objeto que não pertence ao grupo.

### 3.7 Regras de conteúdo

- Cada cartão tem exatamente três respostas, uma correta.
- Imagens com licença adequada ou criadas para a aplicação.
- Áudio opcional e texto alternativo para imagens.
- Explicação breve da resposta correta, quando útil.
- Evitar imagens ambíguas, perguntas culturalmente enviesadas ou respostas múltiplas involuntárias.

## 4. Ecrãs da aplicação

| Ecrã | Funcionalidades |
|---|---|
| Boas-vindas | Escolha de idioma e apresentação simples |
| Perfis | Seleção de criança e avatar; nome opcional |
| Início | Módulos educativos e botão «Continuar» |
| Catálogo | Categorias, níveis e conjuntos disponíveis |
| Atividade | Pergunta, imagens, áudio, interações e feedback |
| Descobre a Resposta | Cartão A/B/C, quadrados escuros e revelação |
| Resultado | Feedback amigável e opção de repetir/avançar |
| Conquistas | Estrelas e marcos educativos sem pressão |
| Área dos pais | Perfis, progresso, conteúdos e definições |
| Editor de cartões | Criar pergunta, anexar 3 imagens, escolher resposta |
| Relatórios | Evolução por módulo, acertos, tentativas e sessões |
| Definições | Voz, volume, animações, dificuldade e acessibilidade |

## 5. Modelo de dados

### 5.1 Entidades Room

**ChildProfile**
- `id: UUID`
- `displayName: String?`
- `avatarId: String`
- `ageBand: String`
- `createdAt: Instant`

**LearningModule**
- `id: String`
- `title: String`
- `ageMin: Int`
- `ageMax: Int`
- `enabled: Boolean`

**CardSet**
- `id: String`
- `moduleId: String`
- `title: String`
- `difficulty: Int`
- `language: String`
- `version: Int`
- `source: String`

**QuestionCard**
- `id: String`
- `cardSetId: String`
- `questionText: String`
- `questionAudioAsset: String?`
- `explanation: String?`
- `revealMode: String`
- `orderIndex: Int`

**AnswerOption**
- `id: String`
- `cardId: String`
- `label: String` (A/B/C)
- `imageAsset: String`
- `audioAsset: String?`
- `isCorrect: Boolean`

**LearningSession**
- `id: UUID`
- `childId: UUID`
- `moduleId: String`
- `startedAt: Instant`
- `endedAt: Instant?`
- `mode: String`

**AnswerAttempt**
- `id: UUID`
- `sessionId: UUID`
- `cardId: String`
- `selectedOptionId: String`
- `correct: Boolean`
- `attemptNumber: Int`
- `answeredAt: Instant`
- `durationMs: Long?`

**ChildSettings**
- `childId: UUID`
- `speechEnabled: Boolean`
- `soundEffectsEnabled: Boolean`
- `animationsEnabled: Boolean`
- `revealMode: String`
- `difficultyOverride: Int?`

### 5.2 Exemplo JSON de cartão

```json
{
  "id": "ANIMALS_001",
  "cardSetId": "ANIMALS_3_5",
  "language": "pt-PT",
  "question": "Qual destes animais é um gato?",
  "audio": "audio/animals_001_pt.wav",
  "options": [
    {"id": "a", "label": "A", "image": "images/dog.webp", "correct": false},
    {"id": "b", "label": "B", "image": "images/cat.webp", "correct": true},
    {"id": "c", "label": "C", "image": "images/rabbit.webp", "correct": false}
  ],
  "revealMode": "THERMAL_SIMULATION",
  "explanation": "O gato faz miau!"
}
```

**Validações de importação:** exatamente três opções, labels A/B/C únicas, uma única resposta correta, ficheiros de imagem existentes, limites de tamanho e IDs únicos.

## 6. Arquitetura técnica

```text
VCC Aprender (Android)
├── app/                         Navegação e configuração
├── core/designsystem/           Tema, componentes infantis e acessibilidade
├── core/data/                   Room, DataStore, assets e importação
├── core/audio/                  TextToSpeech e efeitos sonoros
├── core/content/                Modelos e validação de cartões
├── feature/profiles/            Perfis de crianças
├── feature/home/                Página inicial
├── feature/learning/            Letras, números, cores, formas, sons
├── feature/revealgame/          Descobre a Resposta
├── feature/progress/            Sessões, evolução e relatórios
├── feature/parents/             Área dos pais
└── feature/cardeditor/          Editor e importação/exportação
```

**Stack recomendada:**
- Kotlin + Jetpack Compose + Material 3 (tema visual infantil personalizado).
- MVVM, ViewModel, StateFlow e Coroutines.
- Navigation Compose.
- Room para perfis, cartões e progresso.
- DataStore para preferências.
- Android TextToSpeech (`pt-PT` quando disponível) e ficheiros áudio pré-gravados como alternativa.
- Coil para imagens locais.
- Hilt para injeção de dependências, se a dimensão do projeto justificar.
- Testes JUnit, Compose UI Test e testes instrumentados Android.

### 6.1 Motor do jogo

```kotlin
data class RevealOption(
    val id: String,
    val label: String,
    val imagePath: String,
    val isCorrect: Boolean
)

data class RevealCard(
    val id: String,
    val question: String,
    val options: List<RevealOption>
)

enum class RevealMode { TAP, HOLD, THERMAL_SIMULATION }
```

O motor deve validar as respostas, emitir eventos de tentativa, gerir o estado de revelação e permitir reiniciar o cartão. A UI implementa os efeitos visuais com `Animatable`, `animateFloatAsState` ou `Canvas` e máscaras de revelação. Não utilizar sensores de temperatura como substituto do toque.

### 6.2 Narração e áudio

- Ler a pergunta ao abrir o cartão, se autorizado nas definições.
- Botão para repetir a leitura.
- Preferir voz pt-PT; se não estiver instalada, informar o adulto e permitir instalar uma voz compatível ou usar áudio incluído.
- Evitar reprodução simultânea de narração e efeitos sonoros.

## 7. Acompanhamento da evolução

### Indicadores

- Cartões concluídos por módulo e conjunto.
- Taxa de acerto na primeira tentativa.
- Número de tentativas por pergunta.
- Conteúdos praticados recentemente.
- Evolução semanal e áreas que merecem repetição.
- Tempo aproximado de utilização, sem transformar velocidade em pressão para a criança.

### Relatórios

- Visão por criança, módulo e período.
- Exportação CSV/JSON para adultos numa fase posterior.
- Dados guardados localmente; backup/exportação manual opcional.
- Sem classificações públicas nem comparação entre crianças.

## 8. Segurança, privacidade e conformidade

- Não exigir contas nem recolher dados pessoais desnecessários.
- Não incluir anúncios, rastreadores publicitários ou analytics externos no MVP.
- Guardar progresso apenas no dispositivo, com possibilidade de apagar perfil e dados.
- Colocar exportações, links externos e definições avançadas na área dos pais.
- Evitar permissões de câmara, microfone e localização no MVP.
- Se houver publicação na Google Play, verificar as políticas aplicáveis a aplicações destinadas a crianças e os requisitos de privacidade em vigor.
- Para conteúdos importados, validar formatos, tamanho e origem; não executar conteúdo remoto.

## 9. Roadmap

| Fase | Entregáveis | Critérios de aceitação |
|---|---|---|
| 0 — Fundação | Projeto Android, tema, navegação, modelos | Build e testes básicos funcionais |
| 1 — MVP educativo | Perfis locais, letras, números, cores, formas, animais, sons e palavras (conteúdo inicial limitado) | Criança consegue concluir atividades offline |
| 2 — Descobre a Resposta | Cartões A/B/C, quadrados, smiles, animação e feedback | Resposta certa/errada identificada; sem revelação prematura |
| 3 — Voz e acessibilidade | Leitura pt-PT, repetição e ajustes de áudio | Perguntas podem ser ouvidas; app utilizável sem áudio |
| 4 — Progresso | Sessões, tentativas, relatórios para pais | Evolução persiste após reiniciar a app |
| 5 — Conteúdos | Mais conjuntos, níveis e repetição de dificuldades | Catálogo ampliável sem alterar motor do jogo |
| 6 — Editor | Criar/importar/exportar cartões JSON e imagens | Conteúdo validado e editável localmente |
| 7 — Polimento | Tablets, acessibilidade, testes, backup | Experiência estável em diferentes ecrãs |
| 8 — Futuro | Sincronização familiar opcional, novos jogos e idiomas | Funcionalidades opcionais sem comprometer o offline |

## 10. MVP recomendado

O primeiro lançamento deve conter:

1. Um perfil de criança local.
2. Página inicial com categorias.
3. Exercícios simples de letras, números, cores, formas, animais, sons e palavras.
4. **Descobre a Resposta** com pelo menos 30 cartões originais distribuídos por categorias.
5. Perguntas em texto e leitura em voz alta.
6. Revelação por toque e efeito térmico simulado.
7. Feedback com smile a rir na resposta correta e smile não sorridente nas incorretas.
8. Registo de tentativas e progresso básico.
9. Área de pais com definições essenciais.
10. Funcionamento offline sem publicidade.

### Critérios de aceitação do jogo

- Cada cartão mostra uma pergunta, três imagens A/B/C e três quadrados escuros.
- A resposta não é visível antes da interação.
- Ao tocar num quadrado, revela-se o smile correspondente.
- A opção correta apresenta um smile a rir; as outras não.
- Uma resposta incorreta permite continuar a explorar sem penalização no modo livre.
- O jogo funciona sem Internet e mantém o progresso após fechar a app.
- A ordem das opções pode variar sem alterar a resposta correta.
- O modo de animação respeita a preferência por movimento reduzido.

## 11. Backlog inicial de trabalho

| ID | Work item | Prioridade |
|---|---|---|
| VA-001 | Criar projeto Android e configuração Gradle | P0 |
| VA-002 | Definir tema infantil e componentes Compose | P0 |
| VA-003 | Implementar navegação e ecrã inicial | P0 |
| VA-004 | Implementar perfis locais de crianças | P0 |
| VA-005 | Criar modelo Room e DAOs | P0 |
| VA-006 | Criar catálogo e importação de cartões locais | P0 |
| VA-007 | Implementar módulos básicos de aprendizagem | P0 |
| VA-008 | Implementar ecrã de cartão A/B/C | P0 |
| VA-009 | Implementar revelação de smiles por toque | P0 |
| VA-010 | Implementar animação térmica simulada | P0 |
| VA-011 | Implementar validação e registo de tentativas | P0 |
| VA-012 | Integrar TextToSpeech pt-PT | P0 |
| VA-013 | Criar 30 cartões educativos originais | P0 |
| VA-014 | Criar área dos pais e preferências | P0 |
| VA-015 | Implementar dashboard de progresso básico | P0 |
| VA-016 | Testes unitários do motor de cartões | P0 |
| VA-017 | Testes UI e acessibilidade | P0 |
| VA-018 | Adicionar modos desafio e praticar erros | P1 |
| VA-019 | Implementar editor de cartões | P1 |
| VA-020 | Importação/exportação de conjuntos JSON | P1 |
| VA-021 | Relatórios avançados e exportação CSV | P1 |
| VA-022 | Otimizar layouts para tablets | P1 |
| VA-023 | Backup e restauro local | P1 |
| VA-024 | Novos conteúdos e idiomas | P2 |

## 12. Decisões em aberto

- Confirmar o layout exato dos cartões físicos (quadrados sob cada imagem ou numa faixa inferior).
- Escolher identidade visual, mascote e estilo dos smiles.
- Definir se o editor de cartões entra no MVP ou numa fase posterior.
- Escolher entre narração TTS e gravações de voz para conteúdos essenciais.
- Decidir quantos conjuntos originais serão incluídos no primeiro lançamento.

---

**Resumo:** O VCC Aprender deve ser uma plataforma modular de aprendizagem infantil, com um jogo de revelação de respostas que reproduz digitalmente a surpresa dos cartões termossensíveis, sem depender de deteção real de calor. O projeto deve priorizar segurança, simplicidade, português de Portugal e funcionamento offline.
