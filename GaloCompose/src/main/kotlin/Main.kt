import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import pt.isel.tds.ttt.model.*

val CELL_DIM = 200.dp

/**
 * Shows the player in the board or an empty box clickable.
 * @param player The player to show.
 * @param onClick The action to execute when the box is clicked.
 */
@Composable
fun PlayerViewer(player: Player?, onClick: () -> Unit={}) {
    val modifier = Modifier.size(CELL_DIM).background(color = Color.White)
    if (player == null)
        Box(modifier.clickable(onClick=onClick))
    else
        Image(
            painterResource(
                if (player == Player.X) "cross.png"
                else "circle.png"
            ),
            contentDescription = null,
            modifier
        )
}

val GRID_LINE_DIM = 5.dp

/**
 * Shows the board.
 */
@Composable
fun BoardViewer(board: Board, onClick: (Position) -> Unit) {
    Column {
        repeat(BOARD_SIZE) { row ->
            Row {
                repeat(BOARD_SIZE) { col ->
                    val pos = Position(row,col)
                    PlayerViewer(board.moves[pos]) {
                        onClick(pos)
                    }
                }
            }
        }
    }
}

/**
 * Shows the game.
 * The mutable state is the board.
 */
@Composable
@Preview
fun TicTacToeApp() {
    var board:Board by remember { mutableStateOf(Board()) }
    MaterialTheme {
        BoardViewer(board) { pos ->
            if (board is BoardRun)  board = board.play(pos)
        }
    }
}

/**
 * The TicTacToe application in compose desktop.
 */
fun main() {
    application(exitProcessOnExit = false) {
        Window(
            onCloseRequest = ::exitApplication,
            state = WindowState(size= DpSize.Unspecified)
        ) {
            TicTacToeApp()
        }
    }
}

