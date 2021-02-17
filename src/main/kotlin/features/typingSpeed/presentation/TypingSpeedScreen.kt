package features.typingSpeed.presentation

import MainAppBar
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import navigation.Screen
import org.koin.java.KoinJavaComponent

@Suppress("FunctionName")
@ExperimentalCoroutinesApi
class TypingSpeedScreen : Screen {
    private val viewModel by KoinJavaComponent.inject(ITypingSpeedViewModel::class.java)

    @Composable
    override fun render() {
        val challengeText = viewModel.challengeText.collectAsState()
        val inputText = viewModel.inputText.collectAsState()
        val lastTypingSpeed = viewModel.lastTypingSpeed.collectAsState()

        MaterialTheme {
            Scaffold(
                topBar = {
                    MainAppBar(
                        icon = Icons.Default.ArrowBack,
                        title = "Typing speed",
                        onIconClick = { viewModel.goBack() }
                    )
                }
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(8.dp)
                    ) {
                        Text(
                            text = challengeText.value
                        )
                        Row {
                            TextField(
                                value = inputText.value,
                                onValueChange = { newValue ->
                                    viewModel.setInputText(newValue)
                                }
                            )
                            Button(
                                onClick = {
                                    viewModel.saveWord()
                                }
                            ) {
                                Text("Save")
                            }
                        }
                        lastTypingSpeed.value?.let { speed ->
                            Providers(AmbientContentAlpha provides ContentAlpha.medium) {
                                Text("$speed ms per character")
                            }
                        }
                    }
                }
            }
        }
    }
}
