# VA-031 — Acessibilidade avançada da VCCAprender

**Issue:** [#70](https://github.com/Victor-Candeias/VCCAprender/issues/70)  
**Branch:** `feature/VA-031-accessibility`  
**Base:** `feature/VA-030-responsive-tablet-layouts`  
**Estado:** código e testes enviados ao GitHub; **compilação e testes em emulador/dispositivo ainda por validar**.

## Objetivo

Tornar a experiência educativa utilizável por crianças com diferentes capacidades visuais, motoras e auditivas, sem perder o conteúdo infantil e a navegação tradicional. A inclusão não depende de um único sentido: as respostas utilizam **texto, símbolo, cor, foco visível e áudio opcional**.

## Entregue

| Área | Implementação |
|---|---|
| Regras testáveis | `ui/accessibility/AccessibilityRules.kt`: descrições dos estados das respostas e cálculo de contraste WCAG sRGB |
| Ícones decorativos | `BrandArtwork.kt`: `description = null` elimina repetições quando o texto adjacente já identifica a ação |
| Ilustrações | `TopicIllustration.kt`: parâmetro `decorative`; cartões do ecrã inicial não anunciam o tema duas vezes |
| Cabeçalhos e progresso | `FamilyUi.kt`: `heading()`, `LiveRegionMode.Polite` no texto de progresso e barra visual excluída da leitura redundante |
| Respostas e foco | `GameUi.kt`: rótulos de estado `Selecionada, resposta correta` / `Selecionada, tenta novamente`; contorno para foco de teclado, símbolo de acerto e botões com altura mínima de 64dp |
| Feedback | `GameUi.kt` e `AnswerFeedbackPanel.kt`: acerto/erro identificado por texto, fundo e símbolo; confetes decorativos; atualização anunciada educadamente |
| Letras grandes | `GameNavigation`: botões empilhados com largura total se menos de 410dp ou escala tipográfica ≥1.5; evite truncagem a 200% |
| TTS opcional | `SpeechControls.kt`: botões de pelo menos 52dp para `Ouvir pergunta` e, após resposta, `Ouvir resultado`, usando **o mesmo motor de fala existente**; áudio apenas a pedido e respeitando a preferência parental |
| Jogos | `ActivityScreen.kt`, `AlphabetScreen.kt`, `DiscoverCardsScreen.kt`: feedback em voz alta opcional e eliminação de semânticas duplicadas do conteúdo anterior durante a animação |
| Navegação no tablet | `ResponsiveUi.kt`: grupos de travessia semântica para pergunta antes das opções em ecrãs de duas colunas |
| Perfis | `ProfileScreen.kt`: ações de entrada/edição/eliminação distinguíveis pelo nome da criança; avatares com semântica de seleção (RadioButton), sem descrição de arte repetida |
| Ecrã inicial | `HomeScreen.kt`: cabeçalhos semânticos, cartões com contorno ao receber foco e ilustrações decorativas quando o cartão já tem nome |
| Revelação A/B/C | `RevealTilesGrid.kt`: quadrados ocultos têm nome, papel de botão, estado `Por revelar`, foco visível e ação clicável por tecnologias assistivas |
| Editor | `ActivityEditorScreen.kt`: cabeçalhos de secções, filtros com altura mínima de 48dp, ações largas e feedback com região viva |
| Área parental | `ParentalSettingsScreen.kt`: controlos rotulados, espaço para texto ampliado e etiqueta explícita `Leitura por voz` |
| CI | `.github/workflows/android-ui-ci.yml` atualizado para compilar testes unitários **e testes instrumentados** (sem emulador) e executar testes unitários também em pushes para a VA-031 |

### Semântica e ordem de foco

- Os ícones apenas decorativos deixam de receber foco de TalkBack. Ícones informativos isolados continuam com descrição.
- Texto de feedback torna explícita a correção, sem depender apenas da cor nem de um símbolo.
- Em tablets, o leitor de ecrã encontra primeiro o grupo da pergunta e depois o das respostas.
- A pergunta anterior em animação mantém os elementos gráficos, mas deixa de disponibilizar controlo ou conteúdo de acessibilidade interativo enquanto sai do ecrã.
- As ações `Entrar`, `Editar` e `Eliminar` dos perfis incluem o nome do perfil, evitando vários botões indistinguíveis.
- Os filtros e botões normais já utilizam componentes Material3 acessíveis; os quadrados de descoberta recebem explicitamente `Role.Button`.
- O foco via teclado é delineado nos cartões de resposta e temas do ecrã inicial. Testar manualmente o deslocamento automático ao focar elementos fora do ecrã.

### Contraste

`ContrastRulesTest` (dentro de `AccessibilityRulesTest.kt`) verifica luminância relativa e contraste de texto normal segundo a fórmula WCAG. Pares testados:

- `AprenderColors.Ink` (`#24324B`) sobre `Cream`, `Sky`, `Coral`, `Mint`, `Lavender` e `Sunshine`: todos ≥ 4,5:1;
- `AprenderColors.Blue` (`#2156B5`) sobre branco: 6,86:1;
- `AprenderColors.Navy` sobre `Sky`: 11,52:1.

**Nota:** estes testes cobrem pares designados, não garantem por si só toda a UI, o foco, imagens nem o modo escuro. A verificação visual em dispositivos é obrigatória.

### Movimento reduzido

É reaproveitado o mecanismo implementado em VA-029: `GameMotionProvider` respeita a preferência parental `animations_enabled` do DataStore e `Settings.Global.ANIMATOR_DURATION_SCALE` do Android. Quando qualquer um desativa movimento, as transições/estados continuam utilizáveis e o feedback permanece textual. A cobertura automatizada existente em `GameMotionPolicyTest` e `GameMotionUiTest` é mantida.

## Testes acrescentados ou ajustados

**JVM**
- `app/src/test/java/com/vcc/aprender/AccessibilityRulesTest.kt`: estados de resposta independentes de cor e pares de contraste mínimo 4,5:1.

**Instrumentados (Compose UI)**
- `app/src/androidTest/java/com/vcc/aprender/AccessibilityUiTest.kt`:
  - estado da resposta anunciado no nó semântico;
  - `Enter` no cartão com foco de teclado chama a ação;
  - títulos semânticos em perfis/relatórios e ícones decorativos não duplicados;
  - botões `Anterior`/`Próximo` acessíveis com fonte a 200%;
  - quadrados de revelar imagem como botões para Switch Access.
- `GameMotionUiTest.kt`: asserção atualizada para o feedback textual após tornar o símbolo decorativo.
- Manter a regressão dos testes anteriores: `GameDesignSystemUiTest`, `ProfileScreenUiTest`, `ParentalSettingsUiTest`, `ResponsiveLayoutsUiTest`, `HomeScreenUiTest`.

Os testes automatizados **não substituem** TalkBack e Switch Access reais.

## Comandos de verificação (Codespaces ou Android Studio)

```bash
git switch feature/VA-031-accessibility
git pull --ff-only origin feature/VA-031-accessibility

gradle :app:compileDebugKotlin :app:compileReleaseKotlin \
  :app:compileDebugAndroidTestKotlin :app:testDebugUnitTest \
  :app:assembleDebug :app:assembleRelease \
  --console=plain --no-daemon --max-workers=2

# Necessita de emulador/dispositivo Android:
gradle :app:connectedDebugAndroidTest \
  --console=plain --no-daemon --max-workers=2
```

O workflow `Android UI CI` foi estendido a esta branch e compila também os testes AndroidTest (sem os executar). Consultar também o estado do GitHub Actions: a presença do workflow **não confirma sucesso de build**.

## Checklist de QA manual

- [ ] Android 8, 11, 13 e 16: navegar por perfis, jogos, relatórios, editor e área parental com TalkBack ligado; comparar foco e texto anunciado.
- [ ] Teclado e D-pad: Tab, Shift+Tab, setas, Enter e Espaço para navegar entre cartões, respostas, filtros, avatares, botões e diálogos.
- [ ] Switch Access: percorrer e acionar todas as respostas A/B/C, quadrados de revelação, perfis e controlos parentais.
- [ ] Fonte Android a 100%, 150% e 200%: telemóvel 360×800dp, paisagem 800×360dp, tablet 800×1280dp e 1280×800dp.
- [ ] Verificar em janelas pequenas que a navegação permite atingir a última resposta sem corte nem sobreposição.
- [ ] Observar o foco visível e confirmar que elementos não dependem exclusivamente da cor.
- [ ] Verificar zoom, contraste de textos dinâmicos, ícones, campos, barras de progresso, controlos desativados e estados de foco.
- [ ] Testar `Ouvir pergunta` e `Ouvir resultado` com voz portuguesa instalada e preferência de áudio ativa.
- [ ] Desativar áudio na área parental: os botões de voz devem desaparecer; sem leitura automática inesperada.
- [ ] Desativar animações na área parental e colocar escala de animação do Android a zero; verificar ausência de movimento.
- [ ] Durante transição A/B/C, confirmar que TalkBack não anuncia a pergunta anterior nem permite acionar respostas antigas.
- [ ] Verificar perfis existentes e persistência de preferências sem alterações Room.
- [ ] Validar testes instrumentados (incluindo teste de foco via Enter) em pelo menos um emulador e um dispositivo real, se possível.
- [ ] Inspecionar e corrigir eventuais avisos do Accessibility Scanner; anexar capturas e logs ao Issue #70.
- [ ] Rever e aprovar PR, resolver dependências VA-027 → VA-030 e só depois fazer merge na `main`.

## Limitações assumidas

1. **Nenhum build foi executado nesta sessão**. Código e testes foram escritos no GitHub; qualquer erro de compilação ou teste deve ser corrigido antes de fechar #70.
2. A API Compose de testes de teclado só valida interações simuladas; **Switch Access e TalkBack necessitam de QA real**.
3. Os conteúdos de aprendizagem ainda podem incluir emojis pedagogicamente específicos, cuja substituição correta está registada no VA-027.
4. A versão atual mantém a paleta clara do projeto; validação específica em modo escuro/alto contraste e outras configurações de sistema fica pendente.
5. Alguns ecrãs menores ou fluxos de importação/exportação podem exigir iteração após testes de fonte a 200%.
