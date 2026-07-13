package bots.examples

import framework.arena.Action
import framework.arena.Direction
import framework.arena.Position
import framework.arena.RobotBrain
import framework.arena.RobotState
import framework.arena.Sensors
import framework.arena.approachDirectionTo
import framework.arena.directionTo
import framework.arena.enemiesInLineOfSight
import framework.arena.manhattanDistanceTo
import framework.arena.weakest

/**
 * Der aggressivste Beispiel-Bot: kombiniert zwei Ideen, die in echten Kampf-KIs
 * (z.B. Zielpriorisierung nach Bedrohung/Gesundheit, siehe gängige Utility-AI-
 * Ansätze) üblich sind, statt nur stur den nächsten Gegner zu verfolgen wie
 * [ChaserBot]:
 *
 * 1. Steht IRGENDEIN Gegner bereits in Schusslinie (gleiche Reihe/Spalte), wird
 *    sofort geschossen - und zwar auf den schwächsten unter ihnen (niedrigste
 *    HP zuerst töten, statt wahllos auf den nächstbesten zu ballern).
 * 2. Ist niemand in Schusslinie, wird ein Ziel per Score aus Distanz UND
 *    Gesundheit gewählt (nahe UND schwache Gegner zuerst), nicht nur die
 *    nächste Distanz wie bei [ChaserBot]. Zum Ausrichten wird zuerst die
 *    x-Differenz geschlossen (gleiche Spalte anpeilen), dann geschossen -
 *    empirisch die stärkste der getesteten Annäherungsstrategien (bewusst
 *    NICHT die "größere Achse zuerst"-Heuristik von [ChaserBot]/[FluchtBot]).
 *
 * Bewusst KEINE Flucht bei niedriger Gesundheit (anders als [FluchtBot]): gegen
 * einen Gegner mit identischem Bewegungsmuster (z.B. FluchtBot selbst) würde ein
 * Flucht-Trigger dazu führen, dass beide Bots synchron in eine endlose
 * Verfolgungsjagd geraten und sich nie mehr treffen -> Unentschieden statt Sieg.
 * PowerBot bleibt aggressiv bis zum Schluss.
 *
 * Grenze: Gegen einen exakt gleich starken, ähnlich aggressiven Bot (gleiche HP,
 * gleicher Schaden, z.B. [ChaserBot]) endet der Kampf zwangsläufig unentschieden -
 * sobald beide in derselben Linie stehen und feuern, sterben sie wegen 100 HP /
 * 10 Schaden nach exakt 10 Ticks gleichzeitig. Das ist Arithmetik, keine
 * Schwäche der Zielwahl.
 */
class PowerBot(override val name: String = "PowerBot") : RobotBrain {

    /**
     * Priorisiert Gegner in Sichtlinie (schwächster zuerst), sonst Annäherung an
     * das beste Ziel nach Distanz+HP-Score. Nie Flucht, siehe Klassen-Doc.
     *
     * @param sensors aktueller Wahrnehmungszustand (eigene Position, lebende Gegner).
     * @return [Action.Wait] wenn kein Gegner mehr lebt, sonst [Action.Shoot] auf das
     *   priorisierte Ziel in Sichtlinie oder [Action.Move] zur Annäherung.
     */
    override fun decide(sensors: Sensors): Action {
        val self = sensors.self.position
        if (sensors.others.isEmpty()) return Action.Wait

        val weakestAligned = sensors.enemiesInLineOfSight().weakest()
        if (weakestAligned != null) {
            return Action.Shoot(self.directionTo(weakestAligned.position)!!)
        }

        val target = bestTarget(sensors)
        return moveTowardAlignment(self, target.position)
    }

    /** Score aus Distanz und Gesundheit: nahe UND schwache Gegner werden bevorzugt. */
    private fun bestTarget(sensors: Sensors): RobotState =
        sensors.others.minBy { it.position.manhattanDistanceTo(sensors.self.position) + it.health / 10 }

    /**
     * Schließt zuerst die x-Differenz (bewegt sich in die gleiche Spalte wie das
     * Ziel), erst danach die y-Differenz. Diese "Spalte zuerst"-Reihenfolge hat
     * sich in Testläufen gegen alle anderen Beispiel-Bots als die stärkste
     * Ausricht-Strategie erwiesen - deshalb bewusst kein
     * [framework.arena.approachDirectionTo] (das würde die größere Achse zuerst wählen).
     */
    private fun moveTowardAlignment(self: Position, target: Position): Action {
        val dx = target.x - self.x
        return if (dx != 0) {
            Action.Move(if (dx > 0) Direction.EAST else Direction.WEST)
        } else {
            Action.Move(self.approachDirectionTo(target)!!)
        }
    }
}
