package pt.isel.tds.ttt.view

import pt.isel.tds.ttt.model.*

/**
 * Represents a command that can be executed in the application.
 * Implementation using functional programming.
 * @property argsSyntax The syntax of the command arguments.
 * @property isToFinish Indicates if the command finishes the application.
 */
class Command(
    val argsSyntax:String = "",
    val isToFinish: Boolean = false,
    val execute: Match.(args:List<String>)->Match = { this }
)

/**
 * Command to play a move in the game.
 * The position is given as a single integer.
 */
val Play = Command("<position>") { args ->
    require(args.isNotEmpty()) { "Missing position" }
    val pos = requireNotNull(args[0].toPositionOrNull()) { "Position ${args[0]} invalid"}
    play(pos)
}

/**
 * Function to create a command that have a name as argument.
 */
private fun storageCommand(exec: Match.(Name) -> Match) =
    Command("<name>") { args ->
        require(args.isNotEmpty()) { "Missing name" }
        exec(Name(args[0]))
    }

/**
 * Returns a map of all commands supported by the application.
 */
fun getCommands(): Map<String, Command> = mapOf(
    "NEW" to Command { newBoard() },
    "PLAY" to Play,
    "EXIT" to Command(isToFinish= true) { exit(); this },
    "SCORE" to Command { gameRun?.showScore(); this },
    "START" to storageCommand { start(it) },
    "JOIN" to storageCommand { join(it) },
    "REFRESH" to Command { refresh() },
)
