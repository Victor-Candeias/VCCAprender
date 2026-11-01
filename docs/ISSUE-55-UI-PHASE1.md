# Issue #55 — Fase 1: design system e ecrã inicial

## Implementado
- Paleta Material 3 colorida, com contraste pensado para leitura e cores reutilizáveis em `AprenderColors`.
- Hierarquia tipográfica para títulos, subtítulos, texto e botões.
- Ecrã inicial com painel de boas-vindas, cartões ilustrados com emojis por tema, áreas de toque de pelo menos 108 dp de altura e descrição acessível.
- Mantidos os callbacks de navegação e estados de loading/erro/sucesso.
- Sem dependências adicionais e sem acesso à rede.

## Por validar
- Compilar `assembleDebug` e executar testes unitários.
- Verificar layout em Android API 26, 30 e 35, em telemóveis e tablets.
- Validar TalkBack, contraste, fontes ampliadas, toque e navegação por todos os temas.
- Testar regressões nas restantes áreas, agora afetadas pela paleta global.
- Confirmar renderização de emojis nas versões Android suportadas.
- Substituir emojis por ilustrações vetoriais próprias e integrar a identidade visual do issue VA-025.
- Criar previews e testes Compose específicos para a nova home.

## Execução
```bash
git fetch origin
git switch feature/issue-55-colorful-ui-foundation
gradle :app:testDebugUnitTest :app:assembleDebug --no-daemon --max-workers=1
```

O repositório pode não conter `gradle/wrapper/gradle-wrapper.jar`; até corrigir, usar Gradle 8.9 instalado, como no workflow de release.

## Fases seguintes
2. Jogos, feedback e animações.
3. Perfis, definições e relatórios.
4. Layouts tablet, acessibilidade, testes instrumentados e validação final.
