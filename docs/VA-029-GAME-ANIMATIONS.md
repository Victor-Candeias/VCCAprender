# VA-029 — Animações e celebrações dos jogos

**Issue:** [#66](https://github.com/Victor-Candeias/VCCAprender/issues/66)  
**Branch:** `feature/VA-029-game-animations` (a partir de `feature/VA-028-app-branding-splash`)

## Implementado

- `GameMotionPolicy` condiciona movimento à preferência parental **e** à escala de animação do Android.
- `GameMotionProvider` lê a escala `Settings.Global.ANIMATOR_DURATION_SCALE` e observa alterações por `ContentObserver`; preferência guardada em DataStore `animations_enabled`, valor predefinido `true`, sem migração de base de dados.
- A navegação propaga `LocalGameAnimationsEnabled` aos jogos. Até chegar a primeira preferência guardada, inicia sem movimento para evitar animação antes de aplicar o valor persistido.
- `GameQuestionTransition` partilha uma transição de entrada/saída entre perguntas em atividades, alfabeto e cartões A/B/C, substituída por transição instantânea quando o movimento está desativado.
- `GameAnswerButton`: cores e aumento de escala curtos ao selecionar; o botão mantém a área de toque e a descrição de acessibilidade.
- `GameProgress`: progressão linear animada, instantânea quando as animações estão desativadas.
- `GameFeedback`: resposta correta com estrelas/confetes vetoriais e mensagem legível; resposta incorreta com incentivo suave, sem penalizações.
- `AnswerFeedbackPanel`: feedback positivo com celebração e negativo com indicação textual; sem música ou estímulos intermitentes.
- `GameCompletionBanner`: troféu, estrelas e mensagem de parabéns nas atividades/alphabet após resposta à última pergunta e no fim do baralho A/B/C.
- Controlo parental na secção «Movimento e animações», com `Switch` acessível que guarda a preferência.
- Testes unitários de `GameMotionPolicy` e `ParentalSettings`, testes instrumentados para feedback de movimento reduzido e controlo parental.

## Verificação técnica
A transição anima apenas a apresentação, não altera a validação, gravação de respostas ou navegação. Os cartões anteriores mantêm dados próprios até saírem da transição; ações da pergunta anterior ficam desativadas. O efeito de celebração usa vetores locais e é ocultado do leitor de ecrã por ser decorativo. O feedback verbal/textual permanece disponível.

**Limitação importante:** a conclusão das atividades e do alfabeto é uma celebração de fim de sequência, não um novo estado persistido de «conclusão», pois a lógica original dos ViewModels volta ao início após a última pergunta. A celebração não depende de responder corretamente.

## Compilação e testes pendentes

```bash
git switch feature/VA-029-game-animations
git pull --ff-only origin feature/VA-029-game-animations

gradle :app:compileDebugKotlin :app:compileReleaseKotlin \
  :app:testDebugUnitTest :app:assembleDebug :app:assembleRelease \
  --console=plain --no-daemon --max-workers=2

# Com emulador/dispositivo Android ligado:
gradle :app:connectedDebugAndroidTest \
  --console=plain --no-daemon --max-workers=2
```

## Checklist de QA

- [ ] Builds Debug/Release e testes unitários passam (não foram executados nesta sessão).
- [ ] `GameMotionUiTest`, `ParentalSettingsUiTest` e regressão dos testes instrumentados passam.
- [ ] Verificar que o controlo parental continua definido após fechar/abrir a aplicação.
- [ ] Alternar «Animações» durante uma atividade; o efeito é imediatamente interrompido ou removido.
- [ ] Android: em Opções de programador, colocar «Escala de duração da animação» a `0x`; confirmar ausência de movimento com preferência parental ligada.
- [ ] Android: testar escalas `0.5x`, `1x`, `2x`, desativar animações nas preferências e confirmar que prevalece.
- [ ] Verificar transições em atividades, alfabeto e cartões sem aceitar respostas de cartões antigos.
- [ ] Verificar estrelas/confetes no acerto, incentivo suave no erro e conclusão na última pergunta/cartão.
- [ ] Validar acessibilidade TalkBack: respostas, progresso, mensagens de feedback; evitar leituras duplicadas e controles fora de foco.
- [ ] Testar com letras ampliadas, orientação horizontal, tablets, modo claro/escuro e dispositivos lentos.
- [ ] Conferir impacto de `AnimatedContent` no estado de `SpeechControls` ao transitar entre perguntas.
- [ ] Abrir Pull Request, rever e integrar depois de aprovar o QA.

## Riscos / detalhes conhecidos

- O repositório não foi compilado nesta sessão; todas as alterações precisam do build do Codespaces.
- A escala `ANIMATOR_DURATION_SCALE` é respeitada para **desativar** as animações quando `0`; as durações locais não são multiplicadas pelo valor de escala, mas o Android/Compose pode aplicar a escala global aos relógios de animação.
- O conteúdo pedagógico ainda possui alguns emojis de origem, fora do âmbito VA-029; ver #63.
- A branch parte de VA-028, que também tem QA pendente; evitar merge isolado sem validar a cadeia de branches.
