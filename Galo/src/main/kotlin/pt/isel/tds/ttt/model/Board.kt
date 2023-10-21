package pt.isel.tds.ttt.model

const val BOARD_SIZE = 3
const val BOARD_CELLS = BOARD_SIZE* BOARD_SIZE

/**
 * Represents a Tic-Tac-Toe board.
 * @property moves The board moves.
 * @property turn The player to play.
 * @property winner The winner of the board.
 */
class Board(
    val moves: List<Player?> = List(BOARD_CELLS) { null },
    val turn: Player = Player.X,
    val winner: Player? = null
)

/**
 * Plays a move in the board.
 * @param pos The position to play.
 * @return The new board.
 */
fun Board.play(pos: Position): Board {
    //require(pos in 0..8) { "Position $pos invalid" }
    check(winner==null) { "Game is over" }
    require(moves[pos.index]==null) { "Position already used" }
    val movesAfter = moves.mapIndexed { idx, c -> if (idx == pos.index) turn else c }
    return Board(
        moves = movesAfter,
        turn = turn.other,
        winner = winnerIn(movesAfter,pos)
    )
}

private fun winnerIn(moves: List<Player?>, pos: Position): Player? {
    val player = checkNotNull(moves[pos.index])
    if (moves.count{ it==player } < BOARD_SIZE) return null
    return if (
        (0..<BOARD_SIZE).all{ moves[pos.row* BOARD_SIZE + it] == player} ||
        (0..<BOARD_CELLS step BOARD_SIZE).all{ moves[pos.col + it] == player } ||
        pos.row==pos.col && (0..<BOARD_CELLS step BOARD_SIZE+1).all{ moves[it]==player } ||
        pos.row+pos.col== BOARD_SIZE-1 && (BOARD_SIZE-1..<BOARD_CELLS step BOARD_SIZE-1).all{ moves[it]==player }
    ) player
    else null
}

/**
 * Checks if the board is full.
 * @return True if the board is full, false otherwise.
 */
fun Board.isFull() = moves.all { it != null }