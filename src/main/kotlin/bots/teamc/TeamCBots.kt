package bots.teamc

// Hier entsteht euer Team-Bot. Ihr dürft diese Datei erweitern oder weitere
// Bot-Klassen in diesem Package anlegen. Vergesst nicht, neue Bots unten in
// `teamCBots` einzutragen, damit sie im Spiel erscheinen.

import framework.arena.Action
import framework.arena.Direction
import framework.arena.Position
import framework.arena.RobotBrain
import framework.arena.Sensors

/** Startpunkt für euren eigenen Bot - benennt/erweitert diese Klasse nach Belieben. */
class MeinBot(override val name: String = "Team C - MeinBot") : RobotBrain {
    override fun decide(sensors: Sensors): Action {
        return(Action.Move(Direction.entries.random()))

        val x = sensors.self.position.x
        val y = sensors.self.position.y

        // 1. Spezialfall: Wenn der Bot genau in der Mitte (4, 5) ist
        if (x == 4 && y == 5) {
            return Action.Move(Direction.SOUTH) // Hier zu WEST ändern, falls er Westen bevorzugen soll
        }

        // 2. Normale Bewegung für die X-Achse
        if (x < 4 && x != 0) {
            return Action.Move(Direction.WEST)
        } else if (x > 4 && x != 9) {
            return Action.Move(Direction.EAST)
        }

        // 3. Normale Bewegung für die Y-Achse (wird jetzt erreicht, wenn x genau 4 oder am Rand ist)
        if (y < 5 && y != 0) {
            return Action.Move(Direction.NORTH)
        } else if (y > 5 && y != 9) {
            return Action.Move(Direction.SOUTH)
        }

        // Standard-Aktion, falls keine Bedingung zutrifft (z.B. an den Ecken/Rändern)
        return Action.Move(Direction.EAST)
    }
}

val teamCBots: List<RobotBrain> = listOf(MeinBot())