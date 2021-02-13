import androidx.compose.desktop.Window
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    Window {
        var text by remember { mutableStateOf("Hello, World!") }
        var showDialog by remember { mutableStateOf(false) }

        val dependency by inject(Dependency::class.java)

        @Composable
        @Suppress("FunctionName")
        fun DialogButton() {
            Row {
                Text(
                    text = "Some label",
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 8.dp, 0.dp)
                        .align(Alignment.CenterVertically)
                )
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
            }

            if (showDialog) {
                Dialog(
                    onDismissRequest = { showDialog = false }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {

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

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Button(
                                onClick = { showDialog = false },
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
            }
        }

    }
}
