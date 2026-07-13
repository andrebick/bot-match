package bots.examples

import framework.arena.Action
import framework.arena.RobotBrain
import framework.arena.Sensors
import framework.arena.approachDirectionTo
import framework.arena.canShoot
import framework.arena.directionTo
import framework.arena.nearestEnemy

/**
 * Findet den nächsten lebenden Gegner (kleinste Manhattan-Distanz) und greift
 * ihn an: steht er bereits in exakt gleicher Reihe/Spalte (ein Schuss würde
 * treffen), wird geschossen - sonst nähert sich der Bot ihm Schritt für
 * Schritt an (Achse mit größerem Abstand zuerst, für diagonale Annäherung
 * über mehrere Ticks). Nutzt dafür die Toolkit-Funktionen aus `framework.arena`.
 */
class ChaserBot(override val name: String = "ChaserBot") : RobotBrain {
    /**
     * Greift den nächsten Gegner an: schießt sofort, wenn er in gleicher Reihe/Spalte
     * steht, sonst wird die Achse mit dem größeren Abstand zuerst angeglichen.
     *
     * @param sensors aktueller Wahrnehmungszustand (eigene Position, lebende Gegner).
     * @return [Action.Wait] wenn kein Gegner mehr lebt, sonst [Action.Shoot] bei
     *   exakter Ausrichtung oder [Action.Move] zur Annäherung.
     */
    override fun decide(sensors: Sensors): Action {
        val target = sensors.nearestEnemy() ?: return Action.Wait
        val self = sensors.self.position

        return if (sensors.canShoot(target)) {
            Action.Shoot(self.directionTo(target.position)!!)
        } else {
            Action.Move(self.approachDirectionTo(target.position)!!)
        }
    }
}
