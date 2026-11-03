# VA-034 — Apenas «Magia na ponta dos dedos»

**Work Item:** [#81](https://github.com/Victor-Candeias/VCCAprender/issues/81)  
**Branch:** `feature/VA-034-only-finger-magic`  
**Origem:** `main`

## Novo mapa da aplicação

```text
Arranque -> Escolher/criar perfil -> Início
                                 +-> Magia na ponta dos dedos
                                 |   +-> 111 perguntas, baralho aleatório
                                 |   +-> A/B/C + toque prolongado + explicação + leitura
                                 |   +-> Jogar novamente / Voltar
                                 +-> Configurações
                                     +-> Mudar de perfil
                                     +-> Ver progresso
                                     +-> Área parental
                                         +-> Áudio e velocidade da voz
                                         +-> Animações
                                     +-> Voltar
```

## Remoção funcional
No ecrã inicial já **não há** cartões nem opções de navegação para as sete atividades clássicas: **Letras, Números, Cores, Formas, Animais, Sons e Palavras**.

As configurações deixam de apresentar **Leitura e reconhecimento de palavras**, o jogo separado **Descobre a Resposta A/B/C** e o respetivo **Editor de atividades**.

Rotas Compose eliminadas de `AprenderNavHost`: `TOPIC`, `ALPHABET`, `READING`, `CARDS`, `DECKS`, `CARDS_FILTERED` e `EDITOR`.

## O que é preservado
- `FingerMagicContent`, `FingerMagicDeck`, `FingerMagicSession`, `FingerMagicViewModel` e todo o ecrã do jogo com opções A/B/C internas.
- `ProfileScreen` e os perfis já criados.
- `ProgressRepository`, histórico e `ProgressScreen`.
- `ParentalSettingsScreen` e preferências de áudio/velocidade/animações.
- `LearningTopic` e o esquema persistente da Room, ainda utilizados para relacionar as respostas do jogo com o histórico. **Não executar alterações destrutivas de dados**.
- Suporte offline, TTS, acessibilidade e modos de visualização existentes.

## Implementação e testes
- `HomeScreen.kt`: ecrã principal sem `HomeUiState` e sem cartões de tópicos; apenas jogo e Configurações. O acesso deixa de depender do catálogo de atividades antigas.
- `SettingsScreen.kt`: mantém somente as opções de suporte ao jogo.
- `AprenderNavHost.kt`: grafo reduzido a seis destinos (`profiles`, `home`, `finger-magic`, `settings`, `parents`, `progress`).
- `FingerMagicScreen.kt`: nome do jogo no plural, conforme o requisito.
- `HomeScreenUiTest.kt`: testa acesso ao jogo e ausência das sete atividades.
- `SettingsScreenUiTest.kt`: testa ausência das funcionalidades eliminadas e disponibilidade das preferências remanescentes.
- `FingerMagicUiTest.kt`: mantém a verificação de acesso ao jogo com a nova assinatura do ecrã inicial.
- `README.md`: descrição do produto coerente com um único jogo.

## Verificação recomendada

```bash
gradle :app:compileDebugKotlin :app:testDebugUnitTest \
  :app:compileDebugAndroidTestKotlin :app:assembleDebug \
  --no-daemon --max-workers=2 --console=plain

# Com emulador/dispositivo configurado:
gradle :app:connectedDebugAndroidTest --no-daemon --max-workers=2
```

### Pontos de QA manual
- [ ] A única atividade do menu principal é «Magia na ponta dos dedos».
- [ ] As 7 atividades antigas não aparecem; o botão Configurações abre o menu simplificado.
- [ ] Nenhuma rota removida é acessível por caminhos de navegação existentes.
- [ ] Perfil novo, mudança de perfil, histórico e evolução mantêm o funcionamento.
- [ ] Jogo continua a apresentar 111 perguntas aleatórias, uma resposta correta por pergunta.
- [ ] TTS, TalkBack, teclado, retrato, paisagem e tablet.
- [ ] Atualização a partir de uma versão instalada não apaga a base de dados.

## Limite explícito
A remoção incide sobre as **funcionalidades utilizáveis e os respetivos destinos na app**. O código-fonte legado que compila mas já não é referenciado pela navegação não foi apagado nesta alteração. O esquema Room e tipos partilhados continuam intactos por compatibilidade com o progresso. Uma posterior limpeza física do código legado, testes obsoletos e recursos não utilizados só deve ser efetuada após análise de dependências e sem eliminar histórico existente.
