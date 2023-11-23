import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }
    log("Start App")
    MaterialTheme {
        Button(onClick = {
            log("OnClick")
            text = "Hello, Desktop!"
        }) {
            log("Button")
            Text(text)
        }
    }
}

fun main() {
    log("Start")
    application(exitProcessOnExit = false) {
        log("App")
        Window(onCloseRequest = ::exitApplication) {
            App()
        }
        log("End App")
    }
    log("End")
}

fun log(label: String) {
    println("$label thread=${Thread.currentThread().name}")
}
