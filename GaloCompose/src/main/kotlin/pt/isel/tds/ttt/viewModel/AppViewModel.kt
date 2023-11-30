package pt.isel.tds.ttt.viewModel

import androidx.compose.runtime.*
import pt.isel.tds.ttt.model.*

class AppViewModel(storage: MatchStorage) {
    var game by mutableStateOf(Game())  // TODO: Match storage
        private set

    val board get() = game.board
    val score get() = game.score

    fun newBoard() { game = game.newBoard() }

    fun play(pos: Position) { if (game.board is BoardRun) game = game.play(pos) }

    var showScore by mutableStateOf(false)
        private set

    fun showScore() { showScore=true }
    fun hideScore() { showScore=false }
}