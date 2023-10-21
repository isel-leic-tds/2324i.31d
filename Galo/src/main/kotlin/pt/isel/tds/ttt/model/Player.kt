package pt.isel.tds.ttt.model

/**
 * Represents a player.
 */
enum class Player {
    X, O;
    val other get() = if (this==X) O else X
    // Only for demonstration purposes
    operator fun invoke(a: Int) { println("$this $a") }
}

// Only for demonstration purposes
fun main() {
    val p1 = Player.X
    p1(3) // => p1.invoke(3)
}