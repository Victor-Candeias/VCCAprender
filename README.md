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

## VA-034 — Aplicação com um único jogo
Foram retiradas do ecrã inicial as entradas jogáveis **Letras, Números, Cores, Formas, Animais, Sons e Palavras**. Nas configurações foram retirados **Leitura e reconhecimento de palavras**, **Descobre a Resposta A/B/C** (minijogo separado) e o **Editor de atividades** associado às cartas.

Estas funcionalidades deixam de ter rotas de navegação na aplicação. **Não se eliminam as opções A/B/C internas ao jogo Magia**, nem os dados persistentes dos perfis e histórico. Tipos de dados e classes legadas podem permanecer no código para compatibilidade e manutenção futura; a sua eventual eliminação física deverá ser realizada com validação de dependências e migrações.

## Documentação técnica
- [VA-033 — 111 perguntas e baralho aleatório](docs/VA-033-MAGIC-QUESTIONS-RANDOM.md)
- [VA-034 — Simplificação para um único jogo](docs/VA-034-ONLY-FINGER-MAGIC.md)

Sem ligação obrigatória à Internet, sem publicidade e com conteúdos em português de Portugal.
