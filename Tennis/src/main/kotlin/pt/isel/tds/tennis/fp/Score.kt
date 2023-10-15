package pt.isel.tds.tennis.fp

import pt.isel.tds.tennis.Player
import pt.isel.tds.tennis.pm.*

data class Score(
    val isGame: Boolean = false,
    val placard: String,
    val next: (winner: Player)-> Score
)

fun game(winner: Player) = Score(true,"Game $winner") {
    error("Game is over")
}

fun advantage(player: Player): Score = Score(placard = "Advantage $player") {
    if (it==player) game(player)
    else deuce()
}

fun deuce(): Score = Score(
    placard = "Deuce",
    next = { winner -> advantage(winner) }
)
fun pointsScore(pointsA: Points, pointsB: Points): Score {
    fun pointsOf(player: Player) =
        if(player==Player.A) pointsA else pointsB
    return Score(
        placard = "${pointsA.value} - ${pointsB.value}",
        next = { winner ->
            when {
                pointsOf(winner)== Points.THIRTY ->
                    fortyScore(winner, pointsOf(winner.other))
                winner== Player.A -> pointsScore(pointsA.next(), pointsB)
                else -> pointsScore(pointsA, pointsB.next())
            }
        }
    )
}
fun fortyScore(player: Player, pointsOther: Points): Score = Score(
    placard =
        if (player==Player.A) "40 - ${pointsOther.value}"
        else "${pointsOther.value} - 40",
    next = { winner ->
        when {
            winner==player -> game(player)
            pointsOther== Points.THIRTY -> deuce()
            else -> fortyScore(player, pointsOther.next())
        }
    }
)

val initialScore = pointsScore(Points.LOVE, Points.LOVE)