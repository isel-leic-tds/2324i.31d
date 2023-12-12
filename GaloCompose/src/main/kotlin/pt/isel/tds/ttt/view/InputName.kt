package pt.isel.tds.ttt.view

import androidx.compose.material.*
import androidx.compose.runtime.*
import pt.isel.tds.ttt.model.Name
import pt.isel.tds.ttt.viewModel.InputName

/**
 * Dialog to edit Name for Start or for Join.
 * @param inputName The input name to edit.
 * @param onAction The action to execute when dialog is confirmed.
 * @param onCancel The action to execute when dialog is cancelled
 */
@Composable
fun InputNameEdit(inputName: InputName, onAction: (Name)->Unit, onCancel: () -> Unit) {
    var name by remember { mutableStateOf("") }  // Local mutable state
    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text("Name for ${inputName.txt}", style = MaterialTheme.typography.h4) },
        text = {
            OutlinedTextField(
                value = name, onValueChange = { name = it }, label = { Text("Name") }
            )
        },
        dismissButton = { Button(onClick = onCancel) { Text("Cancel") } },
        confirmButton = { Button(onClick = { onAction(Name(name)) }) { Text(inputName.txt) } }
    )
}
