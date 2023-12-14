package pt.isel.tds

import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.window.*
import kotlinx.coroutines.*

fun main() {
    application(exitProcessOnExit = false) {
        Window(onCloseRequest = ::exitApplication) {
            val scope = rememberCoroutineScope()
            var job: Job? by remember { mutableStateOf(null) }
            log("Start App: Job=$job")
            Row {
                Button(enabled = job==null, onClick = {
                    log("Clicked.")
                    job = scope.launch {
                        log("Waiting..")
                        repeat(5) {
                            delay(1000)
                            print('.')
                        }
                        job = null
                    }
                }) { Text("Click me") }
                Button(enabled = job!=null, onClick = {
                    job?.cancel()
                    job = null
                }) { Text("Enable") }
            }
        }
    }
}

fun log(label: String) {
    println("$label thread=${Thread.currentThread().name}")
}
