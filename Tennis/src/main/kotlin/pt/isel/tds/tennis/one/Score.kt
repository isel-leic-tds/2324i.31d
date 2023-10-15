package pt.isel.tds.tennis.one

import pt.isel.tds.tennis.Player
import pt.isel.tds.tennis.one.Points.*

enum class Points(val value: Int) {
    LOVE(0), FIFTEEN(15), THIRTY(30), FORTY(40), GAME(45)
}


private fun Points.next() = Points.entries[ordinal+1]

/**
 * Represents the score of a tennis game.
 * @property pointsA the points of player A
 * @property pointsB the points of player B
 * Score(40,40) -> Deuce
 * Score(45,40) -> Advantage A and Score(40,45) -> Advantage B
 * Score(45,?) -> Game A and Score(?,45) -> Game B , where ?!=40
 */
class Score(val pointsA: Points, val pointsB: Points) {
    init {
        require(pointsA!=GAME || pointsB!=GAME)
    }
    val isGame get() =
        pointsA==GAME && pointsB!=FORTY ||
        pointsB==GAME && pointsA!=FORTY
    private val isAdvantage get() =
        pointsA==GAME && pointsB==FORTY ||
        pointsB==GAME && pointsA==FORTY
    private fun player45() = if(pointsA==GAME) Player.A else Player.B
    val placard get() =
        when {
            pointsA==FORTY && pointsB==FORTY -> "Deuce"
            isGame -> "Game ${player45()}"
            isAdvantage -> "Advantage ${player45()}"
            else -> "${pointsA.value} - ${pointsB.value}"
        }
    fun next(winner: Player): Score =
        when {
            isGame -> error("Game is over")
            isAdvantage ->
                if (winner==player45())
                    if (winner== Player.A) Score(GAME,LOVE) else Score(LOVE,GAME)
                else Score(FORTY,FORTY)
            else ->
                if (winner== Player.A) Score(pointsA.next(), pointsB)
                else Score(pointsA, pointsB.next())
        }
}

val initialScore = Score(LOVE,LOVE)