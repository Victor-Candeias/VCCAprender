
#!/usr/bin/env bash
set -euo pipefail

# Diretoria onde está o script (raiz do projeto)
PROJECT_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$PROJECT_ROOT"

# Validar mensagem do commit
if [ $# -eq 0 ]; then
    echo "ERRO: Indica a mensagem do commit."
    echo "Utilização: ./push.sh \"Mensagem do commit\""
    exit 1
fi

COMMIT_MESSAGE="$*"
BRANCH="$(git branch --show-current)"

if [ -z "$BRANCH" ]; then
    echo "ERRO: Não existe uma branch ativa."
    exit 1
fi

echo "======================================"
echo " VCC Fala & Aprende - Git Push"
echo "======================================"
echo "Branch: $BRANCH"
echo "Commit: $COMMIT_MESSAGE"
echo ""

echo "[1/3] Adicionar ficheiros..."
git add .

if git diff --cached --quiet; then
    echo "Não existem alterações para commit."
    exit 0
fi

echo "[2/3] Criar commit..."
git commit -m "$COMMIT_MESSAGE"

echo "[3/3] Enviar para GitHub..."
git push -u origin "$BRANCH"

echo ""
echo "======================================"
echo " PUSH CONCLUÍDO"
echo "======================================"
