package pt.isel.tds.ttt.view

import pt.isel.tds.storage.Storage
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
    val execute: Game.(args:List<String>)->Game = { this }
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
private fun storageCommand(exec: (String, Game) -> Game) =
    Command("<name>") { args ->
        require(args.isNotEmpty()) { "Missing name" }
        val name = args[0]
        require(name.isNotEmpty()) { "Name must not be empty" }
        exec(name, this)
    }

/**
 * Returns a map of all commands supported by the application.
 */
fun getCommands(st: Storage<String, Game>): Map<String, Command> = mapOf(
    "NEW" to Command { newBoard() },
    "PLAY" to Play,
    "EXIT" to Command(isToFinish= true),
    "SCORE" to Command { showScore(); this },
    "SAVE" to storageCommand { name, game ->
        game.also{ st.create(name, it) }
    },
    "LOAD" to storageCommand { name, _ ->
        st.read(name) ?: error("$name not found")
    }
)
