# VA-033 — 111 perguntas e ordem aleatória em «Magia na ponta do dedo»

**Issue:** [#75](https://github.com/Victor-Candeias/VCCAprender/issues/75)  
**Branch:** `feature/VA-033-magic-111-random-questions` (base `main`)  
**Estado:** implementação publicada; validação automatizada e QA de dispositivo a confirmar.

## Implementação
- Os IDs originais `magic-01` a `magic-51` e as respetivas respostas permanecem intactos.
- `FingerMagicExpansion.questions` adiciona 60 perguntas, `magic-52` a `magic-111`, repartidas igualmente por cinco categorias: universo, automóveis, clubes de futebol, somas e conhecimento geral.
- Cada nova pergunta tem **três opções distintas**, `correctIndex` entre `0` e `2`, explicação própria em português de Portugal e metadados `MagicCategory`.
- `MagicPicture` contém os novos identificadores visuais. `MagicExpansionArtwork.kt` renderiza recursos **offline e originais** para os novos itens; os cartões de marcas e clubes recorrem a símbolos **genéricos** e texto, sem importar logótipos/emblemas oficiais. Imagens de terceiros não foram adicionadas.
- O jogo identifica a categoria no ecrã e continua a apresentar a numeração de progresso com base no total real (`111`). O ecrã inicial já obtém a contagem dinamicamente de `FingerMagicContent.questions.size`.
- A lista estática `FingerMagicContent.questions` mantém ordem canónica para testes, catálogo e auditoria.

## Baralho aleatório (sem repetição)
- `FingerMagicDeck.newSession(random)` cria um **snapshot baralhado** de todas as perguntas, usando `shuffled(random)`.
- `FingerMagicViewModel` cria esse snapshot uma única vez no construtor e um novo snapshot em `restart()`; não há sorteio dentro de `@Composable`.
- `Random` pode ser injetado para reproduzir ordens determinísticas em testes.
- `FingerMagicSession.next()` preserva a mesma lista durante toda a partida.
- Cada ID surge uma vez por partida, mas pode surgir noutra posição na partida seguinte.
- Como o `correctIndex` e as `options` pertencem ao objeto `MagicQuestion`, embaralhar perguntas não troca as respetivas respostas.
- **Não se embaralharam as opções A/B/C**, de acordo com o âmbito aprovado. O botão **Jogar novamente** limpa também tentativas, sorrisos revelados e temporizadores.
- A orientação/recomposição não altera a ordem enquanto o mesmo `ViewModel` permanece vivo. Após morte do processo Android, a ordem não é atualmente recuperada; uma persistência opcional de sessão será um trabalho separado, se necessária.

## Testes
- `FingerMagicSessionTest` atualizado para 111 perguntas, IDs sequenciais, cinco categorias de 12 perguntas e conclusão ao fim de toda a sessão.
- `FingerMagicDeckTest` acrescenta cobertura de seeds reprodutíveis, ordens distintas, conteúdo sem duplicação, preservação de respostas e progressão até ao final.
- `FingerMagicUiTest` atualizado para a conclusão de 111 perguntas; acrescenta testes de restart com seed e visibilidade da categoria.
- O workflow `.github/workflows/android-ui-ci.yml` foi configurado para compilar Kotlin Debug e `AndroidTest`, executar testes JVM e gerar APK Debug em pushes para a branch. A assinatura Release utiliza uma keystore privada e deve ser testada separadamente num ambiente com credenciais válidas, sem as expor na CI.
- **A existência do workflow não garante que o build passou.** Consultar a execução mais recente do GitHub Actions.

### Executar em Codespaces / Android Studio

```bash
git switch feature/VA-033-magic-111-random-questions
git pull --ff-only origin feature/VA-033-magic-111-random-questions

# Na CI Linux usa-se VCC_RELEASE_STORE_FILE=/tmp/va033-ci-unconfigured-release.p12
# para impedir a resolução do caminho Windows existente em keystore.properties.
gradle :app:compileDebugKotlin :app:compileDebugAndroidTestKotlin \
  :app:testDebugUnitTest :app:assembleDebug \
  --console=plain --no-daemon --max-workers=2

# Apenas no ambiente de release com a keystore privada disponível:
gradle :app:assembleRelease --console=plain --no-daemon --max-workers=2

# Android ligado ou emulador iniciado:
gradle :app:connectedDebugAndroidTest \
  --console=plain --no-daemon --max-workers=2
```

## Checklist de QA pendente
- [ ] Build Debug, testes unitários e compilação AndroidTest a verde na última revisão da branch; **validar Release separadamente com keystore autorizada**.
- [ ] Testes instrumentados executados com sucesso num dispositivo ou emulador.
- [ ] Verificar imagens e legibilidade **de todas as novas opções** em telefones/tablets, retrato/paisagem e fontes a 200%.
- [ ] Rever pedagogicamente as 60 perguntas (em particular factos culturais/desportivos) e as respetivas explicações antes de distribuição.
- [ ] Confirmar a utilização correta das categorias, a reprodução TTS em pt-PT e a ausência de sons automáticos.
- [ ] Verificar que o menu mostra 111 perguntas e que o jogo comunica o total corretamente.
- [ ] Abrir várias partidas: primeira pergunta e ordem variadas; nunca repetir uma pergunta antes de concluir.
- [ ] Reiniciar depois de um sorriso, de tentativas erradas e de uma partida concluída; confirmar que o novo baralho fica limpo.
- [ ] Rodar o dispositivo durante uma pergunta respondida; verificar que a lista não muda.
- [ ] TalkBack/teclado/Switch Access: descrição das opções, toque prolongado/ativação acessível e feedback adequados.
- [ ] Rever se a recuperação após encerramento pelo sistema será necessária; atualmente, ao criar um novo ViewModel, inicia-se novo baralho.
- [ ] Rever um Pull Request para `main` e só integrar após QA.

## Decisões de produto
- Os 111 desafios são apresentados num só baralho, por agora. Filtrar por categoria/idade e guardar uma sessão para retoma poderão constituir futuros Work Items.
- As perguntas de somas utilizam `LearningTopic.NUMBERS` no progresso; as restantes usam `LearningTopic.WORDS`, mas mantêm a categoria própria na UI. Uma categorização histórica mais fina requer evolução do modelo de progresso.
- Para evitar uso indevido de logótipos e escudos, não foram incorporados elementos oficiais de marcas ou clubes.

## Nota de segurança fora de âmbito
O repositório principal inclui um `keystore.properties` versionado com valores de credenciais. Este ficheiro deverá ser retirado do controlo de versões e os segredos associados revistos/rodados por quem gere as chaves de assinatura. **A alteração VA-033 não modificou nem expôs os valores dessas credenciais**. A CI evita consumi-los para assinar qualquer APK.
