package pt.isel.tds.ttt.view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pt.isel.tds.ttt.model.Player
import pt.isel.tds.ttt.model.Score

@Composable
@Preview
fun ScoreViewerPreview() {
    ScoreViewer(mapOf(
        Player.X to 1,
        Player.O to 2,
        null to 3
    )) {}
}

/**
 * Dialog to show the score.
 * @param score The score to show.
 * @param onClose The action to execute when dialog is closed.
 */
@Composable
fun ScoreViewer(score: Score, onClose: () -> Unit) = AlertDialog(
    onDismissRequest = onClose,
    title = { Text("Score", style = MaterialTheme.typography.h3) },
    text = {
        Row(
            Modifier.fillMaxWidth(0.8f).padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Player.entries.forEach {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Cell(modifier = Modifier.size(40.dp), player = it)
                        Text(" - ${score[it]}", style = MaterialTheme.typography.h4)
                    }
                }
            }
            Text("Draw - ${score[null]}", style = MaterialTheme.typography.h4)
        }
    },
    confirmButton = { Button(onClick = onClose) { Text("OK") } }
)
