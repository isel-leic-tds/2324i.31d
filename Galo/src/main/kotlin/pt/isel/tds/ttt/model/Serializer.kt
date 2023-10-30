package pt.isel.tds.ttt.model

import pt.isel.tds.storage.Serializer

/*
data class Game(
    val board: Board? = null,
    val firstPlayer: Player = Player.X,
    val score: Score = (Player.entries+null).associateWith { 0 }
)
*/
object GameSerializer: Serializer<Game> {
    override fun serialize(data: Game): String = buildString {
        appendLine("${data.firstPlayer}")
        appendLine(data.score.entries.joinToString(" ") { "${it.key}:${it.value}" })
        data.board?.let { appendLine(BoardSerializer.serialize(it))}
    }
    override fun deserialize(text: String): Game =
        text.split("\n").let { lines ->
            val firstPlayer = Player.valueOf(lines[0])
            val score = lines[1].split(" ").associate { entry ->
                val (player, value) = entry.split(":")
                (if (player=="null") null else Player.valueOf(player)) to
                        value.toInt()
            }
            val board = if (lines[2].isBlank()) null
                        else BoardSerializer.deserialize(lines[2])
            Game(board, firstPlayer, score)
        }
}

/*
sealed class Board(val moves: Moves)
class BoardRun(moves: Moves, val turn: Player): Board(moves)
class BoardWin(moves: Moves, val winner: Player): Board(moves)
class BoardDraw(moves: Moves): Board(moves)
 */
object BoardSerializer: Serializer<Board> {
    override fun serialize(data: Board): String = "Board"
        // TODO()

    override fun deserialize(text: String): Board = Board()
        // TODO()
}