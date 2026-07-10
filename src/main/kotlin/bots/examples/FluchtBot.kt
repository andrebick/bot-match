package bots.examples

import framework.arena.Action
import framework.arena.RobotBrain
import framework.arena.Sensors
import framework.arena.approachDirectionTo
import framework.arena.canShoot
import framework.arena.directionTo
import framework.arena.fleeDirectionFrom
import framework.arena.nearestEnemy

/**
 * Solange die eigenen HP bei 20 oder mehr liegen, verhält sich dieser Bot wie
 * [ChaserBot] (greift den nächsten Gegner an). Sobald die HP unter 20 fallen,
 * flüchtet er stattdessen vom nächsten Gegner weg. Landet er dabei an einer
 * Wand, ist das kein Problem - die Engine blockt ungültige Bewegungen ohnehin
 * ab. Nutzt dafür die Toolkit-Funktionen aus `framework.arena`.
 */
class FluchtBot(override val name: String = "FluchtBot") : RobotBrain {

    private companion object {
        const val FLEE_HEALTH_THRESHOLD = 20
    }

    /**
     * Greift den nächsten Gegner an, solange die eigenen HP über [FLEE_HEALTH_THRESHOLD]
     * liegen, sonst flüchtet er von ihm weg.
     *
     * @param sensors aktueller Wahrnehmungszustand (eigene HP/Position, lebende Gegner).
     * @return [Action.Wait] wenn kein Gegner mehr lebt, sonst je nach eigener HP
     *   Angriffs- oder Fluchtaktion.
     */
    override fun decide(sensors: Sensors): Action {
        val target = sensors.nearestEnemy() ?: return Action.Wait
        val self = sensors.self.position

        return if (sensors.self.health < FLEE_HEALTH_THRESHOLD) {
            Action.Move(self.fleeDirectionFrom(target.position)!!)
        } else if (sensors.canShoot(target)) {
            Action.Shoot(self.directionTo(target.position)!!)
        } else {
            Action.Move(self.approachDirectionTo(target.position)!!)
        }
    }
}
