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
    override fun serialize(data: Board): String =
        when(data) {
            is BoardRun -> "Run ${data.turn}"
            is BoardWin -> "Win ${data.winner}"
            is BoardDraw -> "Draw"
        } + "|" +
        data.moves.entries.joinToString(" ") { (pos,player) -> "${pos}:${player}" }

    override fun deserialize(text: String): Board =
        text.split("|").let {
            val left = it[0].split(" ")
            val player = if (left.size==1) "" else left[1]
            val type = left[0]
            val moves =
                if (it[1].isBlank()) emptyMap()
                else {
                    it[1].split(" ").associate { entry ->
                        val (pos, player) = entry.split(":")
                        Position(pos.toInt()) to Player.valueOf(player)
                    }
                }
            when(type) {
                "Run" -> BoardRun(moves, Player.valueOf(player))
                "Win" -> BoardWin(moves, Player.valueOf(player))
                "Draw" -> BoardDraw(moves)
                else -> error("Invalid state name")
            }
        }
}