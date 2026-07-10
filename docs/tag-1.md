# Tag 1 — Framework kennenlernen, erste Bots

Ziel des Tages: Jedes Team hat einen lauffähigen Bot, der sich sichtbar bewegt und
mindestens eine einfache Überlebensregel (Epic 1) umsetzt.

**Achtung, kurzer Tag:** Ab 13:00 Uhr findet ein externer KI-Vortrag statt (nicht
Teil dieses Kurses). Tag 1 läuft daher nur 9:00–13:00 ohne Mittagspause. Angriffslogik
(Epic 2) und die ersten Testduelle wandern deshalb auf Tag 2, siehe [tag-2.md](tag-2.md).

## 09:00–09:15 — Sprint Planning

- Kurzer Rückblick: was war Tag 1/2 (Kotlin-Grundlagen)?
- Sprint-Ziel vorstellen: *"Bot mit mindestens zwei Verhaltensregeln, der ein Testduell übersteht."*
- Backlog-Karten (Epic 1 + 2) liegen bereits am Board (siehe [scrum-board.md](dozent/scrum-board.md)). Jedes Team wählt 2-3 Storys aus Epic 1 zum Einstieg.

## 09:15–09:30 — Kurz-Intro: Git & Gradle

Dozentengeführt, kurz und knapp — nur so viel, dass Teams gleich damit arbeiten
können, keine Tiefenbohrung:

1. Git in ein bis zwei Sätzen: Team-Branch, committen, täglicher Pull Request
   auf den Basis-Branch (Details siehe [git-github-basics.md](git-github-basics.md)).
2. Gradle in ein bis zwei Sätzen: Build-Tool, das Abhängigkeiten lädt und den
   Code baut; die drei Befehle `./gradlew run`/`test`/`build` reichen für den
   gesamten Praktikumsalltag (Details siehe [gradle-basics.md](gradle-basics.md)).

Beide Docs stehen zum Nachlesen bereit, für dieses Fenster reicht ein kurzer
mündlicher Überblick plus Verweis darauf.

## 09:30–10:45 — Arbeitsblock 1: Framework-Walkthrough

Dozentengeführt, alle Teams gemeinsam. Ausführliches Begleitmaterial (Inhalte +
Ablauf/Regie) siehe [tag-1-framework-walkthrough.md](dozent/tag-1-framework-walkthrough.md):

1. App einmal live starten (`./gradlew run`), Beispiel-Bots (`RandomBot` vs. `ChaserBot`) gegeneinander laufen lassen — Schüler sehen live, was am Ende funktionieren soll.
2. Domain-Modell erklären anhand von `Models.kt`: `Direction`, `Position`, `RobotState`, `Sensors`, `Action`, `RobotBrain`. Betonen: **Schüler schreiben nur `decide()`**, alles andere ist fertig.
3. `Toolkit.kt` kurz zeigen: fertige Funktionen für Distanz, Richtung, Gegnersuche und Sichtlinie — Schüler rufen sie auf, statt die Formeln selbst herzuleiten (Referenz: [`toolkit-referenz.md`](toolkit-referenz.md)).
4. Kurzer Blick in `bots/teama/TeamABots.kt` (o.ä. für die eigene Gruppe): wo trage ich meinen Code ein, wie heißt die Liste, die ich pflegen muss.
5. Erste Übung gemeinsam an der Tafel/am Beamer: `RandomBot`-Logik nachvollziehen (liegt in `bots/examples/RandomBot.kt`), Zeile für Zeile durchgehen.

Didaktischer Hinweis: Die Konzepte `sealed interface` (für `Action`) und `enum class` mit Property (`Direction.dx/dy`) sind für die Schüler evtl. neu — kurz erklären, aber nicht vertiefen, sie müssen nur *benutzen*, nicht selbst definieren können.

## 10:45–11:00 Pause

## 11:00–12:45 — Arbeitsblock 2: Erste Bots (Epic 1)

Teamarbeit, Pair-Programming (wie an Tag 1/2 eingeführt: zwei Schüler pro Rechner, wechseln sich als "Driver"/"Navigator" ab).

- Story 1.1 (Zufällige Bewegung) als Einstieg — jedes Team bekommt seinen Bot zum ersten Mal ans Laufen.
- Story 1.2 (Rand halten) für Teams, die schneller fertig sind.
- Story 1.3 (Flucht bei niedriger Gesundheit) für sehr schnelle Teams — sonst kein Problem, das wird an Tag 2 nachgeholt.
- Zusätzlicher Puffer für die schnellsten Teams (kein Muss, kein Vorgriff auf Epic 2/3): Story 1.4 (Randcheck), 1.5 (Sicherheitsabstand), 1.6 (Fluchtrichtung + Wandvermeidung) — bleiben alle innerhalb Epic 1, kein Dozenten-Freigabe nötig wie bei der Kür-Story 3.3.
- Dozent geht herum, hilft bei Compile-Fehlern (häufigster Fehler: neue Bot-Klasse nicht in `teamXBots`-Liste eingetragen).

**Zwischenziel bis 12:45:** jeder Bot bewegt sich sichtbar in der App und hat mindestens eine Bewegungsregel aus Epic 1 umgesetzt.

## 12:45–13:00 — Tagesrückblick

- Kurze Runde: Was hat funktioniert, wo gab es Stolpersteine?
- Ausblick auf Tag 2: Angriffslogik (Epic 2, direkt zu Beginn), danach Zustände/Strategie (Epic 3).
- Hinweis: ab 13:00 Uhr KI-Vortrag.
