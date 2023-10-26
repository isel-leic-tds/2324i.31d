package pt.isel.tds.ttt.view

import pt.isel.tds.ttt.model.*

/**
 * Represents a command that can be executed in the application.
 * Implementation using functional programming.
 * @property argsSyntax The syntax of the command arguments.
 * @property isToFinish Indicates if the command finishes the application.
 *
 */
class Command(
    val argsSyntax:String = "",
    val isToFinish: Boolean = false,
    val execute: (args:List<String>,Game)->Game = { _, g -> g }
)

/**
 * Command to play a move in the game.
 * The position is given as a single integer.
 */
val Play = Command("<position>") { args, game ->
    require(args.isNotEmpty()) { "Missing position" }
    val pos = requireNotNull(args[0].toPositionOrNull()) { "Position ${args[0]} invalid"}
    game.play(pos)
}

/**
 * Returns a map of all commands supported by the application.
 */
fun getCommands(): Map<String, Command> = mapOf(
    "NEW" to Command { _, game -> game.newBoard() },
    "PLAY" to Play,
    "EXIT" to Command(isToFinish= true),
    "SCORE" to Command { _, game -> game.also { it.showScore() } }
)