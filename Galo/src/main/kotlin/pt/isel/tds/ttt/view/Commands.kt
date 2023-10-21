package pt.isel.tds.ttt.view

import pt.isel.tds.ttt.model.*

/**
 * Represents a command.
 * @property argsSyntax The syntax of the command arguments.
 * @property isToFinish Indicates if the command is to finish the program.
 */
abstract class Command(val argsSyntax:String = "") {
    /**
     * Executes the command.
     * @param args The command arguments.
     * @param board The previous board of the game.
     * @return The board of the game after the command execution.
     */
    open fun execute(args: List<String>, board: Board?): Board? = board
    open val isToFinish: Boolean = false
}

/**
 * Command to play a move in the game.
 * The position is given as a single integer.
 */
object Play : Command("<position>") {
    override fun execute(args: List<String>, board: Board?): Board {
        require(args.isNotEmpty()) { "Missing position" }
        checkNotNull(board) { "Game is not started" }
        val pos = requireNotNull(args[0].toPositionOrNull()) { "Position ${args[0]} invalid"}
        return board.play(pos)
    }
}

/**
 * Returns a map of all commands supported by the application.
 */
fun getCommands(): Map<String, Command> = mapOf(
    "NEW" to object : Command() {
        override fun execute(args: List<String>, board: Board?) = Board()
    },
    "PLAY" to Play,
    "EXIT" to object : Command(){
        override val isToFinish = true
    }
)