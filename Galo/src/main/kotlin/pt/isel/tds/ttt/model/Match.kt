package pt.isel.tds.ttt.model

import pt.isel.tds.storage.Storage

typealias MatchStorage = Storage<String, Game>

/**
 * Represents a match using a remote storage to save the game state.
 * @property ms the storage of matches.
 */
open class Match(val ms: MatchStorage)

/**
 * Represents a match after start or join.
 * @property id the name of the game.
 * @property sidePlayer player of this side.
 * @property game the game state.
 */
class MatchRun(
    ms: MatchStorage,
    val id: String,
    val sidePlayer: Player,
    val game: Game,
) : Match(ms)

/**
 * Make a new Match with the new board state.
 */
fun MatchRun.copy(game: Game) = MatchRun(ms, id, sidePlayer, game)

/**
 * Start a new match with the first board.
 * The player of this side is X.
 */
fun Match.start(name: String): Match {
    val game = Game().newBoard()
    ms.create(name, game)
    return MatchRun(ms, name, Player.X, game)
}

/**
 * Join to a match with the [name].
 * The player of this side is O.
 */
fun Match.join(name: String): Match {
    val game = ms.read(name) ?: error("Match $name not found")
    return MatchRun(ms, name, Player.O, game)
}

/**
 * Auxiliary function to operations on a MatchRun.
 */
private fun Match.runOper(actions: MatchRun.()->Game): Match {
    check(this is MatchRun) { "Match not started" }
    return copy(actions())
}

/**
 * Make a new board.
 */
fun Match.newBoard() = runOper {
    game.newBoard().also { ms.update(id, it) }
}

/**
 * Make a play in the board.
 * Check if it is the turn of this side.
 */
fun Match.play(pos: Position) = runOper {
    val gameAfter = game.play(pos)
    check(sidePlayer == (game.board as BoardRun).turn) { "Not your turn" }
    ms.update(id, gameAfter)
    gameAfter
}

/**
 * Refresh the match state.
 * Check if the board changed.
 */
fun Match.refresh() = runOper {
    (ms.read(id) as Game).also { check(it!=game) { "Game not changed" } }
}