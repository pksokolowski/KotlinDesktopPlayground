import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.window.Dialog
import feature.dependencies.Dependency
import modules.mainModule
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.inject

fun main() = Window {
    startKoin {
        modules(mainModule)
    }

    var text by remember { mutableStateOf("Hello, World!") }
    var showDialog by remember { mutableStateOf(false) }

    val dependency by inject(Dependency::class.java)

    @Composable
    @Suppress("FunctionName")
    fun DialogButton() {
        Row {
            Text("Some label")
            Button({
                showDialog = true
            }) {
                Text("Click here!")
            }
        }
    }

    MaterialTheme {

        Column {
            DialogButton()

            Button(onClick = {
                text = dependency.getString()
            }) {
                Text(text)
            }

        }

        if (showDialog) {
            Dialog({
                showDialog = false
            }) {
                Text("Dialog's content :)")
                Button({ showDialog = false }) {
                    Text("Hide dialog")
                }
            }
        }
    }

}

