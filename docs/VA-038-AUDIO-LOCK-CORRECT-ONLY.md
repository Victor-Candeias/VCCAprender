# VA-038 — Áudio sem sobreposição e imagem correta sem smile após erro

**Base:** `feature/VA-037-magic-answer-on-image`  
**Branch:** `feature/VA-038-audio-lock-correct-only`

## Comportamento implementado

### Áudio
- «Ouvir pergunta» e «Ouvir resultado» partilham o mesmo estado de reprodução da voz.
- Ao iniciar uma reprodução, ambos os botões ficam desativados até chegar `onDone`, `onError` ou `onStop` do Android Text-to-Speech.
- O próprio `AndroidSpeechEngine.speak()` rejeita outra leitura enquanto existir uma ativa, mesmo antes da recomposição dos botões.
- Cada leitura tem ID único. Um callback atrasado de uma leitura anterior não pode desbloquear uma leitura posterior.
- Desativar o áudio, sair do ecrã ou libertar o motor TTS interrompe e desbloqueia a leitura de forma segura.

### Uma tentativa por pergunta
- Mantém-se a pressão longa sobre a opção A/B/C para confirmar a escolha.
- Se acertar: smile feliz sobre a imagem selecionada e botão «Pergunta seguinte».
- Se falhar: desaparecem *imediatamente*, após a confirmação da tentativa, ambas as imagens incorretas. Só a imagem correta permanece, sem smile.
- Não existe smile triste nem smile feliz no cenário de erro.
- A criança não pode tentar outra vez, e «Pergunta seguinte» fica disponível logo após a resposta.
- O progresso regista o erro como resposta errada (`solved=false`).
- Após mudar de questão, as três imagens voltam a aparecer para a próxima pergunta.

## Ficheiros alterados
- `audio/AndroidSpeechEngine.kt`: callback de progresso, estado da reprodução.
- `audio/SpeechPlaybackGate.kt`: bloqueio atómico e proteção contra callbacks antigos.
- `ui/audio/SpeechControls.kt`: os dois botões ficam desativados durante qualquer leitura.
- `domain/magic/FingerMagicSession.kt`: conclusão imediata de pergunta, inclusive num erro.
- `ui/magic/FingerMagicViewModel.kt`: remove temporizador anterior de erro.
- `ui/magic/FingerMagicScreen.kt`: imagens incorretas ocultadas após erro, sem smiles.
- Testes: `SpeechPlaybackGateTest.kt`, `FingerMagicSessionTest.kt`, `FingerMagicUiTest.kt`.

## Testes recomendados no Android
1. Tocar repetidamente no botão ouvir pergunta durante leitura longa: não deve sobrepor áudio.
2. Durante essa leitura, tentar o botão ouvir resultado (se existir): deve estar desativado.
3. Reproduzir resposta e voltar a tentar pergunta: só permitir depois do fim da resposta.
4. Interromper a leitura desativando áudio ou saindo: sem bloqueio permanente, sem voz em segundo plano.
5. Selecionar opção errada por toque prolongado: só a imagem correta permanece, sem smiles; botão «Pergunta seguinte» disponível.
6. Selecionar opção correta: smile feliz e avançar.
7. Confirmar isolamento entre questões, testes com TalkBack, rotação e fontes ampliadas.

## Comandos de validação
```bash
git fetch origin
git switch feature/VA-038-audio-lock-correct-only
git pull --ff-only
./gradlew :app:testDebugUnitTest :app:assembleDebug
./gradlew :app:connectedDebugAndroidTest
```

**Nota de validação:** alterações publicadas e revista estaticamente a consistência das referências; build e testes no dispositivo ainda não executados neste ambiente.
