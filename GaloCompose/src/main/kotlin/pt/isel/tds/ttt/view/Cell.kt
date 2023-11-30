package pt.isel.tds.ttt.view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pt.isel.tds.ttt.model.Player

@Composable
@Preview
fun CellPreview() {
    Cell(player = Player.X)
}

/**
 * Shows the player in the board or an empty box clickable.
 * @param modifier The modifier to apply.
 * @param player The player to show.
 * @param onClick The action to execute when the box is clicked.
 */
@Composable
fun Cell(
    modifier: Modifier = Modifier.size(CELL_DIM).background(color = Color.White),
    player: Player?,
    onClick: () -> Unit = {}
) {
    if (player == null) Box(modifier.clickable(onClick = onClick))
    else Image(
        painterResource(
            if (player == Player.X) "cross.png"
            else "circle.png"
        ),
        contentDescription = null,
        modifier
    )
}