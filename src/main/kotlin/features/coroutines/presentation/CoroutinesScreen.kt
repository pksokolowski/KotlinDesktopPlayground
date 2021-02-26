package features.coroutines.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import views.ScreenContent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import navigation.Screen
import org.koin.java.KoinJavaComponent

class CoroutinesScreen : Screen {
    private val viewModel by KoinJavaComponent.inject(ICoroutinesViewModel::class.java)

    @Composable
    @Suppress("FunctionName")
    @ExperimentalCoroutinesApi
    override fun render() {
        MaterialTheme {
            val input = viewModel.inputText.collectAsState()
            val output = viewModel.outputText.collectAsState()
            val explanation = viewModel.explanationText.collectAsState()

            ScreenContent(
                title = "Coroutines",
                onBackPress = { viewModel.goBack() }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(Color(15, 15, 15))
                ) {
                    Row(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(
                            text = output.value,
                            modifier = Modifier
                                .weight(3f)
                                .fillMaxHeight()
                                .background(Color.Black)
                                .padding(8.dp),
                            style = TextStyle(
                                color = Color.White
                            )
                        )

                        Spacer(
                            modifier = Modifier
                                .width(8.dp)
                        )

                        Text(
                            text = explanation.value,
                            modifier = Modifier
                                .weight(1.5f)
                                .fillMaxHeight()
                                .background(Color.Black)
                                .padding(8.dp),
                            style = TextStyle(
                                color = Color.White
                            )
                        )
                    }

                    TextField(
                        value = input.value,
                        onValueChange = viewModel::setInput,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(Alignment.Bottom),
                        textStyle = TextStyle(
                            color = Color.White
                        )
                    )
                }
            }
        }
    }


}