package pt.isel.tds.ttt.view

/**
 * Represents a command read from the input.
 * @property name The command name.
 * @property args The command arguments.
 */
data class LineCommand(val name: String, val args: List<String>)

/**
 * Reads a command from the input.
 * If the line is empty, it reads another command.
 * @return The command read.
 */
tailrec fun readCommand(): LineCommand {
    print("> ")
    val line = readln().uppercase().split(' ').filter { it.isNotEmpty() }
    return if (line.isEmpty()) readCommand()
    else LineCommand(line[0], line.drop(1))
}