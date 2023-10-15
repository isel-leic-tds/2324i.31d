package pt.isel.tds.tennis.oop

import pt.isel.tds.tennis.Player
import pt.isel.tds.tennis.oop.Points.*

enum class Points(val value: Int) {
    LOVE(0), FIFTEEN(15), THIRTY(30), // FORTY(40) //, GAME(45)
}

private fun Points.next() = Points.entries[ordinal+1]

interface Score {
    val isGame: Boolean get() = false
    val placard: String
    fun next(winner: Player): Score
}

class Game(val winner:Player) : Score {
    override val isGame: Boolean get() = true
    override val placard: String get() = "Game $winner"
    override fun next(winner: Player): Score = error("Game is over")
}
class Advantage(val player:Player) : Score {
    override val placard: String get() = "Advantage $player"
    override fun next(winner: Player): Score =
        if (winner==player) Game(player)
        else Deuce
}
object Deuce : Score {
    override val placard: String get() = "Deuce"
    override fun next(winner: Player): Score =
        Advantage(winner)
}
class PointsScore(val pointsA: Points, val pointsB: Points) : Score {
    override val placard: String get() = "${pointsA.value} - ${pointsB.value}"
    override fun next(winner: Player): Score = when {
        pointsOf(winner)== THIRTY -> FortyScore(winner, pointsOf(winner.other))
        winner== Player.A -> PointsScore(pointsA.next(), pointsB)
        else -> PointsScore(pointsA, pointsB.next())
    }
}
class FortyScore(val player: Player, val pointsOther: Points) : Score {
    override val placard: String get() =
        if (player==Player.A) "40 - ${pointsOther.value}"
        else "${pointsOther.value} - 40"
    override fun next(winner: Player): Score = when {
        winner==player -> Game(player)
        pointsOther==THIRTY -> Deuce
        else -> FortyScore(player, pointsOther.next())
    }
}
fun PointsScore.pointsOf(player: Player) =
    if(player==Player.A) pointsA else pointsB

val initialScore = PointsScore(LOVE,LOVE)