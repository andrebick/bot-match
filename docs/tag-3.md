# Tag 3 — Feinschliff, Turnier, Review

Ziel des Tages: Alle drei Team-Bots sind beim Dozenten integriert, ein Battle-Royale-Finale läuft, der Sprint wird mit Review und Retrospektive abgeschlossen.

## 09:00–09:15 — Daily Standup

Letztes Daily des Sprints. Fokus: "Was fehlt noch bis zur finalen Bot-Version (Story 5.1)?"

## 09:15–10:45 — Arbeitsblock 1: Feinschliff/Bugfixing

- Letzte Anpassungen an der eigenen Bot-Logik.
- Story 5.1 (Finale Bot-Version festlegen) abschließen: `teamXBots`-Liste enthält genau die Bots, die antreten sollen.
- Jedes Team committet und pusht seinen finalen Stand auf den eigenen Team-Branch (falls nicht schon geschehen).

## 10:45–11:00 Pause

## 11:00–12:00 — Arbeitsblock 2: Integration

- Dozent mergt die drei finalen Pull Requests (Team-Branches → Basis-Branch, siehe [`docs/git-github-basics.md`](git-github-basics.md#so-läuft-es-in-unserem-praktikum-ab)) und führt danach `./gradlew build` auf dem Basis-Branch aus, behebt eventuelle Compile-Konflikte (z.B. doppelte Klassennamen zwischen Teams — sollte durch getrennte Packages `bots.teama/b/c` nicht vorkommen, aber sicherheitshalber prüfen).
- Während der Integration: Teams bereiten ihre Strategie-Kurzvorstellung vor (Story 5.2, 1-2 Sätze).
- Sobald Integration steht: kurzer technischer Probelauf (Dozent), damit während der Proberunden mit Schülern keine Überraschungen auftreten.

## 12:00–13:00 Mittagspause

## 13:00–14:00 — Arbeitsblock 3: Turnier-Proberunden

- Einzelne Testduelle zwischen den drei finalen Team-Bots (nicht das große Finale, sondern Aufwärmen — jedes Team sieht, wie sein Bot gegen die anderen abschneidet, bevor es "zählt").
- Bei Bedarf: letzte kleine Anpassungen, falls ein offensichtliches Problem auffällt (z.B. Bot bewegt sich nur in eine Ecke und bleibt dort stecken).

## 14:00–14:45 — Arbeitsblock 4: Finale — Battle Royale

- Alle drei (oder mehr, falls Teams mehrere Bots eingetragen haben) Team-Bots gleichzeitig in der App auswählen, Speed moderat einstellen (z.B. 300-500ms, gut zum Zuschauen).
- Match laufen lassen, gemeinsam zuschauen und kommentieren.
- Bei Bedarf mehrere Runden (Battle Royale ist nicht deterministisch bei zufälligem Bot-Verhalten wie `RandomBot`-Anteilen — ein zweiter Durchlauf zeigt, ob das Ergebnis Zufall oder Strategie war).

## 14:45–15:00 — Sprint Review + Retrospektive

**Sprint Review (siehe [scrum-board.md](dozent/scrum-board.md)):**
- Jedes Team stellt seine Strategie kurz vor (Story 5.2).
- Kurzes Feedback aus der Runde.

**Retrospektive:**
- Zwei Fragen, Antworten auf Post-its ans Board: "Was lief gut?" / "Was würden wir nächstes Mal anders machen?"
- Kurze gemeinsame Sichtung, Praktikum-Abschluss.
