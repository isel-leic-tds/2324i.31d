package pt.isel.tds.ttt.model

/**
 * Represents a player.
 */
enum class Player {
    X, O;
    val other get() = if (this==X) O else X
}
