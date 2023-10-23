package pt.isel.tds.ttt.view

import pt.isel.tds.ttt.model.*

// Separator line between rows.
// to avoid computing evry time the show function is called
private val separator = "\n---"+"+---".repeat(BOARD_SIZE-1)

/**
 * Shows the grid of the board with moves.
 * Shows the board state
 * @receiver The board to show.
 */
fun Board.show() {
    Position.values.forEach { pos ->
        print(" ${moves[pos] ?: ' '} ")
        if (pos.col == BOARD_SIZE-1) {
            if (pos.row < BOARD_SIZE - 1) println(separator)
        }
        else print("|")
    }
    println()
    when (this) {
        is BoardWin -> println("winner: $winner")
        is BoardDraw-> println("Draw")
        is BoardRun -> println("turn: $turn")
    }
}