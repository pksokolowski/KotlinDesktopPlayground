package features.lists

import MainAppBar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import navigation.Screen
import org.koin.java.KoinJavaComponent.inject

class ListsScreen : Screen {
    private val viewModel by inject(IListsViewModel::class.java)

    @Composable
    @Suppress("FunctionName")
    @ExperimentalCoroutinesApi
    override fun render() {
        val inputText = viewModel.inputText.collectAsState()
        val outputList = viewModel.outputList.collectAsState()

        MaterialTheme {
            Scaffold(
                topBar = {
                    MainAppBar(
                        icon = Icons.Default.ArrowBack,
                        title = "RecyclerView-like list",
                        onIconClick = { viewModel.goBack() }
                    )
                }
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp)
                ) {
                    Column {
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
                }
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

}