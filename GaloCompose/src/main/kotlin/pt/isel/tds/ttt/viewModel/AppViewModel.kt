package pt.isel.tds.ttt.viewModel

import androidx.compose.runtime.*
import pt.isel.tds.ttt.model.*

/**
 * The purpose of name editing
 */
enum class InputName(val txt: String) { FOR_START("Start"), FOR_JOIN("Join") }

/**
 * The application view model.
 * @param storage the storage used in matches.
 */
class AppViewModel(storage: MatchStorage) {
    var match by mutableStateOf(Match(storage))     // Model state
        private set

    val board get() = match.let{ (it as? MatchRun)?.game?.board  }
    val score get() = match.let{ (it as MatchRun).game.score  }

    fun newBoard() { match = match.newBoard() }

    fun play(pos: Position) { match = match.play(pos) }

    var showScore by mutableStateOf(false)  // ScoreDialog state
        private set

    fun showScore() { if (match is MatchRun) showScore=true }
    fun hideScore() { showScore=false }

    var inputName by mutableStateOf<InputName?>(null) // StartOrJoinDialog state
        private set

    fun readName(inputName: InputName) { this.inputName = inputName }
    fun cancelInput() { inputName = null }
    fun startOrJoin(name: Name) {
        match = if (inputName==InputName.FOR_START)
            match.start(name)
        else
            match.join(name)
        inputName = null
    }
}