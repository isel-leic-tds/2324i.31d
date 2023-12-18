package pt.isel.tds.ttt

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
    val scope = rememberCoroutineScope()
    val vm = remember { AppViewModel(storage,scope) } // ViewModel (state & UI logic)
    AppMenu(vm, onExit)
    MaterialTheme {
        Column {
            BoardViewer(vm.board?.moves) { pos -> vm.play(pos) }
            StatusBar(vm.board, vm.sidePlayer)
            if (vm.showScore) ScoreViewer(vm.score, vm.gameName) { vm.hideScore() }
            vm.inputName?.let{ InputNameEdit(it,
                onAction = { name -> vm.startOrJoin(name) },
                onCancel = { vm.cancelInput() }
            ) }
            vm.message?.let{ MessageDialog(it) { vm.clearMessage() } }
        }
        if (vm.isWaiting) WaitingIndicator()
    }
}

@Composable
fun WaitingIndicator() {
        CircularProgressIndicator(Modifier.size(BOARD_DIM).padding(25.dp),
            strokeWidth = 10.dp
        )
}

@Composable
fun FrameWindowScope.AppMenu(
    vm: AppViewModel,
    onExit: () -> Unit
) {
    MenuBar {
        Menu("Match") {
            Item("Start") { vm.readName(InputName.FOR_START) }
            Item("Join") { vm.readName(InputName.FOR_JOIN) }
            //Item("Refresh", enabled = vm.board!=null, onClick = vm::refresh )
            Item("Exit", onClick = {
                vm.exit()
                onExit()
            } )
        }
        Menu("Game") {
            Item("New Game", enabled = vm.newBoardAvailable, onClick = vm::newBoard )
            Item("Score", enabled = vm.board!=null, onClick = vm::showScore)
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
            onCloseRequest = {},//::exitApplication,
            title = "TDS - Galo",
            resizable = false,
            state = WindowState(size = DpSize.Unspecified),
        ) {
            TicTacToeApp(onExit = ::exitApplication, storage = storage)
}   }   }
