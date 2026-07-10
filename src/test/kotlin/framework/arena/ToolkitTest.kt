package framework.arena

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ToolkitTest {

    private fun robot(id: String, x: Int, y: Int, health: Int = 100, team: String = id) =
        RobotState(id = id, teamName = team, position = Position(x, y), health = health)

    private fun sensors(self: RobotState, others: List<RobotState>, w: Int = 10, h: Int = 10, tick: Int = 1) =
        Sensors(self = self, others = others, arenaWidth = w, arenaHeight = h, tick = tick)

    // ---------- Position.manhattanDistanceTo ----------

    @Test
    fun `Manhattan-Distanz summiert beide Achsenabstaende`() {
        assertEquals(7, Position(1, 1).manhattanDistanceTo(Position(4, 5)))
    }

    @Test
    fun `Manhattan-Distanz zu sich selbst ist 0`() {
        assertEquals(0, Position(3, 3).manhattanDistanceTo(Position(3, 3)))
    }

    // ---------- Position.isAlignedWith ----------

    @Test
    fun `isAlignedWith erkennt gleiche Spalte`() {
        assertTrue(Position(2, 1).isAlignedWith(Position(2, 8)))
    }

    @Test
    fun `isAlignedWith erkennt gleiche Reihe`() {
        assertTrue(Position(1, 4).isAlignedWith(Position(9, 4)))
    }

    @Test
    fun `isAlignedWith ist false bei Diagonale`() {
        assertFalse(Position(1, 1).isAlignedWith(Position(2, 2)))
    }

    // ---------- Position.directionTo ----------

    @Test
    fun `directionTo liefert NORTH wenn Ziel darueber in gleicher Spalte liegt`() {
        assertEquals(Direction.NORTH, Position(3, 5).directionTo(Position(3, 1)))
    }

    @Test
    fun `directionTo liefert SOUTH wenn Ziel darunter in gleicher Spalte liegt`() {
        assertEquals(Direction.SOUTH, Position(3, 1).directionTo(Position(3, 5)))
    }

    @Test
    fun `directionTo liefert EAST wenn Ziel rechts in gleicher Reihe liegt`() {
        assertEquals(Direction.EAST, Position(1, 4).directionTo(Position(6, 4)))
    }

    @Test
    fun `directionTo liefert WEST wenn Ziel links in gleicher Reihe liegt`() {
        assertEquals(Direction.WEST, Position(6, 4).directionTo(Position(1, 4)))
    }

    @Test
    fun `directionTo ist null bei Diagonale`() {
        assertNull(Position(1, 1).directionTo(Position(2, 2)))
    }

    @Test
    fun `directionTo ist null bei identischer Position`() {
        assertNull(Position(3, 3).directionTo(Position(3, 3)))
    }

    // ---------- Position.approachDirectionTo / fleeDirectionFrom ----------

    @Test
    fun `approachDirectionTo waehlt groessere Achse zuerst`() {
        assertEquals(Direction.EAST, Position(0, 0).approachDirectionTo(Position(5, 1)))
    }

    @Test
    fun `approachDirectionTo waehlt Y-Achse wenn sie groesser ist`() {
        assertEquals(Direction.SOUTH, Position(0, 0).approachDirectionTo(Position(1, 5)))
    }

    @Test
    fun `approachDirectionTo ist null bei identischer Position`() {
        assertNull(Position(3, 3).approachDirectionTo(Position(3, 3)))
    }

    @Test
    fun `fleeDirectionFrom ist Umkehrung von approachDirectionTo`() {
        assertEquals(Direction.EAST, Position(5, 1).fleeDirectionFrom(Position(0, 0)))
    }

    @Test
    fun `fleeDirectionFrom ist null bei identischer Position`() {
        assertNull(Position(3, 3).fleeDirectionFrom(Position(3, 3)))
    }

    // ---------- Position.isInsideArena ----------

    @Test
    fun `isInsideArena ist true innerhalb der Grenzen`() {
        assertTrue(Position(0, 0).isInsideArena(10, 10))
        assertTrue(Position(9, 9).isInsideArena(10, 10))
    }

    @Test
    fun `isInsideArena ist false ausserhalb der Grenzen`() {
        assertFalse(Position(-1, 0).isInsideArena(10, 10))
        assertFalse(Position(10, 0).isInsideArena(10, 10))
    }

    // ---------- Position.distanceToNearestEdge / directionToNearestEdge ----------

    @Test
    fun `distanceToNearestEdge in der Ecke ist 0`() {
        assertEquals(0, Position(0, 0).distanceToNearestEdge(10, 10))
    }

    @Test
    fun `distanceToNearestEdge in der Mitte einer 10x10 Arena ist 4`() {
        assertEquals(4, Position(4, 4).distanceToNearestEdge(10, 10))
    }

    @Test
    fun `directionToNearestEdge zeigt zum naeheren Rand`() {
        assertEquals(Direction.WEST, Position(1, 5).directionToNearestEdge(10, 10))
        assertEquals(Direction.NORTH, Position(5, 1).directionToNearestEdge(10, 10))
    }

    // ---------- Position.isNearCenter ----------

    @Test
    fun `isNearCenter ist true am exakten Mittelpunkt`() {
        assertTrue(Position(4, 4).isNearCenter(10, 10))
    }

    @Test
    fun `isNearCenter ist false am Rand`() {
        assertFalse(Position(0, 0).isNearCenter(10, 10))
    }

    // ---------- List<RobotState> Helfer ----------

    @Test
    fun `nearestTo liefert naechsten Roboter`() {
        val near = robot("near", 1, 0)
        val far = robot("far", 9, 9)
        assertEquals(near, listOf(far, near).nearestTo(Position(0, 0)))
    }

    @Test
    fun `nearestTo ist null bei leerer Liste`() {
        assertNull(emptyList<RobotState>().nearestTo(Position(0, 0)))
    }

    @Test
    fun `weakest liefert Roboter mit wenigster Gesundheit`() {
        val weak = robot("weak", 0, 0, health = 5)
        val strong = robot("strong", 0, 0, health = 90)
        assertEquals(weak, listOf(strong, weak).weakest())
    }

    @Test
    fun `strongest liefert Roboter mit meister Gesundheit`() {
        val weak = robot("weak", 0, 0, health = 5)
        val strong = robot("strong", 0, 0, health = 90)
        assertEquals(strong, listOf(strong, weak).strongest())
    }

    @Test
    fun `inLineWith filtert nur ausgerichtete Roboter`() {
        val aligned = robot("aligned", 3, 0)
        val diagonal = robot("diagonal", 1, 1)
        assertEquals(listOf(aligned), listOf(aligned, diagonal).inLineWith(Position(3, 3)))
    }

    @Test
    fun `named filtert nach teamName`() {
        val a = robot("a1", 0, 0, team = "PowerBot")
        val b = robot("b1", 1, 1, team = "ChaserBot")
        assertEquals(listOf(a), listOf(a, b).named("PowerBot"))
    }

    // ---------- Sensors Wrapper ----------

    @Test
    fun `nearestEnemy ist null wenn others leer ist`() {
        val self = robot("self", 5, 5)
        assertNull(sensors(self, emptyList()).nearestEnemy())
    }

    @Test
    fun `nearestEnemy findet naechsten Gegner`() {
        val self = robot("self", 5, 5)
        val near = robot("near", 5, 6)
        val far = robot("far", 0, 0)
        assertEquals(near, sensors(self, listOf(far, near)).nearestEnemy())
    }

    @Test
    fun `weakestEnemy und strongestEnemy delegieren an others`() {
        val self = robot("self", 5, 5)
        val weak = robot("weak", 0, 0, health = 1)
        val strong = robot("strong", 9, 9, health = 100)
        val s = sensors(self, listOf(weak, strong))
        assertEquals(weak, s.weakestEnemy())
        assertEquals(strong, s.strongestEnemy())
    }

    @Test
    fun `enemiesInLineOfSight und nearestEnemyInLineOfSight`() {
        val self = robot("self", 5, 5)
        val inLineFar = robot("far", 5, 0)
        val inLineNear = robot("near", 9, 5)
        val diagonal = robot("diagonal", 0, 0)
        val s = sensors(self, listOf(inLineFar, inLineNear, diagonal))

        assertEquals(setOf(inLineFar, inLineNear), s.enemiesInLineOfSight().toSet())
        assertEquals(inLineNear, s.nearestEnemyInLineOfSight())
    }

    @Test
    fun `nearestEnemyInLineOfSight ist null ohne ausgerichteten Gegner`() {
        val self = robot("self", 5, 5)
        val diagonal = robot("diagonal", 0, 0)
        assertNull(sensors(self, listOf(diagonal)).nearestEnemyInLineOfSight())
    }

    @Test
    fun `canShoot prueft Ausrichtung zum Ziel`() {
        val self = robot("self", 5, 5)
        val target = robot("target", 5, 9)
        val s = sensors(self, listOf(target))
        assertTrue(s.canShoot(target))
        assertFalse(s.canShoot(robot("other", 1, 2)))
    }

    @Test
    fun `isNearCenter distanceToNearestEdge directionToNearestEdge delegieren an self position`() {
        val self = robot("self", 4, 4)
        val s = sensors(self, emptyList())
        assertTrue(s.isNearCenter())
        assertEquals(4, s.distanceToNearestEdge())
        assertEquals(Direction.WEST, sensors(robot("self", 1, 5), emptyList()).directionToNearestEdge())
    }

    @Test
    fun `enemiesNamed filtert others nach Namen`() {
        val self = robot("self", 5, 5)
        val power = robot("p", 0, 0, team = "PowerBot")
        val chaser = robot("c", 1, 1, team = "ChaserBot")
        assertEquals(listOf(power), sensors(self, listOf(power, chaser)).enemiesNamed("PowerBot"))
    }
}
