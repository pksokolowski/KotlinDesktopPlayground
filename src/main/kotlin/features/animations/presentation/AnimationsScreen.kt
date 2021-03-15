package features.animations.presentation

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import navigation.Screen
import org.koin.java.KoinJavaComponent
import views.ScreenContent

class AnimationsScreen : Screen {
    private val viewModel by KoinJavaComponent.inject(IAnimationsViewModel::class.java)

    @Composable
    @Suppress("FunctionName")
    @ExperimentalCoroutinesApi
    override fun render() {
        MaterialTheme {


            ScreenContent(
                title = "Animations",
                onBackPress = { viewModel.goBack() }
            ) {

            }
        }
    }

}