# Tag 2 — Angriffslogik nachholen, Strategie vertiefen, Bot robust machen

Ziel des Tages: Jedes Team hat einen Bot mit Angriffslogik (Epic 2) UND mehreren
erkennbaren Verhaltenszuständen (mindestens Angriff + Flucht oder Angriff +
Patrouille) und hat ihn mehrfach gegen andere Bots getestet.

## 09:00–09:15 — Daily Standup

Format siehe [scrum-board.md](dozent/scrum-board.md): 3 Fragen pro Team, max. 2 Min. je Team.

## 09:15–10:45 — Arbeitsblock 1: Angriffslogik nachholen + Vertiefung Strategie

Tag 1 endete wegen des KI-Vortrags vor Epic 2 — dieser Block holt das nach, bevor
es weitergeht:

- Story 2.1 (Dauerfeuer feste Richtung) — einfacher Einstieg ins Schießen.
- Story 2.2 (Zielen auf Gegner in Sichtlinie) — anspruchsvoller, ggf. mit Dozenten-Hilfe pro Team.
- Story 1.3 (Flucht bei niedriger Gesundheit) nachholen, falls an Tag 1 noch nicht geschafft.
- Schnelle Teams: Story 2.3 (Verfolgen) direkt ergänzen.
- Zusätzlicher Puffer für sehr schnelle Teams (kein Vorgriff auf Epic 3): Story 2.4 (nicht ins Leere schießen), 2.5 (schwächsten Gegner in Sichtlinie zuerst), 2.6 (Rückzug beim Schießen vermeiden).
- Einstieg in Story 3.1 (Zustandsmaschine): Dozent erklärt kurz am Beispiel `bots/examples/FluchtBot.kt` (bereits fertig, als Referenz zeigbar — nicht die Musterlösung aus `docs/dozent/loesungen.md` direkt zeigen, Schüler sollen selbst probieren).
- Konzept "Zustand" verständlich machen: "Euer Bot verhält sich unterschiedlich, je nachdem in welcher Situation er gerade ist — wie eine Ampel, die je nach Zustand rot/gelb/grün zeigt."

## 10:45–11:00 Pause

## 11:00–12:00 — Arbeitsblock 2: Zustandsmaschinen (Epic 3)

Teamarbeit:
- Story 3.1 umsetzen (mind. 3 Zustände: Patrouille/Angriff/Flucht oder eigene Benennung).
- Story 3.2 (Zielpriorisierung) für Teams, die mehrere Gegner gleichzeitig behandeln wollen.
- Schnelle Teams: Story 3.4 (zeitgesteuerte Startphase) als weiterer Ausbau der Zustandsmaschine.
- Dozent achtet besonders auf Lesbarkeit: `when`-Ausdrücke statt verschachtelte `if`-Ketten, sprechende Namen für Zustände.

## 12:00–13:00 Mittagspause

## 13:00–14:00 — Arbeitsblock 3: Testduelle + Backlog-Grooming

- Jedes Team lässt seinen aktuellen Bot gegen mindestens 2 verschiedene Beispiel-Bots antreten (Story 4.2) — inklusive des ersten Testduells, das an Tag 1 wegen der verkürzten Zeit ggf. noch nicht stattfand.
- Kurzes Grooming am Board (siehe dozent/scrum-board.md): reicht das Backlog noch für den Rest des Tages und Tag 3? Teams, die schon alle Pflicht-Storys geschafft haben, nehmen sich Story 3.5 (Abklingzeit nach der Flucht) als weiteren Ausbau — keine Dozenten-Freigabe nötig, im Gegensatz zur Kür-Story 3.3. Wer danach immer noch Kapazität hat, formuliert erst dann eine Kür-Story (3.3).
- Dozent sammelt Beobachtungen: gibt es Bugs im Framework selbst (nicht im Schülercode), die noch behoben werden müssen, bevor Tag 3 startet?

## 14:00–14:45 — Arbeitsblock 4: Optional — Unit-Tests (Epic 4) / Feinschliff

- Wer mag: Story 4.1 (eigener Unit-Test) ausprobieren — guter Anlass, das an Tag 1/2 eingeführte Unit-Test-Konzept nochmal in einem für die Schüler relevanten Kontext zu üben.
- Wer nicht: Zeit für weiteren Feinschliff der Bot-Logik, weitere Testduelle, Vorbereitung auf Story 3.3 (Kür).
- Gegen Ende: Dozent bittet alle Teams, ihren finalen Tag-2-Stand zu committen und auf den eigenen Team-Branch zu pushen, und mergt die drei Pull Requests auf den Basis-Branch (siehe [git-github-basics.md](git-github-basics.md#so-läuft-es-in-unserem-praktikum-ab)) — Vorbereitung für Tag 3.

## 14:45–15:00 — Tagesrückblick

- Kurze Runde: aktueller Stand jedes Teams, was ist für Tag 3 noch offen?
- Hinweis auf Tag 3: Vormittag = letzter Feinschliff, Nachmittag = Turnier + Review.
