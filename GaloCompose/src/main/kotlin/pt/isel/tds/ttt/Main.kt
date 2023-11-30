package pt.isel.tds.ttt

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import pt.isel.tds.storage.*
import pt.isel.tds.ttt.model.*
import pt.isel.tds.ttt.view.*
import pt.isel.tds.ttt.viewModel.AppViewModel

/**
 * Shows the game.
 * @param onExit the function to call on exit the game.
 * @param storage the storage used in matches.
 */
@Composable
fun FrameWindowScope.TicTacToeApp(onExit: () -> Unit, storage: MatchStorage) {
    val vm = remember { AppViewModel(storage) } // ViewModel (state & UI logic)
    MenuBar {
        Menu("Match") {
            // TODO: Menu
            Item("Start") {  }
            Item("Join") {  }
            Item("Refresh") {  }
            Item("Exit", onClick = onExit)
        }
        Menu("Game") {
            Item("New Game") { vm.newBoard() }
            Item("Score") { vm.showScore() }
        }
    }
    MaterialTheme {
        Column {
            BoardViewer(vm.board?.moves) { pos -> vm.play(pos) }
            StatusBar(vm.board)
            if (vm.showScore) ScoreViewer(vm.score) { vm.hideScore() }
        }
    }
}

/**
 * The TicTacToe game in "compose desktop".
 */
fun main() = MongoDriver("galo").use { driver ->
    val storage = MongoStorage<Name,_>(driver, "games", GameSerializer)
    application(exitProcessOnExit = false) {
        Window(
            onCloseRequest = ::exitApplication,
            title = "TDS - Galo",
            resizable = false,
            state = WindowState(size = DpSize.Unspecified)
        ) {
            TicTacToeApp(onExit = ::exitApplication, storage = storage)
}   }   }
