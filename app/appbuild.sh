
#!/usr/bin/env bash
set -euo pipefail

# Obter a raiz do projeto (pasta acima de app)
PROJECT_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

cd "$PROJECT_ROOT"

echo "======================================"
echo " VCC Aprende - Build Android"
echo "======================================"
echo "Diretoria: $PROJECT_ROOT"

echo ""
echo "[1/3] Atualizar referências do Git..."
git pull

echo ""
echo "[2/3] Preparar Gradle..."

if [ ! -f "./gradlew" ]; then
    echo "ERRO: gradlew não encontrado em $PROJECT_ROOT"
    exit 1
fi

chmod +x ./gradlew

echo ""
echo "[3/3] Limpar, testar e compilar..."
./gradlew clean test assembleDebug

echo ""
echo "======================================"
echo " BUILD SUCCESSFUL"
echo "======================================"
echo "APK: $PROJECT_ROOT/app/build/outputs/apk/debug/app-debug.apk"
