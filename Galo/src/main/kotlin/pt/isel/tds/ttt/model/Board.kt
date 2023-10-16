package pt.isel.tds.ttt.model

/**
 * Represents a Tic-Tac-Toe board.
 * @property moves The board moves.
 * @property turn The player to play.
 */
class Board(
    val moves: List<Char> = listOf(
        ' ', ' ', ' ',
        ' ', ' ', ' ',
        ' ', ' ', ' '
    ),
    val turn: Char = 'X'
)

/**
 * Plays a move in the board.
 * @param pos The position to play.
 * @return The new board.
 */
fun Board.play(pos: Int): Board = Board(
    moves = moves.mapIndexed { idx, c -> if (idx == pos) turn else c },
    turn = if (turn == 'X') 'O' else 'X'
)

/**
 * Checks if a move can be played in the board.
 * @param pos The position to play.
 * @return True if the move can be played, false otherwise.
 */
fun Board?.canPlay(pos: Int) =
    this != null && moves[pos] == ' '

/**
 * Checks if the board has a winner.
 * @param player The player to check.
 * @return True if the player is a winner, false otherwise.
 */
fun Board.isWinner(player: Char) =
    (0..6 step 3).any { row ->
        (0..2).all { col -> moves[row + col] == player }
    } ||
    (0..2).any { col ->
        (0..6 step 3).all { row -> moves[row + col] == player }
    } ||
    (0..8 step 4).all { i -> moves[i] == player } ||
    (2..6 step 2).all { i -> moves[i] == player }

/**
 * Checks if the board is full.
 * @return True if the board is full, false otherwise.
 */
fun Board.isFull() = moves.all { it != ' ' }