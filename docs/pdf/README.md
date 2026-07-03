# PDF-Export für `docs/`

Erzeugt druckbare PDFs aus den Markdown-Dateien in `docs/`. Quelle ist immer
das jeweilige Markdown — die Skripte hier verändern es nicht. Die fertigen
PDFs liegen direkt in diesem Ordner (Unterordner gespiegelt), die
Erzeugungs-Skripte in [`build/`](build/).

## Voraussetzungen

- [Pandoc](https://pandoc.org/) (getestet mit 3.10)
- LaTeX mit `xelatex` (z.B. via [MacTeX](https://tug.org/mactex/)) sowie den
  Paketen `tcolorbox`, `newunicodechar`, `enumitem` — bei einer vollständigen
  TeX-Live-/MacTeX-Installation bereits enthalten
- Font "Helvetica Neue" (auf macOS vorinstalliert)

## Varianten

### `build/build.sh` → `docs/pdf/backlog.pdf`

Hochformat, A4, jede Story in einer Box, mehrere Storys pro Seite,
Inhaltsverzeichnis. Zum normalen Durchlesen/Ausdrucken als Heft.

```bash
docs/pdf/build/build.sh
```

### `build/build-landscape.sh` → `docs/pdf/backlog-landscape.pdf`

Querformat, A4, genau eine Story pro Seite, große Schrift. Für Story-Karten
zum Ausschneiden/Auslegen (z.B. Scrum-Board).

```bash
docs/pdf/build/build-landscape.sh
```

### `build/build-docs.sh` → alle übrigen `docs/**/*.md` als PDF

Erzeugt für jede Markdown-Datei in `docs/` (außer `backlog.md`, das die beiden
Skripte oben abdecken) ein gleichnamiges PDF unter `docs/pdf/`, inkl.
Unterordnern (z.B. `docs/dozent/loesungen.md` → `docs/pdf/dozent/loesungen.pdf`).
Einfaches Hochformat mit Inhaltsverzeichnis, keine Story-Boxen. Baut dabei
immer alle betroffenen PDFs neu, unabhängig davon, ob sich das jeweilige
Markdown geändert hat.

```bash
docs/pdf/build/build-docs.sh
```

## Ordnerstruktur

```
docs/pdf/
  backlog.pdf              fertiges PDF, Hochformat
  backlog-landscape.pdf    fertiges PDF, Querformat (1 Story/Seite)
  <name>.pdf               je ein PDF pro sonstiger docs/<name>.md
  dozent/loesungen.pdf     Unterordner gespiegelt
  build/
    build.sh                   Baut backlog.pdf
    build-landscape.sh         Baut backlog-landscape.pdf
    build-docs.sh              Baut alle übrigen docs/**/*.md als PDF
    header.tex                 LaTeX-Box-Styling für die Hochformat-Variante
    header-landscape.tex       LaTeX-Box-Styling für die Querformat-Variante
    header-docs.tex            LaTeX-Styling für die einfachen Doku-PDFs
    storybox.lua                Pandoc-Filter: umschließt jede `###`-Story mit einer Box
    storybox-landscape.lua      Wie oben, zusätzlich ein Seitenumbruch pro Story
```

## Nach Änderungen an einer `.md`-Datei

Passendes Skript erneut ausführen — für `backlog.md` `build.sh` bzw.
`build-landscape.sh`, für alle anderen Dateien `build-docs.sh` (baut dabei
einfach alle PDFs neu, es gibt keine Datei-für-Datei-Erkennung).

