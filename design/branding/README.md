# VA-025 — Identidade visual da aplicação

## Imagem aprovada
Imagem promocional VCCAprender gerada na conversa ChatGPT de 2026-11-01.
Nome sugerido do ficheiro original: `vcc-aprender-banner.png`.

> O ficheiro PNG original tem de ser adicionado a `design/branding/vcc-aprender-banner.png`.
> O conector GitHub disponível nesta sessão só suporta escrita de ficheiros de texto, pelo que não foi possível enviar o binário PNG. Não substituir por imagem inventada.

## Integração
1. Adicionar o PNG original a `design/branding/vcc-aprender-banner.png`.
2. Criar um **ícone próprio**, quadrado e simplificado, a partir do logótipo/livro do banner. O banner completo, com texto e personagens, não deve ser usado diretamente como ícone Android.
3. Exportar ícones adaptativos Android (foreground/background) e ícones legacy para as densidades necessárias, via Android Studio Image Asset.
4. Colocar recursos em `app/src/main/res/mipmap-*` e `mipmap-anydpi-v26`.
5. Atualizar o `AndroidManifest.xml` com `android:icon` e `android:roundIcon`.
6. Opcionalmente integrar o banner num ecrã de boas-vindas com acessibilidade e escalonamento responsivo.
7. Validar contraste, recorte, tamanhos, nome correto `VCCAprender`, Android 8+ e modo claro/escuro.

## Estado
Branch e especificação criadas. Importação do PNG e implementação de ícones ainda pendentes.
