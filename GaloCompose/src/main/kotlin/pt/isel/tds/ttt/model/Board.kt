package pt.isel.tds.ttt.model

const val BOARD_SIZE = 3
const val BOARD_CELLS = BOARD_SIZE* BOARD_SIZE

typealias Moves = Map<Position,Player>

/**
 * Represents a Tic-Tac-Toe board.
 * @property moves The board moves.
 * @property turn The player to play, if in RUN state.
 * @property winner The winner, if in WIN state
 */
sealed class Board(val moves: Moves){
    override fun equals(other: Any?): Boolean {
        return other is Board && moves == other.moves && equalsRemain(other)
    }
    override fun hashCode(): Int = moves.hashCode() + this::class.simpleName.hashCode()
}
class BoardRun(moves: Moves, val turn: Player): Board(moves)
class BoardWin(moves: Moves, val winner: Player): Board(moves)
class BoardDraw(moves: Moves): Board(moves)

fun Board.equalsRemain(other: Board) = when(this) {
    is BoardRun -> other is BoardRun && turn == other.turn
    is BoardWin -> other is BoardWin && winner == other.winner
    is BoardDraw -> other is BoardDraw
}

/**
 * Plays a move in the board.
 * @param pos The position to play.
 * @return The new board.
 */
fun Board.play(pos: Position): Board = when(this) {
    is BoardRun -> {
        require(moves[pos]==null) { "Position already used" }
        val movesAfter = moves + (pos to turn)
        val winner = winnerIn(movesAfter,pos)
        when {
            winner!=null -> BoardWin(movesAfter,winner)
            movesAfter.size == BOARD_CELLS -> BoardDraw(movesAfter)
            else -> BoardRun(movesAfter, turn.other)
        }
    }
    is BoardWin, is BoardDraw -> error("Game Over")
}

/**
 * Returns the winner, if any, after playing a move.
 * @param moves The moves after playing a move.
 * @param pos The position played.
 * @return The winner (turn) or null.
 */
private fun BoardRun.winnerIn(moves: Moves, pos: Position): Player? = turn.takeIf {
    moves.filter { it.value == turn }.keys.run {
      size >= BOARD_SIZE
    && ( count { it.row == pos.row } == BOARD_SIZE
      || count { it.col == pos.col } == BOARD_SIZE
      || pos.backSlash && count { it.backSlash } == BOARD_SIZE
      || pos.slash && count { it.slash } == BOARD_SIZE
       )
    }
}

/**
 * Creates the initial board.
 * @param turn The player to first play.
 */
fun Board(turn: Player= Player.X) = BoardRun(emptyMap(), turn)