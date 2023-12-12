package pt.isel.tds.ttt.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pt.isel.tds.ttt.model.*

val STATUS_BAR_DIM = 50.dp

/**
 * Shows the status bar: Turn, winner or draw.
 * @param board The board with the status to show.
 */
@Composable
fun StatusBar(board: Board?, sidePlayer: Player?) {
    Row(
        modifier = Modifier.width(BOARD_DIM).height(STATUS_BAR_DIM).background(color = Color.LightGray),
        horizontalArrangement = Arrangement.Center
    ) {
        sidePlayer?.let {
            Text("player: ", style = MaterialTheme.typography.h4)
            Cell(Modifier.size(STATUS_BAR_DIM), it)
        }
        val (txt, player) = when (board) {
            null -> "Game not started" to null
            is BoardRun -> "turn: " to board.turn
            is BoardWin -> "winner: " to board.winner
            is BoardDraw -> "Draw" to null
        }
        Text(txt, style = MaterialTheme.typography.h4)
        player?.let { Cell(Modifier.size(STATUS_BAR_DIM), it) }
    }
}