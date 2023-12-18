package pt.isel.tds.ttt.viewModel

import androidx.compose.runtime.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pt.isel.tds.ttt.model.*

/**
 * The purpose of name editing
 */
enum class InputName(val txt: String) { FOR_START("Start"), FOR_JOIN("Join") }

/**
 * The application view model.
 * @param storage the storage used in matches.
 */
class AppViewModel(storage: MatchStorage, val scope: CoroutineScope) {
    var match by mutableStateOf(Match(storage))     // Model state
        private set

    val board get() = match.let{ (it as? MatchRun)?.game?.board  }
    val score get() = match.let{ (it as MatchRun).game.score  }
    val sidePlayer get() = (match as? MatchRun)?.sidePlayer
    val gameName get() = (match as MatchRun).id
    val firstPlayer get() = (match as? MatchRun)?.game?.firstPlayer

    fun newBoard() {
        match = match.newBoard()
        cancelWaiting()
        waitForOtherPlayer()
    }

    fun play(pos: Position) {
        try {
            match = match.play(pos)
            waitForOtherPlayer()
        }catch (e: IllegalStateException) {
            message = e.message
        }
    }

    var showScore by mutableStateOf(false)  // ScoreDialog state
        private set

    fun showScore() { if (match is MatchRun) showScore=true }
    fun hideScore() { showScore=false }

    var inputName by mutableStateOf<InputName?>(null) // StartOrJoinDialog state
        private set

    fun readName(inputName: InputName) { this.inputName = inputName }
    fun cancelInput() { inputName = null }
    fun startOrJoin(name: Name) {
        cancelWaiting()
        match = if (inputName==InputName.FOR_START)
            match.start(name)
        else
            match.join(name).also { waitForOtherPlayer() }
        inputName = null
    }

    var message by mutableStateOf<String?>(null)  // MessageDialog state
        private set

    fun clearMessage() { message = null }

    fun exit() {
        match.exit()
        cancelWaiting()
    }

    private var waitingJob by mutableStateOf<Job?>(null)

    val isWaiting get() = waitingJob != null

    val newBoardAvailable get() = match is MatchRun && board.let {
        it is BoardRun && it.turn == sidePlayer || it !is BoardRun && firstPlayer == sidePlayer
    }
    private val isThisSidesTurn get() = sidePlayer!=null && (board as? BoardRun)?.turn == sidePlayer || newBoardAvailable

    private fun cancelWaiting() {
        waitingJob?.cancel()
        waitingJob = null
    }

    private fun waitForOtherPlayer() {
        if (waitingJob!=null || isThisSidesTurn) return
        waitingJob = scope.launch {
            while (!isThisSidesTurn) {
                delay(3000)
                try { match = match.refresh() }
                catch (e: NoChangeException) { /* Ignore */ }
                catch (e: Exception) {
                    message = e.message
                    if (e is GameDeletedException) match = Match(match.ms)
                }
            }
            waitingJob = null
        }
    }
}