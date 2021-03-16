package features.animations.presentation

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
            val crossFadeContentVisible = viewModel.crossFadeContentVisible.collectAsState()

            ScreenContent(
                title = "Animations",
                onBackPress = { viewModel.goBack() }
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { viewModel.toggleCrossFadeContentVisibility() }
                        ) {
                            Text("toggle crossFade")
                        }

                        Crossfade(
                            targetState = crossFadeContentVisible.value,
                            modifier = Modifier
                                .padding(8.dp)
                        ) { visible ->
                            when (visible) {
                                true -> Text("Visible!")
                                false -> Text("Not so much anymore...")
                            }
                        }
                    }
                }
            }
        }
    }

}