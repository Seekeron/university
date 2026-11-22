#!/usr/bin/env bash
set -euo pipefail
cd -- "$(dirname -- "$0")"
python3 setup-local.py
docker compose up -d --wait
exec ./gradlew --console=plain bootRun
