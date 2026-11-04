# VCCAprender — Magia na ponta dos dedos 🌟

A **VCCAprender** é uma aplicação Android educativa e offline, em português de Portugal, destinada a crianças dos 3 aos 8 anos. A experiência jogável da app foi simplificada: **existe apenas um jogo, «Magia na ponta dos dedos»**.

## Jogo
- **111 perguntas** com três opções ilustradas A/B/C; a criança descobre a resposta mantendo o dedo sobre um dos quadrados.
- Ordem aleatória no início de cada partida, **sem repetições** até concluir o baralho. **Jogar novamente** inicia outra sequência.
- Perguntas sobre animais, alimentos, universo, marcas de automóveis, clubes de futebol, contas simples e conhecimentos gerais, com explicações em pt-PT.
- Desenhos vetoriais e cartões originais, disponíveis **offline**; não são incluídos logótipos comerciais ou escudos oficiais.
- Toque prolongado de **800 ms** para revelar o sorriso ou uma carinha triste. As respostas erradas ocultam-se 3 segundos após levantar o dedo; a resposta correta mantém-se visível até avançar.
- Leitura opcional por voz e acesso com TalkBack/teclado, com feedback adequado.

## Acompanhamento e preferências
Na área **Configurações** estão disponíveis a mudança de perfil, a consulta do progresso e a área parental (áudio, velocidade da fala e animações). Perfis e histórico de respostas mantêm-se guardados no dispositivo.

✨ **«Magia na ponta do dedo»** tem uma entrada própria no ecrã inicial e **86 perguntas** em ordem aleatória, sem repetições por partida. São 50 questões originais e de curiosidades e 36 questões adicionais de universo, somas e conhecimentos gerais. Cada pergunta inclui três opções ilustradas A/B/C e uma explicação educativa. Mantém o dedo sobre uma das imagens durante cerca de 0,8 segundos para escolher. Só existe uma tentativa por pergunta: se acertares, surge um smile feliz; se falhares, as duas opções erradas desaparecem e fica apenas a imagem correta, sem smile. O botão «Pergunta seguinte» permite avançar após a revelação, sem nova tentativa. As ilustrações vetoriais são originais e estão incluídas na app, funcionando offline.

Estas funcionalidades deixam de ter rotas de navegação na aplicação. **Não se eliminam as opções A/B/C internas ao jogo Magia**, nem os dados persistentes dos perfis e histórico. Tipos de dados e classes legadas podem permanecer no código para compatibilidade e manutenção futura; a sua eventual eliminação física deverá ser realizada com validação de dependências e migrações.

## Documentação técnica
- [VA-033 — 111 perguntas e baralho aleatório](docs/VA-033-MAGIC-QUESTIONS-RANDOM.md)
- [VA-034 — Simplificação para um único jogo](docs/VA-034-ONLY-FINGER-MAGIC.md)

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


## VA-038 — Áudio sem sobreposição e resposta incorreta sem smiles

Os botões de ouvir a pergunta e o resultado ficam desativados durante a reprodução de qualquer um dos textos e só voltam a ser ativados quando o motor TTS notifica o fim, a interrupção ou um erro. Ao falhar uma questão, as duas opções incorretas desaparecem imediatamente e apenas a opção correta continua visível, sem mostrar smiles. A criança continua limitada a uma tentativa e pode avançar logo para a pergunta seguinte. Num acerto, mantém-se o smile feliz.
