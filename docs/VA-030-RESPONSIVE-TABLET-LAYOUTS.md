# VA-030 — Layouts responsivos, tablets e orientação horizontal

**Issue:** [#68](https://github.com/Victor-Candeias/VCCAprender/issues/68)  
**Branch:** `feature/VA-030-responsive-tablet-layouts`  
**Base:** `feature/VA-029-game-animations`  
**Estado:** Código e testes adicionados. **Build e QA não executados por esta sessão.**

## Objetivo

Manter a VCCAprender utilizável em telemóveis e tablets, em retrato e paisagem, com as mesmas funcionalidades e sem obrigar a criança a deslocar o ecrã horizontalmente. A adaptação é feita em tempo de composição pela **largura e altura realmente disponíveis**, não pelo tipo de dispositivo declarado.

## Alterações implementadas

| Área | Implementação |
|---|---|
| Biblioteca de layouts | `ui/components/ResponsiveUi.kt`: `ResponsiveRules` e `AdaptiveTwoPaneScreen`, com modo empilhado e modo de duas colunas; cada coluna larga tem deslocação vertical independente |
| Jogos de atividades | `ActivityScreen.kt`: pergunta/ilustração/áudio e respostas/feedback/navegação distribuídos por painéis; preservados estados e callbacks |
| Alfabeto | `AlphabetScreen.kt`: letra/áudio e opções distribuídas por painéis; mantém animações VA-029 |
| Cartões A/B/C | `DiscoverCardsScreen.kt`: imagem/questão à esquerda e opções/feedback à direita em ecrãs largos; seleção do cartão anterior desativada durante transição |
| Descoberta de imagem | `RevealTilesGrid.kt`: grelha quadrada com largura máxima de 360dp para evitar tamanhos desproporcionais nos tablets |
| Seletor de baralho | `DeckSelectionScreen.kt`: filtros em `FlowRow` que passam para a linha seguinte e permitem toque de 48dp |
| Ecrã inicial | `HomeScreen.kt`: `LazyVerticalGrid` com `GridCells.Adaptive`; cabeçalhos ocupam toda a largura |
| Navegação inicial | `AprenderNavHost.kt`: substitui seis botões verticais por navegação horizontal deslocável com área de toque mínima de 48dp |
| Perfis | `ProfileScreen.kt`: grelha adaptativa para perfis, ações de 48dp+, avatar em duas linhas no diálogo e semântica de seleção |
| Área parental | `ParentalSettingsScreen.kt`: definições de áudio e de movimento/privacidade em duas colunas quando houver espaço suficiente, com switches nomeados |
| Relatórios | `ProgressScreen.kt`: resumo de largura completa e grelha responsiva para competências, história e progresso por tema |

## Regras e pontos de adaptação

| Largura / altura útil em dp | Jogos (atividades, alfabeto, A/B/C) | Área parental |
|---|---|---|
| 360 × 800 — telemóvel retrato | 1 coluna com scroll vertical | 1 coluna com scroll vertical |
| 800 × 360 — telemóvel paisagem | 1 coluna com scroll vertical, **sem duas colunas apertadas** | 2 colunas se houver espaço e altura de pelo menos 360dp |
| 800 × 1280 — tablet retrato | 1 coluna com scroll (largura confortável) | 2 colunas |
| 1280 × 800 — tablet paisagem | 2 colunas, deslocação independente | 2 colunas |
| 900 × 320 — paisagem muito baixa | 1 coluna com scroll | 1 coluna com scroll |

- Jogos: duas colunas apenas a partir de **840dp** de largura **e 360dp** de altura disponíveis.
- Área parental: duas colunas a partir de **760dp** de largura e 360dp de altura.
- Grelhas de perfis, temas e relatórios: colunas adaptativas com células de largura mínima aproximada **290–300dp**, até à largura máxima de conteúdo de 1100dp.
- Painéis de jogos: conteúdo centralizado até aproximadamente 1200dp, com scrolling independente.
- As transições de perguntas continuam a seguir a preferência parental e a escala de animação do sistema herdadas do VA-029.

A **disposição efetiva** também depende da densidade, da dimensão de letra, das barras do sistema e do tamanho da janela (incluindo multiwindow). Os valores acima são exemplos de referência, não garantias de comportamento de todos os modelos.

## Acessibilidade

- Botões dos perfis e navegação principal com altura mínima de 48dp; botões de resposta dos jogos já garantem pelo menos 64dp; filtros de baralho com 48dp.
- Controlos parentais com nomes próprios para leitor de ecrã.
- O seletor de avatares organiza-se em 2×2 e indica a opção selecionada.
- Ao alternar para duas colunas, a ordem de leitura da composição segue a coluna da pergunta e depois a coluna das respostas; validar com TalkBack.
- A deslocação vertical independente evita que texto ampliado torne indisponíveis opções e ações.
- Não existem novas dependências de serviços online, nem alterações no esquema Room.

## Testes acrescentados

**Unitários:**
- `app/src/test/java/com/vcc/aprender/ResponsiveRulesTest.kt`: pontos de adaptação por largura/altura e quantidade indicativa de colunas.

**Instrumentados:**
- `app/src/androidTest/java/com/vcc/aprender/ResponsiveLayoutsUiTest.kt`: modo empilhado em telemóvel retrato e paisagem baixa, modo de dois painéis em tablet, visibilidade de controlos parentais, perfis em duas colunas e texto a 200%.

### Comandos de build e testes

```bash
git switch feature/VA-030-responsive-tablet-layouts
git pull --ff-only origin feature/VA-030-responsive-tablet-layouts

gradle :app:compileDebugKotlin :app:compileReleaseKotlin \
  :app:testDebugUnitTest :app:assembleDebug :app:assembleRelease \
  --console=plain --no-daemon --max-workers=2

# Requer emulador/dispositivo em execução:
gradle :app:connectedDebugAndroidTest \
  --console=plain --no-daemon --max-workers=2
```

## Matriz de validação manual

- [ ] Build Debug e Release (incluindo recursos VA-028 e animações VA-029).
- [ ] Testes unitários e instrumentados, incluindo testes anteriores da app.
- [ ] Telefone retrato 360×800dp: jogos, perfis, home, relatórios, área parental.
- [ ] Telefone paisagem 800×360dp e 900×320dp: botões acessíveis por scroll e menu inicial utilizável.
- [ ] Tablet retrato 800×1280dp: grelhas, seletor de baralho, switches, jogos.
- [ ] Tablet paisagem 1280×800dp: duas colunas, scroll independente, feedback e navegação.
- [ ] Testar tamanho de letra 200%, Ampliação de ecrã e ecrãs com margens de sistema diferentes.
- [ ] TalkBack: ordem de foco pergunta → respostas → feedback → botão avançar; seletor de avatares e controlos parentais.
- [ ] Verificar todos os cartões A/B/C com imagem oculta, revelação parcial, opções A/B/C e transições entre cartões.
- [ ] Rodar o aparelho no meio de uma pergunta respondida e verificar conservação do estado em ViewModel.
- [ ] Verificar que scroll, animações e TTS funcionam em paisagem e em modo de movimento reduzido.
- [ ] Verificar comportamento em janelas pequenas/multiwindow e tablets com teclado.
- [ ] Fazer capturas de ecrã de referência nos tamanhos testados e anexar ao Issue.
- [ ] Abrir PR, rever e integrar na branch final após validar a cadeia VA-027 → VA-028 → VA-029 → VA-030.

## Riscos e pendências

1. **Sem build ou testes executados nesta sessão**: código publicado através do conector GitHub, sem acesso ao Android SDK/emulador do Codespaces. Não marcar como validado.
2. `ResponsiveLayoutsUiTest` usa viewports fixos de Compose para verificar a mudança de layout, mas a validação de rotação real exige testes manuais em emulador.
3. O VA-030 herda todas as alterações das branches base. Um PR diretamente para `main` pode incluir commits de VA-027/028/029 ainda por validar.
4. A animação `GameQuestionTransition` mede o layout dos painéis durante a transição; monitorizar dispositivos lentos, posições de scroll e o foco TalkBack.
5. Grelhas adaptativas podem apresentar cartões de alturas diferentes; validar leitura e navegação com leitor de ecrã.
6. Fazer verificação visual de campos do editor de atividades, ecrãs de erro e outros diálogos que não foram redesenhados no VA-030.
7. O ícone e o banner final do VA-025 continuam dependentes da aprovação visual, fora do âmbito deste trabalho.
