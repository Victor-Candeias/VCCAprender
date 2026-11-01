# VA-027 — Identidade visual infantil

Branch: `feature/issue-55-colorful-ui-foundation`

## Entregue
- Biblioteca `BrandArtwork.kt` com 17 símbolos vetoriais originais, renderizados offline em Compose Canvas: avatares (urso, raposa, panda, estrela), perfil, livro, escudo, som, troféu, gráfico, calendário, alvo, puzzle, incentivo, sucesso e adicionar.
- `FamilyHeader` e `FamilySection` mostram ícones vetoriais em vez de emojis nos perfis, definições parentais e relatórios.
- Seleção de avatar dos perfis mostra desenhos vetoriais. **Valores guardados no Room permanecem os mesmos emojis históricos** para evitar migração de dados.
- Feedback de atividades e respostas A/B/C apresenta ícones de sucesso/incentivo originais.
- Ecrãs de atividades e alfabeto utilizam ilustrações vetoriais por tema. A ilustração específica de cada pergunta foi substituída por imagem do tema: validar a adequação pedagógica e restaurar ilustrações específicas sem emojis quando houver recursos.
- Finalização do jogo de cartões apresenta troféu vetorial.
- Testes instrumentados de acessibilidade de ícones e compatibilidade de avatares; atualizadas as asserções de textos de perfil e feedback.

## Comandos de validação
```bash
git switch feature/issue-55-colorful-ui-foundation
git pull --ff-only origin feature/issue-55-colorful-ui-foundation
gradle :app:compileDebugKotlin :app:compileReleaseKotlin :app:testDebugUnitTest :app:assembleDebug :app:assembleRelease --console=plain --no-daemon --max-workers=2
# Requer dispositivo ou emulador:
gradle :app:connectedDebugAndroidTest --console=plain --no-daemon --max-workers=2
```

## Testes e trabalhos pendentes
- [ ] Compilar Debug/Release e executar testes unitários após VA-027 (não executados nesta sessão).
- [ ] Executar testes instrumentados, incluindo `BrandArtworkUiTest`, `GameDesignSystemUiTest`, `ProfileScreenUiTest`.
- [ ] Confirmar criação/edição de perfis, seleção de avatar e perfis antigos após atualização.
- [ ] Validar visualmente todos os ícones em Android 7+ e tablets, tamanhos de letra elevados, modo paisagem e TalkBack.
- [ ] Confirmar que o conteúdo pedagógico continua a permitir reconhecer os objetos das perguntas: criar desenhos específicos por palavra/pergunta em substituição das ilustrações emoji de origem.
- [ ] Converter os restantes emojis embebidos em dados de perguntas/opções e eventuais textos secundários em ilustrações sem perda de conteúdo.
- [ ] Criar ícone de launcher e splash definitivos (VA-028).
- [ ] Rever contraste, foco e semântica de ícones decorativos; evitar leituras duplicadas de cabeçalhos pelo TalkBack.
- [ ] Preparar PR e revisão; não foi efetuado merge.

**Estado:** código e documentação publicados, sem build nem QA executados por esta sessão.
