package pt.isel.tds.ttt

import pt.isel.tds.ttt.model.*
import pt.isel.tds.ttt.view.*

fun main() {
    var board: Board? = null
    while(true) {
        val (name,args) = readCommand()
        when (name) {
            "PLAY" -> {
                val pos = args[0].toInt()
                if (board.canPlay(pos))
                    board = board?.play(pos)
            }
            "NEW" -> board = Board()
            "EXIT" -> break
            else -> println("Unknown command")
        }
        board?.show()
    }
}

