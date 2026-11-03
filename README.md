🌟 **VCC Aprender — Aprender, Brincar e Descobrir!** 🎨🚀

Transforma cada momento numa aventura de aprendizagem! Desenvolvida para crianças dos 3 aos 8 anos, a VCC Aprender combina diversão e educação através de jogos interativos, desafios e atividades coloridas.

🔤 Descobre letras e palavras, 🔢 explora números, 🎨 aprende cores e formas, 🐾 conhece animais e sons e 🧩 desenvolve a memória, a lógica e o raciocínio.

😄 Com o divertido jogo **«Descobre a Resposta»**, as crianças escolhem entre três imagens e revelam smiles surpresa ao tocar nos quadrados mágicos!

✨ **«Magia na ponta do dedo»** tem uma entrada própria no ecrã inicial e 111 perguntas em ordem aleatória sem repetições por partida, cada uma com três imagens/opções A/B/C e três quadrados: 20 perguntas originais, 31 perguntas de curiosidades e 60 novas perguntas sobre universo, automóveis, futebol, somas e conhecimento geral (animais, alimentos, corpo humano, espaço e objetos) inspiradas nas cartas do jogo físico «Magia na Ponta do Dedo», com ilustrações vetoriais próprias. Mantém o dedo num quadrado durante 2 segundos para revelar a carinha. Nas respostas erradas, a cara triste fica visível enquanto manténs o dedo e durante mais 3 segundos depois de o levantares; depois volta a ficar tapada e podes tentar outra resposta. O sorriso da resposta correta permanece visível e permite avançar com «Seguinte». O jogo inclui ilustrações vetoriais offline, leitura opcional da pergunta, registo do progresso por perfil e ativação acessível por TalkBack ou teclado.

🏆 Inclui recompensas, leitura em voz alta, desafios adaptados à idade e acompanhamento da evolução pelos pais.

💡 **100% offline, sem publicidade e com conteúdos em português de Portugal.**

**VCC Aprender — Pequenas descobertas, grandes conquistas!** 🌈


### VA-033 — Magia na ponta do dedo (111 perguntas)

As 111 perguntas são embaralhadas uma única vez no início da partida e novamente ao escolher **Jogar novamente**. A ordem mantém-se estável durante a sessão; cada questão aparece apenas uma vez. As respostas mantêm a respetiva posição A/B/C e cada pergunta possui uma explicação educativa. Os novos temas usam figuras vetoriais e cartões de texto **offline**, sem logótipos oficiais de marcas ou clubes. A opção de áudio e os controlos de acessibilidade existentes continuam disponíveis. O progresso apresentado é baseado na posição na partida e não no identificador fixo da pergunta.

A validação pedagógica e visual dos novos conteúdos encontra-se documentada em `docs/VA-033-MAGIC-QUESTIONS-RANDOM.md`.

## VA-035 — Jogos como APKs independentes (Plugin SDK v1)

A VCCAprender passa a atuar como **aplicação-base**. Para além de «Magia na ponta dos dedos», permite descobrir e executar jogos Android instalados separadamente e assinados com o **mesmo certificado** do host. O novo módulo `game-plugin-sdk` define o contrato de execução e resultados; o módulo `sample-game-plugin` contém um jogo funcional de exemplo.

A descoberta usa visibilidade limitada de pacotes Android, Intents **explícitos** e permissão de assinatura. A aplicação-base valida o jogo antes de abrir, verifica o resultado e guarda o progresso por perfil, através de migração Room v4→v5 não destrutiva. Não existe carregamento de código externo na aplicação-base.

Um workflow Debug compila SDK, host e APK exemplo; um workflow Release independente permite assinar o plugin com os **mesmos GitHub Secrets** usados para assinar a VCCAprender.

**Nota:** a migração do jogo Magic integrado para um APK externo é uma fase posterior; os perfis e as 111 perguntas atuais permanecem funcionais.

[Guia de arquitetura, criação de plugins e testes](docs/VA-035-ANDROID-GAME-PLUGINS.md) · [Work Item #84](https://github.com/Victor-Candeias/VCCAprender/issues/84)
