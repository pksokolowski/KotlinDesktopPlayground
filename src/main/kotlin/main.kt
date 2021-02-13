import androidx.compose.desktop.Window
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import feature.dependencies.Dependency
import modules.mainModule
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.inject

fun main() {
    startKoin {
        modules(mainModule)
    }

    val dependency by inject(Dependency::class.java)

    Window {
        var inputText by remember { mutableStateOf("") }
        var outputList = remember { mutableStateListOf<String>() }
        var showDialog by remember { mutableStateOf(false) }

        MaterialTheme {
            Column(
                verticalArrangement = Arrangement.SpaceAround
            ) {
                DialogButton(
                    onClick = { showDialog = true }
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    EditorView(
                        text = inputText,
                        onTextChanged = { input ->
                            inputText = input
                        }
                    )

                    Button(
                        onClick = {
                            outputList.add(inputText)
                            inputText = ""
                        },
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(8.dp)
                    ) {
                        Text("Add")
                    }
                }

                Column {
                    outputList.forEach { item ->
                        Text(item)
                    }
                }
            }

            MainDialog(
                shown = showDialog,
                onDismiss = { showDialog = false }
            )
        }

    }
}

@Composable
@Suppress("FunctionName")
private fun EditorView(text: String, onTextChanged: (String) -> Unit) {
    TextField(
        value = text,
        onValueChange = { newText ->
            onTextChanged(newText)
        }
    )
}

@Composable
@Suppress("FunctionName")
private fun MainDialog(shown: Boolean, onDismiss: () -> Unit) {
    if (!shown) return

    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "Order of modifiers matters, below clickable is added before and after padding",
                style = TextStyle(
                    color = Color(100, 50, 50),
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text("click me 1", modifier = Modifier
                    .clickable { }
                    .padding(30.dp)
                )

                Text("click me 2", modifier = Modifier
                    .padding(30.dp)
                    .clickable { }
                )
            }

            Button(
                onClick = { onDismiss() },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 8.dp)
            ) {
                Text("Hide dialog")
            }
        }

    }
}

@Composable
@Suppress("FunctionName")
fun DialogButton(onClick: () -> Unit) {
    Row {
        Text(
            text = "Some label",
            modifier = Modifier
                .padding(0.dp, 0.dp, 8.dp, 0.dp)
                .align(Alignment.CenterVertically),
            style = MaterialTheme.typography.caption
        )
        Button(
            onClick = {
                onClick()
            }) {
            Text(
                text = "Click here!"
            )
        }
    }
}
