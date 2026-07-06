package bots.teama

// Hier entsteht euer Team-Bot. Ihr dürft diese Datei erweitern oder weitere
// Bot-Klassen in diesem Package anlegen. Vergesst nicht, neue Bots unten in
// `teamABots` einzutragen, damit sie im Spiel erscheinen.

import framework.arena.Action
import framework.arena.Direction
import framework.arena.RobotBrain
import framework.arena.Sensors

/** Startpunkt für euren eigenen Bot - benennt/erweitert diese Klasse nach Belieben. */
class MeinBot(override val name: String = "Team A - SkibidiTerminator") : RobotBrain {
    override fun decide(sensors: Sensors): Action {
        var direction = Direction.entries.random()
        println(direction)
        println(sensors)
        println (sensors.self.position)



        val x = sensors.self.position.x
        val y = sensors.self.position.y

        if (x < 5) {
            direction = Direction.WEST
        }
        else{
            direction = Direction.EAST
        }

        if (y < 5) {
            direction = Direction.NORTH
        }
        else{
            direction = Direction.SOUTH
        }

        if (x == 0 || x == 9){
            if (y < 5){
                direction = Direction.NORTH
            }
            else{
                direction = Direction.SOUTH
            }
        }

        if (y == 0 || y == 9){
            if (x < 5){
                direction = Direction.WEST
            }
            else{
                direction = Direction.EAST
            }
        }

        return Action.Move(direction)
    }
}

val teamABots: List<RobotBrain> = listOf(MeinBot())