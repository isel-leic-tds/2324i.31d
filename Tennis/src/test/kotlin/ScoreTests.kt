import pt.isel.tds.tennis.Player
import kotlin.test.*
import pt.isel.tds.tennis.fp.*
import pt.isel.tds.tennis.toPlayerOrNull

fun Score.sequenceNext(winners: String): Score =
    winners.fold(this) { score, winner ->
        score.next( requireNotNull( winner.toPlayerOrNull() ) {
            "Invalid winner '$winner'"
        })
    }


class ScoreTests {
    @Test fun initialScore() {
        val score = initialScore
        assertEquals("0 - 0", score.placard)
        assertFalse(score.isGame)
    }
    @Test fun `game of A`() {
        val score = initialScore.sequenceNext("AAAA")
        assertEquals("Game A", score.placard)
        assertTrue(score.isGame)
    }
    @Test fun `deuce conditions`() {
        val score = initialScore.sequenceNext("AAABBB")
        assertEquals("Deuce", score.placard)
        assertFalse(score.isGame)
    }
}