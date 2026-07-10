package bots.teamb

// Hier entsteht euer Team-Bot. Ihr dürft diese Datei erweitern oder weitere
// Bot-Klassen in diesem Package anlegen. Vergesst nicht, neue Bots unten in
// `teamBBots` einzutragen, damit sie im Spiel erscheinen.

import framework.arena.Action
import framework.arena.Direction
import framework.arena.RobotBrain
import framework.arena.Sensors

/** Startpunkt für euren eigenen Bot - benennt/erweitert diese Klasse nach Belieben. */
class MeinBot(override val name: String = "Team B - MeinBot") : RobotBrain {
    override fun decide(sensors: Sensors): Action {
        // Alle Toolkit-Funktionen: siehe docs/toolkit-referenz.md
        return Action.Move(Direction.SOUTH)
    }
}

val teamBBots: List<RobotBrain> = listOf(MeinBot())
