package features.mainScreen.presentation

import MainAppBar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.ExperimentalCoroutinesApi
import navigation.Screen
import org.koin.java.KoinJavaComponent.inject

class MainScreen : Screen {
    private val viewModel by inject(IMainViewModel::class.java)

    @Composable
    @Suppress("FunctionName")
    @ExperimentalCoroutinesApi
    override fun render() {
        val showDialog = viewModel.showDialog.collectAsState()

        MaterialTheme {
            Scaffold(
                topBar = {
                    MainAppBar(
                        icon = Icons.Default.Home,
                        title = "Kotlin Desktop Playground"
                    )
                }
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                        ) {
                            listOf(
                                "Persistence - typing speed" to {
                                    viewModel.gotoTypingSpeedScreen()
                                },
                                "Retrofit - countries" to {
                                    viewModel.gotoCountriesScreen()
                                },
                                "Recycler-view-like list" to {
                                    viewModel.gotoListsScreen()
                                },
                                "A dialog" to {
                                    viewModel.showDialog()
                                },
                            )
                                .sortedBy { it.first.length }
                                .let {
                                    ButtonRows(it)
                                }
                        }

                        Column(
                            verticalArrangement = Arrangement.Top
                        ) {
                            Text(
                                text = """
                                    This is a playground app for Kotlin Desktop
                                    
                                    It is meant for exploration and learning of the technology and 
                                    finding what is possible with it. On the left hand side, there
                                    is a list of buttons which you can use to traverse the supported
                                    features.
                                    
                                    Have fun.
                                """.trimIndent(),
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier
                                    .padding(start = 8.dp, end = 8.dp)
                            )
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

    @Composable
    @Suppress("FunctionName")
    private fun ButtonRows(
        titleActionRows: List<Pair<String, () -> Unit>>
    ) {
        Column {
            titleActionRows.forEach { rowData ->
                val (title, action) = rowData
                Button(
                    onClick = { action() },
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                ) {
                    Text(title)
                }
            }
        }
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
}