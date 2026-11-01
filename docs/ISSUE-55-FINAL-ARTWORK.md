# Issue #55 — Ilustrações finais dos temas

## Entregue
- Sete ilustrações vetoriais originais e offline desenhadas com Compose Canvas, uma por tema: letras (A), números (1), cores (círculos), formas (geometria), animais (urso), sons (notas) e palavras (livro).
- Cartões do ecrã inicial migrados de emojis para `TopicIllustration`.
- Sem novas dependências, fontes de emoji, imagens externas ou pedidos de rede.
- Descrição acessível por tema e teste instrumentado para os sete temas.
- Implementação: `app/src/main/java/com/vcc/aprender/ui/components/TopicIllustration.kt`.

## Testes pendentes
- [ ] `gradle :app:testDebugUnitTest :app:assembleDebug :app:assembleRelease --console=plain --no-daemon --max-workers=2`
- [ ] Com emulador ligado: `gradle :app:connectedDebugAndroidTest --console=plain --no-daemon --max-workers=2`
- [ ] Inspecionar os sete desenhos em telefones e tablets, com fontes ampliadas.
- [ ] Verificar TalkBack, contraste e legibilidade das ilustrações.
- [ ] Testar regressão dos cartões e navegação para cada tema.

## Limites
- Os novos desenhos substituem os emojis dos **cartões de temas do ecrã inicial**, não todos os emojis de perfis, cabeçalhos, feedback ou outros ecrãs.
- Ainda não existe pacote gráfico de branding/ícone Android definitivo integrado.
- Testes e build não foram executados por esta sessão; não foi criado PR nem efetuado merge.
