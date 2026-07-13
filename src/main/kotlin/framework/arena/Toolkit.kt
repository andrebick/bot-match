package framework.arena

import kotlin.math.abs

/**
 * Fertige Rasterberechnungen für Bot-Code: Distanz, Richtung, Sichtlinie, Rand-/Zentrumsnähe,
 * Gegner-Auswahl. Alles hier ist reine Formelarbeit auf Basis von [Position]/[RobotState]/[Sensors] —
 * nichts davon verändert den Spielzustand. Ziel: Schüler rufen diese Funktionen auf, statt
 * Manhattan-Distanz und dx/dy-Vorzeichen jedes Mal selbst herzuleiten.
 */

/** Rasterabstand (keine Diagonalen), Summe der Achsenabstände. */
fun Position.manhattanDistanceTo(other: Position): Int = abs(x - other.x) + abs(y - other.y)

/** True, wenn [other] in derselben Spalte oder Reihe liegt (Voraussetzung für einen Treffer). */
fun Position.isAlignedWith(other: Position): Boolean = x == other.x || y == other.y

/**
 * Exakte Richtung zu [other], aber nur wenn [other] in gerader Linie liegt (siehe [isAlignedWith]).
 * Liefert `null` bei Diagonale oder wenn beide Positionen identisch sind.
 */
fun Position.directionTo(other: Position): Direction? = when {
    x == other.x && y == other.y -> null
    x == other.x -> if (other.y < y) Direction.NORTH else Direction.SOUTH
    y == other.y -> if (other.x > x) Direction.EAST else Direction.WEST
    else -> null
}

/**
 * Ein Schritt näher an [other] heran — auch wenn [other] nicht in gerader Linie liegt.
 * Heuristik: die Achse mit dem größeren Abstand wird zuerst verringert (verringert die
 * Manhattan-Distanz garantiert um genau 1). Liefert `null`, wenn beide Positionen identisch sind.
 */
fun Position.approachDirectionTo(other: Position): Direction? {
    val dx = other.x - x
    val dy = other.y - y
    return when {
        dx == 0 && dy == 0 -> null
        abs(dx) >= abs(dy) -> if (dx > 0) Direction.EAST else Direction.WEST
        else -> if (dy > 0) Direction.SOUTH else Direction.NORTH
    }
}

/** Ein Schritt weg von [other] — die Umkehrung von [approachDirectionTo]. */
fun Position.fleeDirectionFrom(other: Position): Direction? = other.approachDirectionTo(this)

/** True, wenn diese Position innerhalb einer Arena der Größe [arenaWidth]x[arenaHeight] liegt. */
fun Position.isInsideArena(arenaWidth: Int, arenaHeight: Int): Boolean =
    x in 0 until arenaWidth && y in 0 until arenaHeight

/** Kürzester Abstand zu irgendeinem der vier Arena-Ränder. */
fun Position.distanceToNearestEdge(arenaWidth: Int, arenaHeight: Int): Int =
    minOf(x, arenaWidth - 1 - x, y, arenaHeight - 1 - y)

/** In welche Richtung der nächstgelegene Arena-Rand liegt. */
fun Position.directionToNearestEdge(arenaWidth: Int, arenaHeight: Int): Direction {
    val distances = listOf(
        Direction.NORTH to y,
        Direction.SOUTH to (arenaHeight - 1 - y),
        Direction.WEST to x,
        Direction.EAST to (arenaWidth - 1 - x)
    )
    return distances.minBy { it.second }.first
}

/**
 * True, wenn diese Position höchstens [margin] Felder (in x UND y) vom exakten Arena-Mittelpunkt
 * entfernt ist. Nützlich für Story 1.2 ("Zentrum meiden").
 */
fun Position.isNearCenter(arenaWidth: Int, arenaHeight: Int, margin: Int = 2): Boolean {
    val centerX = (arenaWidth - 1) / 2
    val centerY = (arenaHeight - 1) / 2
    return abs(x - centerX) <= margin && abs(y - centerY) <= margin
}

/** Der Roboter aus der Liste mit dem kleinsten Abstand zu [position], oder `null` wenn die Liste leer ist. */
fun List<RobotState>.nearestTo(position: Position): RobotState? =
    minByOrNull { it.position.manhattanDistanceTo(position) }

/** Der Roboter mit der niedrigsten Gesundheit, oder `null` wenn die Liste leer ist. */
fun List<RobotState>.weakest(): RobotState? = minByOrNull { it.health }

/** Der Roboter mit der höchsten Gesundheit, oder `null` wenn die Liste leer ist. */
fun List<RobotState>.strongest(): RobotState? = maxByOrNull { it.health }

/** Alle Roboter aus der Liste, die von [position] aus in gerader Linie stehen. */
fun List<RobotState>.inLineWith(position: Position): List<RobotState> =
    filter { it.position.isAlignedWith(position) }

/**
 * Alle Roboter aus der Liste mit exakt diesem `teamName` (= `RobotBrain.name` des jeweiligen Bots).
 * Achtung: Es gibt kein echtes Team-Konzept zur Laufzeit — das filtert nach dem Bot-Namen, nicht
 * nach einer Team-Zugehörigkeit A/B/C (die ist nur UI-Organisation in `BotRegistry`).
 */
fun List<RobotState>.named(name: String): List<RobotState> = filter { it.teamName == name }

/** Der nächstgelegene gegnerische Roboter, oder `null` wenn kein Gegner mehr lebt. */
fun Sensors.nearestEnemy(): RobotState? = others.nearestTo(self.position)

/** Der gegnerische Roboter mit der niedrigsten Gesundheit, oder `null` wenn kein Gegner mehr lebt. */
fun Sensors.weakestEnemy(): RobotState? = others.weakest()

/** Der gegnerische Roboter mit der höchsten Gesundheit, oder `null` wenn kein Gegner mehr lebt. */
fun Sensors.strongestEnemy(): RobotState? = others.strongest()

/** Alle Gegner, die aktuell in gerader Linie zu diesem Bot stehen (Voraussetzung für einen Treffer). */
fun Sensors.enemiesInLineOfSight(): List<RobotState> = others.inLineWith(self.position)

/** Der nächstgelegene Gegner in gerader Linie, oder `null` wenn keiner in Sichtlinie steht. */
fun Sensors.nearestEnemyInLineOfSight(): RobotState? = enemiesInLineOfSight().nearestTo(self.position)

/** True, wenn [target] von diesem Bot aus in gerader Linie steht. */
fun Sensors.canShoot(target: RobotState): Boolean = self.position.isAlignedWith(target.position)

/** True, wenn dieser Bot nahe am Arena-Mittelpunkt steht (siehe [Position.isNearCenter]). */
fun Sensors.isNearCenter(margin: Int = 2): Boolean = self.position.isNearCenter(arenaWidth, arenaHeight, margin)

/** Kürzester Abstand dieses Bots zu irgendeinem Arena-Rand. */
fun Sensors.distanceToNearestEdge(): Int = self.position.distanceToNearestEdge(arenaWidth, arenaHeight)

/** In welche Richtung der nächstgelegene Arena-Rand von diesem Bot aus liegt. */
fun Sensors.directionToNearestEdge(): Direction = self.position.directionToNearestEdge(arenaWidth, arenaHeight)

/** Alle Gegner mit exakt diesem Namen (siehe [List.named]). */
fun Sensors.enemiesNamed(name: String): List<RobotState> = others.named(name)
