# Energizer: "Menschlicher Bot" — Bewegungspause nach der Pause

Kurze Auflockerung (5–8 Minuten) für nach einer Pause, wenn die Konzentration
nachlässt. Passt thematisch zum Kurs: Die Schüler spielen selbst die Roboter-Logik
durch, die sie sonst in Kotlin programmieren — inklusive der Idee, dass ein Bot
Befehle **exakt** befolgt, ohne mitzudenken. Angelehnt an die "Kidbots"-Übung von
[CS Unplugged](https://www.csunplugged.org/en/at-home/kidbots/), leicht angepasst
an das `Direction`/`Position`-Modell aus diesem Kurs.

## Was ihr braucht

- Etwas Platz (Klassenraum reicht, Stühle kurz beiseite schieben)
- Optional: mit Kreide/Tape ein Raster auf den Boden markieren (z.B. 5×5 Felder) —
  geht aber auch ganz ohne, nur mit gedachtem Raster

Kein Material vorzubereiten, kein Aufwand — perfekt für spontan zwischendurch.

## Ablauf

1. Ein:e Freiwillige:r ist der **Bot**, stellt sich in die Mitte des (gedachten)
   Rasters.
2. Der Rest der Klasse ist die **Engine** — sie rufen abwechselnd Befehle, die der
   Bot **exakt** und **ohne nachzudenken** ausführen muss, genau wie ein
   `RobotBrain` in unserem Framework:
   - `NORTH` / `SOUTH` / `EAST` / `WEST` → ein Schritt in diese Richtung
   - `SHOOT` + Richtung → in diese Richtung zeigen und "Peng!" rufen
   - `WAIT` → einen Moment stillstehen
3. Wichtig fürs Mitspielen: **Ein Befehl pro "Tick"** — die Klasse ruft immer nur
   einen Befehl, wartet, bis der Bot ihn fertig ausgeführt hat, dann kommt der
   nächste. Genau wie `decide()` im Code: pro Tick genau eine Aktion.
4. Der Bot darf **nicht improvisieren** — bekommt er einen unsinnigen Befehl (z.B.
   `NORTH`, obwohl da schon eine Wand/ein Tisch ist), bleibt er einfach stehen, wie
   die Engine das auch machen würde. Das ist Absicht, kein Fehler im Spiel.

## Steigerung (wenn noch Zeit/Energie da ist)

- **Zwei Bots gleichzeitig**, die sich "duellieren": Klasse teilt sich in zwei
  Gruppen, jede ruft abwechselnd Befehle für ihren Bot. Wer zuerst dreimal "trifft"
  (Bot steht in der Reihe/Spalte, in die geschossen wird), gewinnt.
- **Ein Schüler schreibt vorher ein komplettes Programm** auf Papier (Befehlsliste),
  ohne dass der Bot es vorher sieht — dann wird die Liste Zeile für Zeile
  vorgelesen und der Bot folgt blind. Zeigt sehr anschaulich, warum man beim
  Programmieren genau durchdenken muss, was passiert, *bevor* man es laufen lässt.

## Warum das zum Kurs passt (kurzer Transfer-Satz danach)

Ein guter Übergangssatz zurück zum Code: *"Genau das macht euer `decide()` auch —
es bekommt einen Zustand (wo bin ich, was sehe ich) und muss daraus eine einzige,
klare Aktion ableiten. Kein Nachdenken über mehrere Schritte, keine Zusatzinfos —
nur das, was gerade in `Sensors` steht."*

## Zeitaufwand

5 Minuten reichen für eine einfache Runde, 8–10 Minuten mit der Steigerung. Läuft
komplett ohne Vorbereitung, gut geeignet, wenn nach einer Pause spontan Zeit für
eine kurze Auflockerung ist.
