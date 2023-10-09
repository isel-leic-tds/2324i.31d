package pt.isel.tds.tennis.one

import pt.isel.tds.tennis.Player

/**
 * Valid points in a tennis game for each player.
 */
private val validPoints = listOf(0,15,30,40,45)

private fun Int.next() = validPoints[validPoints.indexOf(this)+1]

/**
 * Represents the score of a tennis game.
 * @property pointsA the points of player A
 * @property pointsB the points of player B
 * Score(40,40) -> Deuce
 * Score(45,40) -> Advantage A and Score(40,45) -> Advantage B
 * Score(45,?) -> Game A and Score(?,45) -> Game B , where ?!=40
 */
class Score(val pointsA: Int, val pointsB: Int) {
    init {
        require(pointsA in validPoints)  // Verification in runtime
        require(pointsB in validPoints)
    }
    val isGame get() =
        pointsA==45 && pointsB!=40 ||
        pointsB==45 && pointsA!=40
    private val isAdvantage get() =
        pointsA==45 && pointsB==40 ||
        pointsB==45 && pointsA==40
    private fun player45() = if(pointsA==45) Player.A else Player.B
    val placard get() =
        when {
            pointsA==40 && pointsB==40 -> "Deuce"
            isGame -> "Game ${player45()}"
            isAdvantage -> "Advantage ${player45()}"
            else -> "$pointsA - $pointsB"
        }
    fun next(winner: Player): Score =
        when {
            isGame -> error("Game is over")
            isAdvantage ->
                if (winner==player45())
                    if (winner== Player.A) Score(45,0) else Score(0,45)
                else Score(40,40)
            else ->
                if (winner== Player.A) Score(pointsA.next(), pointsB)
                else Score(pointsA, pointsB.next())
        }
}

val initialScore = Score(0,0)