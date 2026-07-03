#!/bin/bash
# Erzeugt docs/pdf/backlog-landscape.pdf: 1 Story pro Seite, Querformat, große Schrift.
# Aufruf: docs/pdf/build/build-landscape.sh
set -euo pipefail
cd "$(dirname "$0")/../../.."

TMP=$(mktemp -t backlog-pdf).md
trap 'rm -f "$TMP"' EXIT
sed 's/→/->/g' docs/backlog.md > "$TMP"

pandoc "$TMP" \
  -o docs/pdf/backlog-landscape.pdf \
  --pdf-engine=xelatex \
  -V documentclass=extarticle \
  -V geometry:"a4paper,landscape,margin=1.5cm" \
  -V mainfont="Helvetica Neue" \
  -V fontsize=14pt \
  --include-in-header=docs/pdf/build/header-landscape.tex \
  --lua-filter=docs/pdf/build/storybox-landscape.lua

echo "docs/pdf/backlog-landscape.pdf erzeugt."
