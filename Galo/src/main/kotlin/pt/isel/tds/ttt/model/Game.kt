package pt.isel.tds.ttt.model

typealias Score = Map<Player?,Int>

/**
 * Represents the game state.
 * The board, the first player and the score.
 * @property board The current board.
 * @property firstPlayer The first player to next board.
 * @property score The score of the game (number of wins for each player end draws).
 */
data class Game(
    val board: Board? = null,
    val firstPlayer: Player = Player.X,
    val score: Score = (Player.entries+null).associateWith { 0 }
)

/**
 * Makes a move in the board.
 * Updates the score if the game is over.
 */
fun Game.play(pos: Position): Game {
    checkNotNull(board) { "Game is not started" }
    val board = board.play(pos)
    val score = when (board) {
        is BoardWin -> score.advance(board.winner)
        is BoardDraw -> score.advance(null)
        is BoardRun -> score
    }
    return copy(board = board, score = score)
}

/**
 * Advances the score of the player.
 */
private fun Score.advance(player: Player?): Score =
    this + (player to (checkNotNull(this[player]){"no score"}+1))

/**
 * Creates a new board.
 * Swaps the first player to next board.
 * If the current board is running, advances the score of the other player.
 */
fun Game.newBoard() = Game(
    board = Board(firstPlayer),
    firstPlayer = firstPlayer.other,
    score = if (board is BoardRun) score.advance(board.turn.other) else score
)