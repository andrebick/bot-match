package bots.teama

// Hier entsteht euer Team-Bot. Ihr dürft diese Datei erweitern oder weitere
// Bot-Klassen in diesem Package anlegen. Vergesst nicht, neue Bots unten in
// `teamABots` einzutragen, damit sie im Spiel erscheinen.

import framework.arena.Action
import framework.arena.Direction
import framework.arena.RobotBrain
import framework.arena.RobotState
import framework.arena.Sensors
import framework.arena.weakest

/** Startpunkt für euren eigenen Bot - benennt/erweitert diese Klasse nach Belieben. */
class MeinBot(override val name: String = "Team A - MeinBot") : RobotBrain {
    override fun decide(sensors: Sensors): Action {
        // Alle Toolkit-Funktionen: siehe docs/toolkit-referenz.md
        return Action.Move(Direction.SOUTH)
    }
}

val teamABots: List<RobotBrain> = listOf(MeinBot())
