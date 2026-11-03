# VA-027 — Identidade visual infantil

Branch inicial: `feature/issue-55-colorful-ui-foundation`

Conclusão da implementação: `fix/VA-027-learning-artwork`

## Entregue
- Biblioteca `BrandArtwork.kt` com 17 símbolos vetoriais originais, renderizados offline em Compose Canvas: avatares (urso, raposa, panda, estrela), perfil, livro, escudo, som, troféu, gráfico, calendário, alvo, puzzle, incentivo, sucesso e adicionar.
- `FamilyHeader` e `FamilySection` mostram ícones vetoriais em vez de emojis nos perfis, definições parentais e relatórios.
- Seleção de avatar dos perfis mostra desenhos vetoriais. **Valores guardados no Room permanecem os mesmos emojis históricos** para evitar migração de dados.
- Feedback de atividades e respostas A/B/C apresenta ícones de sucesso/incentivo originais.
- Ecrãs de atividades e alfabeto apresentam agora a imagem específica de cada pergunta/palavra, em vez de uma imagem genérica do tema.
- Finalização do jogo de cartões apresenta troféu vetorial.
- Testes instrumentados de acessibilidade de ícones e compatibilidade de avatares; atualizadas as asserções de textos de perfil e feedback.

## Conclusão das ilustrações educativas

- 61 desenhos vetoriais originais em `app/src/main/res/drawable/learn_*.xml`: objetos, animais, formas, cores e símbolos educativos. Não precisam de rede nem de fontes emoji.
- Todas as 26 palavras do alfabeto, as atividades e as perguntas de leitura têm desenhos próprios. «Mesa» apresenta uma mesa; «Xilofone» apresenta um instrumento com lâminas e baquetas.
- Números de 1 a 10 apresentam exatamente a quantidade de estrelas pedida, em filas de até cinco, com descrição acessível da contagem.
- Cartões A/B/C e respetivas opções usam as mesmas ilustrações; a grelha cobre a imagem inteira. A descrição da imagem fica oculta da árvore de acessibilidade até todos os quadrados serem revelados.
- Perguntas sobre sons mantêm um símbolo de som neutro, sem mostrar o animal da resposta.
- `LearningIllustration` define chaves estáveis e descrições em português; `LearningPicture` reconhece também os símbolos antigos. As ilustrações textuais de cartões personalizados continuam suportadas.
- Um símbolo de cadeira num cartão personalizado continua a ser uma cadeira; a correção de «Mesa» aplica-se ao conteúdo educativo incorporado, através da chave `table`.
- Emojis educativos substituídos por chaves no catálogo. Os emojis de avatares persistidos mantêm-se apenas como identificadores compatíveis; a interface mostra vetores.
- Cabeçalho inicial usa um livro vetorial. Cabeçalhos e secções recebem `BrandSymbol` diretamente e não repetem os seus títulos como descrição de imagens decorativas.
- Panda, raposa, sucesso, troféu, puzzle e calendário têm desenhos distintos.
- Removido o campo emoji de feedback, que já não era usado pela interface.

Para regenerar os recursos e a folha de revisão:

```powershell
python tools/generate_learning_artwork.py
```

[Folha de revisão com as 61 imagens](../design/branding/learning-artwork.svg).

### Cobertura de regressões

- `LearningIllustrationTest`: cobertura de todos os conteúdos, contagens de estrelas, correspondência entre imagem e resposta, compatibilidade com símbolos antigos e texto personalizado.
- `LearningArtworkUiTest`: carregamento dos 61 vetores no Android, atualização das imagens, seleção de respostas, ocultação/revelação da descrição acessível e texto personalizado.
- Testes existentes de perfis, identidade visual e restante aplicação.
- Corrigidos imports inválidos de `assertExists` e uma referência a `ApplicationInfo.roundIcon`, campo não disponível na API pública, que impediam compilar os testes instrumentados. A referência do ícone redondo é agora verificada no Manifest compilado; mantém-se o carregamento dos recursos adaptativos normal e redondo.
- Corrigido o tema Android para API 26: barra de navegação azul com ícones claros. A barra clara com ícones escuros aplica-se apenas desde API 27, através de `values-v27`, corrigindo o erro `NewApi` encontrado pelo lint.
- Atualizadas asserções antigas para feedback com imagens decorativas, percentagens repetidas nas secções do progresso e seleção do ícone redondo pelo Android. Os testes mantêm verificações do texto acessível, das ações e das referências dos ícones no Manifest.

## Comandos de validação
```bash
git switch fix/VA-027-learning-artwork
gradle :app:compileDebugKotlin :app:compileReleaseKotlin :app:testDebugUnitTest :app:assembleDebug :app:assembleRelease --console=plain --no-daemon --max-workers=2
# Requer dispositivo ou emulador:
gradle :app:connectedDebugAndroidTest --console=plain --no-daemon --max-workers=2
```

## Testes e trabalhos pendentes
- [x] Compilar Debug/Release e executar testes unitários: 87 testes, sem falhas.
- [x] Executar lint: zero erros; 20 avisos, sobretudo versões de dependências/target SDK existentes.
- [x] Executar testes instrumentados, incluindo `BrandArtworkUiTest`, `GameDesignSystemUiTest`, `ProfileScreenUiTest`: 36 testes, sem falhas, no Android 15/API 35.
- [ ] Confirmar criação/edição de perfis, seleção de avatar e perfis antigos após atualização.
- [ ] Validar visualmente todos os ícones em Android 8+ e tablets, tamanhos de letra elevados, modo paisagem e TalkBack real.
- [x] Criar desenhos específicos por palavra/pergunta em substituição das ilustrações emoji de origem.
- [x] Converter emojis de perguntas/opções e textos secundários em ilustrações, preservando avatares antigos e conteúdo personalizado.
- [x] Criar ícone de launcher e splash definitivos (VA-028, já integrado na base desta branch).
- [ ] Rever contraste, foco e semântica de ícones decorativos; evitar leituras duplicadas de cabeçalhos pelo TalkBack.
- [ ] Preparar PR e revisão; não foi efetuado merge.

## Validação local — 1 de novembro de 2026

- Gradle 8.10.2 instalado em cache, JDK 17, compile/target SDK 35. O `local.properties` foi adaptado temporariamente para o SDK Windows e reposto após os comandos.
- `:app:assembleDebug`, `:app:assembleRelease`, `:app:testDebugUnitTest`, `:app:lintDebug` e compilação dos testes instrumentados concluídos com sucesso.
- `:app:connectedDebugAndroidTest`: 36 testes, zero falhas/erros/ignorados, no AVD FiaTipo3 com Android 15/API 35 e ecrã 1280×800. Inclui carregamento dos 61 vetores, seleção de respostas, perfis, layouts e semântica das imagens reveladas.
- No Android 17, o Espresso 3.6.1 existente falha em `InputManager.getInstance` antes de testar Compose. A execução aprovada nesta validação é a de Android 15; o suporte do executor a Android 17 exige trabalho separado.

**Estado:** implementação e validação automática de build/JVM/lint/instrumentação concluídas localmente. Revisão humana das ilustrações, TalkBack real e restante matriz de dispositivos continuam pendentes antes do fecho do item.
