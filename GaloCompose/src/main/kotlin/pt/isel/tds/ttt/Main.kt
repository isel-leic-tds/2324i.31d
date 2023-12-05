package pt.isel.tds.ttt

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import pt.isel.tds.storage.*
import pt.isel.tds.ttt.model.*
import pt.isel.tds.ttt.view.*
import pt.isel.tds.ttt.viewModel.AppViewModel
import pt.isel.tds.ttt.viewModel.InputName

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
            Item("Start") { vm.readName(InputName.FOR_START) }
            Item("Join") { vm.readName(InputName.FOR_JOIN) }
            Item("Refresh") {  }  // TODO: Menu
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
            vm.inputName?.let{ InputNameEdit(it,
                onAction = { name -> vm.startOrJoin(name) },
                onCancel = { vm.cancelInput() }
            ) }
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
