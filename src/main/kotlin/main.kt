import androidx.compose.desktop.Window
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.ExperimentalCoroutinesApi
import modules.mainModule
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.inject

@ExperimentalCoroutinesApi
fun main() {
    startKoin {
        modules(mainModule)
    }

    Window {
        val viewModel by inject(IMainViewModel::class.java)

        val inputText = viewModel.inputText.collectAsState()
        val outputList = viewModel.outputList.collectAsState()
        val showDialog = viewModel.showDialog.collectAsState()

        MaterialTheme {
            Scaffold(
                topBar = { AppBar(Icons.Default.Home) }
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Top
                    ) {
                        DialogButton(
                            label = "show dialog button",
                            onClick = { viewModel.showDialog() }
                        )

                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier
                                .padding(top = 8.dp)
                        ) {
                            EditorView(
                                text = inputText.value,
                                onTextChanged = { input ->
                                    viewModel.setInputText(input)
                                }
                            )

                            Button(
                                onClick = {
                                    viewModel.addInputTextToOutputList()
                                },
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(8.dp)
                            ) {
                                Text("Add")
                            }
                        }

                        LazyColumn {
                            items(outputList.value) { item ->
                                Text(item)
                            }
                        }
                    }

                    MainDialog(
                        shown = showDialog.value,
                        onDismiss = { viewModel.dismissDialog() }
                    )
                }
            }
        }
    }
}

@Composable
@Suppress("FunctionName")
private fun AppBar(icon: ImageVector, onIconClick: (() -> Unit)? = null) {
    TopAppBar(
        navigationIcon = {
            Icon(
                imageVector = icon,
                modifier = Modifier
                    .clickable { onIconClick?.invoke() }
                    .padding(12.dp)
            )
        },
        title = { Text("Desktop app") }
    )
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
fun DialogButton(label: String, onClick: () -> Unit) {
    Row {
        Text(
            text = label,
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
