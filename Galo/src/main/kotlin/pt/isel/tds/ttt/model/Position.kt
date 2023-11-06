package pt.isel.tds.ttt.model

/**
 * Represents a position in the board.
 * @property index The position index.
 * @property row The position row.
 * @property col The position column.
 */
@JvmInline
value class Position private constructor(val index: Int) {
    val row: Int get() = index / BOARD_SIZE
    val col: Int get() = index % BOARD_SIZE
    val backSlash: Boolean get() = row == col
    val slash: Boolean get() = row + col == BOARD_SIZE - 1

    companion object {
        // All permitted positions
        val values = List(BOARD_CELLS) { Position(it) }
        operator fun invoke(idx: Int): Position {
            require(idx in 0..<BOARD_CELLS) {"Position $idx invalid"}
            return values[idx]
        }
    }
    override fun toString(): String = "$index"
}

fun Int.toPosition(): Position = Position(this)

fun String.toPositionOrNull(): Position? =
    toIntOrNull()?.let { if (it !in 0..<BOARD_CELLS) null else Position(it) }
