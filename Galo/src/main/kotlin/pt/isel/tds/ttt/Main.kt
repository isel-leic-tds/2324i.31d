package pt.isel.tds.ttt

import pt.isel.tds.ttt.model.*
import pt.isel.tds.ttt.view.*

/**
 * The main function of the application.
 * It reads commands from the console and executes them.
 * The only mutable variable is the board state than is updated by the commands.
 * Commands are defined in the [getCommands] function.
 * Exceptions are caught and the error message is printed.
 */
fun main() {
    var board: Board? = null
    val commands: Map<String,Command> = getCommands()
    while(true) {
        val (name,args) = readCommand()
        val cmd = commands[name]
        if (cmd==null) println("Unknown command")
        else try {
            board = cmd.execute(args, board)
            if (cmd.isToFinish) break
        } catch (e: IllegalArgumentException) {
            println("${e.message}\nUse: $name ${cmd.argsSyntax}")
        } catch (e: IllegalStateException) {
            println(e.message)
        }
        board?.show()
    }
}

