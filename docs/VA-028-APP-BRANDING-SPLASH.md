# VA-028 — Ícone Android, ícone temático e Splash Screen

Issue: [#64](https://github.com/Victor-Candeias/VCCAprender/issues/64)  
Branch: `feature/VA-028-app-branding-splash`  
Base: `feature/issue-55-colorful-ui-foundation`

## Implementação

### Logótipo
Criado símbolo vetorial original e simples: **livro aberto + estrela**. Os recursos respeitam a zona segura de um Adaptive Icon e as cores do tema Compose:

- Azul `#2156B5`: fundo do launcher e Splash Screen
- Amarelo `#FFC44D`: estrela
- Branco/azul-claro: páginas do livro
- Azul-escuro `#17315C`: lombada e detalhes

**Importante:** estes vetores foram desenhados para o VA-028 e **não são uma importação nem uma reprodução comprovada do banner original do VA-025**. A imagem PNG promocional do Issue #50 continua por integrar. Validar visualmente o símbolo com o banner aprovado antes de o considerar o logótipo definitivo.

| Recurso | Localização |
|---|---|
| Foreground | `app/src/main/res/drawable/ic_launcher_foreground.xml` |
| Ícone monocromático Android 13+ | `app/src/main/res/drawable/ic_launcher_monochrome.xml` |
| Ícone de compatibilidade | `app/src/main/res/drawable/ic_launcher_legacy.xml` |
| Adaptive Icon | `app/src/main/res/mipmap-anydpi-v26/ic_launcher.xml` |
| Adaptive round icon | `app/src/main/res/mipmap-anydpi-v26/ic_launcher_round.xml` |
| Fallback launcher | `app/src/main/res/mipmap-anydpi/ic_launcher.xml` |
| Fallback round | `app/src/main/res/mipmap-anydpi/ic_launcher_round.xml` |
| Cores Android | `app/src/main/res/values/colors.xml` |

O projeto usa `minSdk = 26` (Android 8.0). No Android 8+, os recursos em `mipmap-anydpi-v26` prevalecem; os vetores em `mipmap-anydpi` fornecem compatibilidade adicional. O sistema Android aplica a máscara do ícone adaptativo; no Android 13+, launchers compatíveis podem aplicar o modo monocromático/temático.

### Splash Screen
- Biblioteca `androidx.core:core-splashscreen:1.0.1`
- `Theme.VCCAprender.Splash` em `res/values/styles.xml`
- Fundo azul e ícone central com as páginas e a estrela
- `postSplashScreenTheme` define `Theme.VCCAprender`, que acolhe a UI Jetpack Compose
- `MainActivity.installSplashScreen()` é chamado **antes** de `super.onCreate`
- Splash termina com o primeiro frame de conteúdo; não foram adicionados timers, `Thread.sleep` nem ecrãs artificiais
- AndroidManifest referencia `android:icon`, `android:roundIcon` e o tema de arranque na `MainActivity`
- Nome visível do launcher alinhado para `VCCAprender`

O ícone é estático; o termo `windowSplashScreenAnimatedIcon` é o nome da propriedade Android, não significa que exista uma animação.

## Testes de build (pendentes nesta sessão)

```bash
git switch feature/VA-028-app-branding-splash
git pull --ff-only origin feature/VA-028-app-branding-splash

gradle :app:compileDebugKotlin :app:compileReleaseKotlin \
  :app:testDebugUnitTest :app:assembleDebug :app:assembleRelease \
  --console=plain --no-daemon --max-workers=2

# Com emulador ou telemóvel Android:
gradle :app:connectedDebugAndroidTest \
  --console=plain --no-daemon --max-workers=2
```

Os testes instrumentados `LauncherBrandingTest` verificam: recursos `ic_launcher` e `ic_launcher_round` adaptativos, `applicationInfo` do Manifest, tema da `MainActivity`, presença dos vetores foreground/monocromático e a cor de fundo. **Não substituem inspeção visual** da máscara/recorte e da Splash Screen em dispositivos reais.

## Matriz de QA manual

| Plataforma | Verificações | Estado |
|---|---|---|
| Android 8 (API 26) | Arranque frio, Adaptive Icon, retorno à UI Compose | Pendente |
| Android 11 (API 30) | Splash via AndroidX, ícone quadrado/redondo | Pendente |
| Android 12 (API 31/32) | Splash nativa, cores, tempo até primeira UI, sem piscar | Pendente |
| Android 13+ (API 33+) | Ícone temático monocromático em launcher compatível | Pendente |
| Android 15/16 (API 35/36) | Splash e recorte, orientação, sistema claro/escuro | Pendente |
| Tablets / launchers | Legibilidade do livro/estrela em tamanho reduzido, ícones redondos | Pendente |
| Acessibilidade | Contraste, localização do rótulo VCCAprender, ausência de informação indispensável só no ícone | Pendente |

Para observar o **arranque frio** (com APK debug já instalado):

```bash
adb shell am force-stop com.vcc.aprender
adb shell am start -n com.vcc.aprender/.MainActivity
```

Para observar o modo monocromático, ativar «Ícones temáticos» num launcher que suporte a funcionalidade. Nem todos os launchers oferecem essa opção.

## Critérios para fechar o Issue #64
- [x] Recursos vetoriais, adaptativos, redondos e monocromáticos presentes no repositório
- [x] Manifest e temas Android atualizados
- [x] Splash Screen AndroidX integrado sem atraso artificial
- [x] Teste de verificação dos recursos criado
- [ ] Build Debug/Release e testes automatizados executados com sucesso
- [ ] Validação em dispositivos reais/emuladores e launchers
- [ ] Confirmação visual de consistência com o banner do VA-025
- [ ] PR, revisão e merge após aprovação

## Limitações / próximos passos
1. O banner binário original de VA-025 não foi enviado para esta branch; não foi criado um PNG inventado.
2. Não foram criados PNGs `mipmap-hdpi`, etc.: o ícone é **100% vetorial**, adequado ao `minSdk=26` e ao mecanismo de ícones adaptativos. Caso seja necessária uma exportação raster específica para uma loja, gerar via Android Studio Image Asset.
3. Não foi executado build local ou instrumentado nesta sessão.
4. A branch parte de uma branch de desenvolvimento que inclui alterações VA-027 ainda por validar. Ao preparar PR para `main`, rever dependências e histórico ou integrar primeiro a branch-base.
