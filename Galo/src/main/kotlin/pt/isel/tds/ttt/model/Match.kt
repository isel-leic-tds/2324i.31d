package pt.isel.tds.ttt.model

import pt.isel.tds.storage.Storage

typealias MatchStorage = Storage<Name, Game>

/**
 * Represents a name of a match.
 * Must be a nonempty string with only letters and digits.
 */
@JvmInline
value class Name(private val value: String) {
    init {
        require( isValid(value) ) { "Invalid name $value" }
    }
    override fun toString() = value
    companion object {
        fun isValid(value: String) =
            value.isNotBlank() && value.all { it.isLetterOrDigit() }
    }
}
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
    val id: Name,
    val sidePlayer: Player,
    val game: Game,
) : Match(ms)

/**
 * Property to access the game state.
 */
val Match.gameRun: Game? get() = (this as? MatchRun)?.game

/**
 * Make a new Match with the new board state.
 */
private fun MatchRun.copy(game: Game) = MatchRun(ms, id, sidePlayer, game)

/**
 * Start a new match with the [name] and with the first board.
 * The player of this side is X.
 */
fun Match.start(name: Name): Match {
    val game = Game().newBoard()
    ms.create(name, game)
    deleteIfIsOwner()
    return MatchRun(ms, name, Player.X, game)
}

/**
 * Join to a match with the [name].
 * The player of this side is O.
 */
fun Match.join(name: Name): Match {
    val game = ms.read(name) ?: error("Match $name not found")
    deleteIfIsOwner()
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

/**
 * Auxiliary function to delete the current match in store
 */
private fun Match.deleteIfIsOwner(){
    if (this is MatchRun && sidePlayer==Player.X)
        ms.delete(id)
}

/**
 * Called when the application is closed.
 */
fun Match.exit() {
    deleteIfIsOwner()
}