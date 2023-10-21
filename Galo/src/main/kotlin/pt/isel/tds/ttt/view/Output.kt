package pt.isel.tds.ttt.view

import pt.isel.tds.ttt.model.*

/**
 * Shows the grid of the board with moves.
 * Shows the winner if there is one.
 * Shows the draw if there is no winner and the board is full.
 * Shows the turn if there is no winner.
 * @receiver The board to show.
 */
fun Board.show() {
    moves.forEachIndexed { index, c ->
        print(" ${c ?: ' '} ")
        if (index % 3 == 2) {
            if (index < 8) println("\n---+---+---")
        }
        else print("|")
    }
    println()
    when {
        winner!=null -> println("winner: $winner")
        isFull() -> println("Draw")
        else -> println("turn: $turn")
    }
}