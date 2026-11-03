🌟 **VCC Aprender — Aprender, Brincar e Descobrir!** 🎨🚀

Transforma cada momento numa aventura de aprendizagem! Desenvolvida para crianças dos 3 aos 8 anos, a VCC Aprender combina diversão e educação através de jogos interativos, desafios e atividades coloridas.

🔤 Descobre letras e palavras, 🔢 explora números, 🎨 aprende cores e formas, 🐾 conhece animais e sons e 🧩 desenvolve a memória, a lógica e o raciocínio.

😄 Com o divertido jogo **«Descobre a Resposta»**, as crianças escolhem entre três imagens e revelam smiles surpresa ao tocar nos quadrados mágicos!

✨ **«Magia na ponta do dedo»** tem uma entrada própria no ecrã inicial e **86 perguntas** em ordem aleatória, sem repetições por partida. São 50 questões originais e de curiosidades e 36 questões adicionais de universo, somas e conhecimentos gerais. Cada pergunta inclui três opções ilustradas A/B/C e uma explicação educativa. Mantém o dedo sobre uma das imagens durante cerca de 0,8 segundos para escolher. Só existe uma tentativa por pergunta: se acertares, surge um smile feliz; se falhares, surge o smile triste e, cerca de 3 segundos depois, a aplicação revela automaticamente o smile feliz na imagem correta. O botão «Pergunta seguinte» permite avançar após a revelação, sem nova tentativa. As ilustrações vetoriais são originais e estão incluídas na app, funcionando offline.

🏆 Inclui recompensas, leitura em voz alta, desafios adaptados à idade e acompanhamento da evolução pelos pais.

💡 **100% offline, sem publicidade e com conteúdos em português de Portugal.**

**VCC Aprender — Pequenas descobertas, grandes conquistas!** 🌈


### VA-036 — Magia na ponta do dedo (86 perguntas)

As 86 perguntas são embaralhadas uma única vez no início da partida e novamente ao escolher **Jogar novamente**. A ordem mantém-se estável durante a sessão; cada questão aparece apenas uma vez. As respostas mantêm a respetiva posição A/B/C e cada pergunta possui uma explicação educativa. As opções incluem ilustrações vetoriais **offline**. As questões sobre clubes de futebol e marcas de automóveis foram removidas. A opção de áudio e os controlos de acessibilidade existentes continuam disponíveis. O progresso apresentado é baseado na posição na partida e não no identificador fixo da pergunta.

A validação pedagógica e visual dos novos conteúdos encontra-se documentada em `docs/VA-033-MAGIC-QUESTIONS-RANDOM.md`.

## VA-035 — Jogos como APKs independentes (Plugin SDK v1)

A VCCAprender passa a atuar como **aplicação-base**. Para além de «Magia na ponta dos dedos», permite descobrir e executar jogos Android instalados separadamente e assinados com o **mesmo certificado** do host. O novo módulo `game-plugin-sdk` define o contrato de execução e resultados; o módulo `sample-game-plugin` contém um jogo funcional de exemplo.

A descoberta usa visibilidade limitada de pacotes Android, Intents **explícitos** e permissão de assinatura. A aplicação-base valida o jogo antes de abrir, verifica o resultado e guarda o progresso por perfil, através de migração Room v4→v5 não destrutiva. Não existe carregamento de código externo na aplicação-base.

Um workflow Debug compila SDK, host e APK exemplo; um workflow Release independente permite assinar o plugin com os **mesmos GitHub Secrets** usados para assinar a VCCAprender.

**Nota:** a migração do jogo Magic integrado para um APK externo é uma fase posterior; os perfis e as 86 perguntas atuais permanecem funcionais.

[Guia de arquitetura, criação de plugins e testes](docs/VA-035-ANDROID-GAME-PLUGINS.md) · [Work Item #84](https://github.com/Victor-Candeias/VCCAprender/issues/84)


## VA-037 — Responder diretamente nas imagens

Os quadrados pretos foram removidos. Cada imagem A/B/C recebe diretamente o toque prolongado, apresenta o smile de resultado sobre a própria figura e fica bloqueada após uma única resposta. Um erro revela automaticamente a opção correta e permite avançar; a lógica de progresso e acessibilidade foi atualizada.
