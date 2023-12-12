package pt.isel.tds.ttt.view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import pt.isel.tds.ttt.model.*

@Composable
@Preview
fun BoardViewerPreview() {
    BoardViewer(Board().play(Position(4)).play(Position(0)).moves) {}
}

val CELL_DIM = 150.dp
val GRID_LINE_DIM = 5.dp
val BOARD_DIM = GRID_LINE_DIM * (BOARD_SIZE -1) + BOARD_SIZE * CELL_DIM

/**
 * Shows the board.
 * @param moves The moves to show.
 * @param onClick The action to execute when a position is clicked.
 */
@Composable
fun BoardViewer(moves: Moves?, onClick: (Position) -> Unit) {
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
                    val pos = Position(row, col)
                    Cell(player = moves?.get(pos) ){
                        onClick(pos)
                    }
                }
            }
        }
    }
}