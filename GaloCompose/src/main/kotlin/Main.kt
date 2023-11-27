import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import pt.isel.tds.ttt.model.*

val CELL_DIM = 200.dp

@Composable
@Preview
fun PlayerViewerPreview() {
    PlayerViewer(player = Player.X)
}

/**
 * Shows the player in the board or an empty box clickable.
 * @param modifier The modifier to apply.
 * @param player The player to show.
 * @param onClick The action to execute when the box is clicked.
 */
@Composable
fun PlayerViewer(
    modifier: Modifier = Modifier.size(CELL_DIM).background(color = Color.White),
    player: Player?,
    onClick: () -> Unit = {}
) {
    if (player == null) Box(modifier.clickable(onClick=onClick))
    else Image(
            painterResource(
                if (player == Player.X) "cross.png"
                else "circle.png"
            ),
            contentDescription = null,
            modifier
        )
}

@Composable
@Preview
fun BoardViewerPreview() {
    BoardViewer(Board().play(Position(4)).play(Position(0))) {}
}

val GRID_LINE_DIM = 5.dp
val BOARD_DIM = GRID_LINE_DIM * (BOARD_SIZE-1) + BOARD_SIZE * CELL_DIM

/**
 * Shows the board.
 * @param board The board with moves to show.
 * @param onClick The action to execute when a position is clicked.
 */
@Composable
fun BoardViewer(board: Board, onClick: (Position) -> Unit) {
    Column(
        modifier = Modifier.size(BOARD_DIM).background(color = Color.Black),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(BOARD_SIZE) { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                repeat(BOARD_SIZE) { col ->
                    val pos = Position(row,col)
                    PlayerViewer(player = board.moves[pos]) {
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
    var board by remember { mutableStateOf<Board>(Board()) }
    MaterialTheme {
        Column {
            BoardViewer(board) { pos ->
                if (board is BoardRun) board = board.play(pos)
            }
            StatusBar(board)
        }
    }
}

val STATUS_BAR_DIM = 50.dp

/**
 * Shows the status bar: Turn, winner or draw.
 * @param board The board with the status to show.
 */
@Composable
fun StatusBar(board: Board) {
    Row(
        modifier = Modifier.width(BOARD_DIM).height(STATUS_BAR_DIM).background(color = Color.LightGray),
        horizontalArrangement = Arrangement.Center
    ) {
        val (txt,player) = when (board) {
            is BoardRun -> "turn: " to board.turn
            is BoardWin -> "winner: " to board.winner
            is BoardDraw -> "Draw" to null
        }
        Text(txt, style = MaterialTheme.typography.h4)
        player?.let { PlayerViewer(Modifier.size(STATUS_BAR_DIM),it) }
    }
}

/**
 * The TicTacToe application in compose desktop.
 */
fun main() {
    application(exitProcessOnExit = false) {
        Window(
            onCloseRequest = ::exitApplication,
            title = "TDS - Galo",
            resizable = false,
            state = WindowState(size= DpSize.Unspecified)
        ) {
            TicTacToeApp()
        }
    }
}
