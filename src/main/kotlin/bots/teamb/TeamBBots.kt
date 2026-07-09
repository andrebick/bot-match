package bots.teamb
package bots.teamb

import framework.arena.Action
import framework.arena.Direction
import framework.arena.Position
import framework.arena.RobotBrain
import framework.arena.RobotState
import framework.arena.Sensors
import kotlin.math.abs

class MeinBot(override val name: String = "Team B - Galaxy Voyager") : RobotBrain {

    private val zielPunkt = Position(0, 0)
    private val schussReichweite = 4
    private val fluchtGrenzeHp = 50

    // Instanz lebt über das ganze Match, Zustand bleibt also über Ticks erhalten.
    private var fluchtAktiv = false
    private var gegnerAnzahlBeiFluchtStart = 0
    private var hpSchwelle = fluchtGrenzeHp

    override fun decide(sensors: Sensors): Action {
        val gegnerAnzahl = sensors.others.size

        if (fluchtAktiv) {
            if (gegnerAnzahl < gegnerAnzahlBeiFluchtStart) {
                // Ein Gegner ist gestorben -> Flucht beenden, aber erst bei weiterem
                // Schaden erneut fliehen (sonst würde er sofort wieder abhauen).
                fluchtAktiv = false
                hpSchwelle = sensors.self.health - 1
            } else {
                return fluchtAction(sensors)
            }
        }

        if (!fluchtAktiv && sensors.self.health <= hpSchwelle) {
            fluchtAktiv = true
            gegnerAnzahlBeiFluchtStart = gegnerAnzahl
            return fluchtAction(sensors)
        }

        val self = sensors.self.position
        val gegnerAufAchse = naehsterGegnerAufAchse(self, sensors.others)
        if (gegnerAufAchse != null) {
            val (gegner, distanz) = gegnerAufAchse
            if (distanz < schussReichweite) {
                return schiesseAuf(self, gegner)
            }
            val ausweichRichtung = ausweichRichtung(sensors, self, gegner)
            if (ausweichRichtung != null) {
                return Action.Move(ausweichRichtung)
            }
            return schiesseAuf(self, gegner)
        }

        return geheZumZiel(sensors)
    }

    /** Zieht sich vom nächsten Gegner zurück. Steht ein Gegner direkt neben uns auf der
     *  Achse (Fluchtbewegung würde nichts mehr bringen), wird stattdessen in Notwehr geschossen. */
    private fun fluchtAction(sensors: Sensors): Action {
        val self = sensors.self.position
        val gegner = sensors.others
        if (gegner.isEmpty()) return Action.Wait

        val achsenGegner = naehsterGegnerAufAchse(self, gegner)
        if (achsenGegner != null && achsenGegner.second <= 1) {
            return schiesseAuf(self, achsenGegner.first)
        }

        val naechster = gegner.minByOrNull { manhattan(self, it.position) }!!.position
        return Action.Move(fluchtRichtung(sensors, self, naechster))
    }

    /** Richtung, die uns am stärksten von [gegner] entfernt, unter Berücksichtigung der Arena-Grenzen. */
    private fun fluchtRichtung(sensors: Sensors, self: Position, gegner: Position): Direction {
        val dx = self.x - gegner.x
        val dy = self.y - gegner.y
        val kandidaten = if (abs(dx) >= abs(dy)) {
            listOf(if (dx >= 0) Direction.EAST else Direction.WEST, if (dy >= 0) Direction.SOUTH else Direction.NORTH)
        } else {
            listOf(if (dy >= 0) Direction.SOUTH else Direction.NORTH, if (dx >= 0) Direction.EAST else Direction.WEST)
        }
        return kandidaten.firstOrNull { innerhalbArena(self.moved(it), sensors) }
            ?: Direction.entries.first { innerhalbArena(self.moved(it), sensors) }
    }

    /** Nächster Gegner, der mit uns auf einer Achse (gleiche x oder y) steht, mit Achsenabstand. */
    private fun naehsterGegnerAufAchse(self: Position, gegner: List<RobotState>): Pair<Position, Int>? {
        return gegner
            .map { it.position }
            .filter { it.x == self.x || it.y == self.y }
            .map { pos -> pos to (if (pos.x == self.x) abs(pos.y - self.y) else abs(pos.x - self.x)) }
            .minByOrNull { it.second }
    }

    private fun schiesseAuf(self: Position, gegner: Position): Action {
        return if (gegner.x == self.x) {
            Action.Shoot(if (gegner.y > self.y) Direction.SOUTH else Direction.NORTH)
        } else {
            Action.Shoot(if (gegner.x > self.x) Direction.EAST else Direction.WEST)
        }
    }

    /** Bewegt sich auf der Achse, auf der der Gegner NICHT steht, um aus der Schusslinie zu kommen. */
    private fun ausweichRichtung(sensors: Sensors, self: Position, gegner: Position): Direction? {
        val kandidaten = if (gegner.x == self.x) {
            listOf(Direction.EAST, Direction.WEST)
        } else {
            listOf(Direction.NORTH, Direction.SOUTH)
        }
        // Bevorzugt die Richtung, die uns dabei näher an Ziel (0|0) bringt.
        val bevorzugt = kandidaten.sortedBy { richtung ->
            val ziel = self.moved(richtung)
            abs(ziel.x - zielPunkt.x) + abs(ziel.y - zielPunkt.y)
        }
        return bevorzugt.firstOrNull { innerhalbArena(self.moved(it), sensors) }
    }

    private fun innerhalbArena(pos: Position, sensors: Sensors): Boolean {
        return pos.x in 0 until sensors.arenaWidth && pos.y in 0 until sensors.arenaHeight
    }

    private fun manhattan(a: Position, b: Position) = abs(a.x - b.x) + abs(a.y - b.y)

    private fun geheZumZiel(sensors: Sensors): Action {
        val self = sensors.self.position

        // Steht ein Gegner auf dem Zielpunkt, weichen wir ins diagonale Feld gegenüber aus.
        val ziel = if (sensors.others.any { it.position == zielPunkt }) Position(1, 1) else zielPunkt

        val dx = ziel.x - self.x
        val dy = ziel.y - self.y
        if (dx == 0 && dy == 0) {
            return Action.Wait
        }
        return if (abs(dx) >= abs(dy)) {
            Action.Move(if (dx > 0) Direction.EAST else Direction.WEST)
        } else {
            Action.Move(if (dy > 0) Direction.SOUTH else Direction.NORTH)
        }
    }
}

val teamBBots: List<RobotBrain> = listOf(MeinBot())
