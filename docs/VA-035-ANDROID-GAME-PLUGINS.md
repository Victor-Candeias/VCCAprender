# VA-035 — VCCAprender como plataforma de jogos em APKs independentes

**Work Item:** [#84](https://github.com/Victor-Candeias/VCCAprender/issues/84)  
**Pull Request:** [#86](https://github.com/Victor-Candeias/VCCAprender/pull/86)  
**Branch:** `feature/VA-035-independent-game-plugins`

## Objetivo e limites do MVP

A **VCCAprender Core** conserva perfis de crianças, histórico, área parental e o jogo «Magia na ponta dos dedos» já existente. O host também descobre **jogos Android externos instalados como APKs separados**, desde que obedeçam ao contrato v1 e partilhem a mesma identidade de assinatura da aplicação-base. Os jogos externos nunca acedem à base de dados ou executam código no processo do host.

A arquitetura é **por instalação independente**, não usa `DexClassLoader`, armazenamento partilhado, download de .dex, scripts executáveis, instalação silenciosa nem `QUERY_ALL_PACKAGES`. Na primeira fase, a migração do jogo Magic de 111 perguntas para um APK externo **não** é incluída, evitando comprometer o progresso e a UI atuais.

```text
APK VCCAprender (com.vcc.aprender)
  Perfis / Progresso / Configurações / Magic integrado
  |
  +- PackageManager: descobre action PLAY limitada em <queries>
  +- checkSignatures(host, plugin) == SIGNATURE_MATCH
  +- verifica activity exportada, permission signature e metadata v1
  +- lançamento EXPLÍCITO: ComponentName(package, activity)
  |    envia request (gameId, profileId opaco, UUID de sessão, version)
  v
APK do jogo (com.vcc.aprender.samplegame ou outro)
  Activity exportada, permissão signature, mesmo certificado do host
  game-plugin-sdk: parse GameRequest
  UI e regras do jogo, dados e assets próprios
  |
  +- setResult(RESULT_OK, GameProtocol.createResultIntent(...))
  v
VCCAprender Core
  - verifica novamente assinatura e activity ainda confiável
  - valida UUID/ID/perfil/versão/correct <= total <= 10000
  - Room v5: external_game_sessions (sessionId UNIQUE)
  - progresso por perfil + feedback no ecrã inicial
```

## Módulos

| Módulo | Papel | APK/artefacto |
|---|---|---|
| `:app` | Core Android VCCAprender + Magic integrado + histórico | APK host |
| `:game-plugin-sdk` | Contrato Android v1 reutilizável em projetos separados | AAR |
| `:sample-game-plugin` | Quiz mínimo offline com 3 opções e retorno de resultado | APK independente |

Os jogos futuros podem viver noutros repositórios Gradle, desde que incluam uma versão publicada/compatível da biblioteca `game-plugin-sdk`.

## Contrato de manifesto do jogo

O APK independente precisa de uma `Activity` exportada, metadata v1 e da permissão de assinatura do host:

```xml
<uses-permission android:name="com.vcc.aprender.permission.PLAY_GAME"/>
<application ...>
    <activity
        android:name=".MyGameActivity"
        android:exported="true"
        android:permission="com.vcc.aprender.permission.PLAY_GAME">
        <intent-filter>
            <action android:name="com.vcc.aprender.plugin.action.PLAY"/>
            <category android:name="android.intent.category.DEFAULT"/>
        </intent-filter>
        <meta-data android:name="com.vcc.aprender.plugin.GAME_ID" android:value="com.minhaempresa.jogo"/>
        <meta-data android:name="com.vcc.aprender.plugin.GAME_TITLE" android:value="O meu jogo"/>
        <meta-data android:name="com.vcc.aprender.plugin.PROTOCOL_VERSION" android:value="1"/>
    </activity>
</application>
```

O `GAME_ID` tem 3–80 caracteres minúsculos, números, `.`, `_` e `-`, começando por uma letra. Deve ser estável entre versões. Um `GAME_ID` duplicado é excluído da descoberta.

## Android v11+ e confiança

- `app/src/main/AndroidManifest.xml` inclui `<queries>` **apenas** para `ACTION_PLAY`. Não é pedido acesso a toda a lista de apps.
- O host declara `com.vcc.aprender.permission.PLAY_GAME` como `protectionLevel="signature"` e pede a mesma permissão.
- O plugin declara essa permissão na Activity. A aceitação exige `PackageManager.checkSignatures(hostPackage, pluginPackage) == SIGNATURE_MATCH`, incluindo novamente na abertura e ao receber o resultado.
- **Esta v1 só permite jogos assinados pelo mesmo detentor da chave do host**. Não distribuir esta chave a terceiros; para parceiros independentes, será necessário desenhar uma política de certificados confiáveis, distribuição e revogação antes de dar suporte.
- O plugin recebe somente `gameId`, `sessionId` e um número interno de perfil. **Nunca** nome, idade, avatar, permissões de armazenamento nem dados médicos.
- A validação do resultado exige `RESULT_OK`, versão v1, jogo, UUID, perfil, limites de pontuação e certificado confiável. A unicidade `sessionId` na Room evita contagens duplicadas.
- O token UUID é um correlacionador imprevisível, **não substitui a autenticação por assinatura**. O jogo só devolve resultados ao chamador que o iniciou para resultado.
- Os resultados remetidos pelo plugin são aceites como informação educativa/autodeclarada de um **jogo confiável**, não prova criptográfica de que o utilizador respondeu corretamente.

## Compilar no Codespaces ou Android Studio

O projeto utiliza JDK 17, Gradle 8.9 e Android SDK 35.

```bash
git fetch origin
git switch feature/VA-035-independent-game-plugins
gradle :game-plugin-sdk:testDebugUnitTest \
  :app:compileDebugKotlin :app:testDebugUnitTest \
  :app:compileDebugAndroidTestKotlin :app:assembleDebug \
  :sample-game-plugin:assembleDebug \
  --no-daemon --max-workers=2 --console=plain
```

Os APKs Debug ficam em:

- `app/build/outputs/apk/debug/app-debug.apk`
- `sample-game-plugin/build/outputs/apk/debug/sample-game-plugin-debug.apk`

### Instalação e teste num dispositivo

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
adb install -r sample-game-plugin/build/outputs/apk/debug/sample-game-plugin-debug.apk
adb shell am force-stop com.vcc.aprender
adb shell monkey -p com.vcc.aprender 1
```

Escolher um perfil, verificar que o ecrã mostra «Magia na ponta dos dedos» e «Jogo exemplo: Descobre a cor», abrir o segundo, responder, voltar e consultar **Ver progresso**. Desinstalar o exemplo e regressar à VCCAprender: a entrada deixa de aparecer, mas os resultados anteriormente guardados permanecem associados ao perfil.

**Assinaturas Debug:** quando ambos os APKs são compilados no mesmo ambiente usando a keystore Debug padrão, devem partilhar a mesma assinatura. Uma VCCAprender **Release** assinada com a keystore real não vai reconhecer um sample plugin **Debug** assinado com outra chave — isto é intencional.

### Assinar o plugin Release com a mesma chave do host

O módulo do exemplo lê `VCC_RELEASE_STORE_FILE`, `VCC_RELEASE_STORE_PASSWORD`, `VCC_RELEASE_KEY_ALIAS` e `VCC_RELEASE_KEY_PASSWORD`, tal como o host. A keystore deve ficar fora do Git; em CI, restaurar `VCC_RELEASE_KEYSTORE_BASE64` apenas no armazenamento temporário do runner. O build Release do plugin deve utilizar o **mesmo certificado real** da VCCAprender Release.

```bash
gradle :sample-game-plugin:assembleRelease --no-daemon --console=plain
```

Antes de instalar, comparar as impressões digitais dos dois APKs:

```bash
apksigner verify --print-certs app/build/outputs/apk/release/*.apk
apksigner verify --print-certs sample-game-plugin/build/outputs/apk/release/*.apk
```

O certificado indicado para os dois APKs deve coincidir. Nunca incluir passwords ou a keystore no código ou nos artefactos descarregáveis.

## Persistência e compatibilidade

O esquema Room evoluiu de v4 para **v5** com a tabela `external_game_sessions`, índices `profileId` e `sessionId UNIQUE`, e `FOREIGN KEY ON DELETE CASCADE` para perfis. A migração preserva as tabelas anteriores. Os resultados do Magic integrado continuam no histórico de aprendizagem antigo; os novos jogos usam a tabela independente.

## Testes e revisão antes do merge

- [ ] CI SDK JVM, app Kotlin, app AndroidTest e APKs host + plugin Debug.
- [ ] Instalação de ambos os APKs Debug, jogo externo visível e sessão gravada.
- [ ] APK externo com outra assinatura **não aparece** na lista.
- [ ] APK desinstalado deixa de aparecer quando a app é retomada.
- [ ] Resultado trocado (nonce/ID/perfil), versão incompatível ou pontuação inválida **não** grava dados.
- [ ] Room v4→v5 conserva os perfis e progresso existentes.
- [ ] Histórico por perfil isolado, sem duplicados após reiniciar a Activity.
- [ ] TalkBack, fontes grandes e dispositivo offline.
- [ ] APKs Release assinados com o mesmo certificado e assinatura validada.

## Limitações conscientes do MVP
- Os APKs são instalados pelo utilizador/instalador do Android; não existe catálogo remoto, compra nem atualização automática.
- O SDK está versionado dentro do mesmo repositório para simplificar o arranque. Distribuí-lo como biblioteca externa versionada (Maven/AAR) será trabalho posterior.
- A política de confiança v1 exige **mesma assinatura** (controlo central de distribuição); parceiros externos com certificados diferentes não são suportados sem outra revisão de segurança.
- O jogo Magic ainda é integrado no APK do host. A migração do Magic para um APK separado será o próximo Work Item, com cuidados específicos para manter o histórico antigo.
