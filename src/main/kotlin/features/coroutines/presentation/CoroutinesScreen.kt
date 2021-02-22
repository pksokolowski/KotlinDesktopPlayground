package features.coroutines.presentation

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
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


            ScreenContent(
                title = "Coroutines",
                onBackPress = { viewModel.goBack() }
            ) {

            }
        }
    }


}