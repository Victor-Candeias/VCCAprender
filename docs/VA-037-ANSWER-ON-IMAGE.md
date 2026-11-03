# VA-037 — Resposta diretamente nas imagens A/B/C

## Implementação
- Removida a fila de quadrados pretos. As três imagens são as superfícies de toque prolongado.
- A pressão tem a mesma duração de revelação (`FingerMagicContent.REVEAL_MILLIS`, 800 ms).
- Retirar o dedo antes de completar a pressão não conta como resposta.
- Cada pergunta aceita uma única resposta revelada. Após essa resposta, todas as imagens ficam bloqueadas.
- Acerto: smile feliz sobre a fotografia/ilustração escolhida, explicação e botão **Pergunta seguinte** (na última, **Concluir**).
- Erro: smile triste sobre a imagem escolhida; após `WRONG_VISIBLE_MILLIS` (3 segundos), smile feliz sobre a correta; só então aparece o botão **Pergunta seguinte**.
- O erro não transforma a tentativa em acerto: o progresso regista `solved=false`.
- Reiniciar, avançar e interromper gestos cancelam temporizadores pendentes.
- TalkBack/teclado mantêm a ação acessível sobre a imagem, em vez do quadrado.

## Alterações técnicas
- `FingerMagicSession`: `answered`, `showCorrectAnswer`, `canAdvance`, `revealCorrectAnswer()`, bloqueio das opções depois da primeira tentativa.
- `FingerMagicViewModel`: temporizador único para revelar automaticamente a resposta certa após o erro, em vez de esconder o smile errado.
- `FingerMagicScreen`: `MagicAnswerImage`, com imagem original sempre visível e smile sobreposto após a resposta.
- Testes unitários e instrumentados adaptados à regra de uma tentativa.

## Verificação necessária no dispositivo
1. Pressão curta na imagem: nenhuma tentativa contabilizada.
2. Pressão completa correta: smile feliz e botão para avançar.
3. Pressão completa errada: smile triste; opções bloqueadas; 3 segundos depois, smile feliz na correta; botão disponível.
4. Após reiniciar ou avançar, nenhuma marca de resposta anterior.
5. TalkBack, teclado, rotação, fonte a 200% e tablet.
6. Última pergunta certa e errada: **Concluir** termina o baralho.

### Build e testes
```bash
./gradlew :app:compileDebugKotlin :app:testDebugUnitTest :app:assembleDebug
./gradlew :app:connectedDebugAndroidTest
```

**Estado:** código e testes atualizados na branch; execução real do build e testes instrumentados ainda não confirmada.
