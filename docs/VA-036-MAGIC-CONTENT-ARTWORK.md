# VA-036 — Remover futebol e automóveis; completar ilustrações

## Alterações
- Removidas as 12 perguntas de marcas de automóveis (`magic-64` a `magic-75`).
- Removidas as 12 perguntas de clubes de futebol (`magic-76` a `magic-87`).
- Removida a pergunta original `magic-18` sobre jogar futebol.
- Na pergunta `magic-106`, a opção ilustrada «Automóvel» foi substituída por «Livro».
- Excluídas as categorias CAR e FOOTBALL e os símbolos de marcas/clubes do catálogo.
- Total: **86 perguntas** (50 originais/curiosidades + 36 de espaço, somas e conhecimentos gerais).
- Preservados os IDs das perguntas que ficam (sem renumeração, para não alterar referências e progresso).
- Substituído o desenho genérico de expansão por ilustrações vetoriais próprias e diferentes para planetas, galáxias, instrumentos, sentidos, materiais, dias, equipamentos, estações, continentes, fenómenos meteorológicos, material escolar e animais. Os números continuam apresentados como algarismos grandes.
- A renderização é offline e mantém o texto descritivo em complemento à imagem.
- Ajustados os testes de contagem, baralho, progresso e ecrã de conclusão.

## Validação
Ainda é necessário correr os testes e verificar visualmente num emulador/dispositivo Android, sobretudo o tamanho e a legibilidade dos rótulos a 200%.

```bash
./gradlew :app:compileDebugKotlin :app:testDebugUnitTest :app:assembleDebug
```

Verificação adicional: percorrer as 86 perguntas e confirmar que cada opção corresponde visualmente à resposta/etiqueta, sem aparecer o símbolo de interrogação de fallback.
