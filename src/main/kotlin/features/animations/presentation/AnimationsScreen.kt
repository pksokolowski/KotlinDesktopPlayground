package features.animations.presentation

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
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
            val stateAnimationSlidePercentage = viewModel.stateAnimationSlidePercentage.collectAsState()

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

                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .height(48.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                viewModel.toggleAutoAnimationSlide()
                                viewModel.startStateAnimationSlide()
                            }
                        ) {
                            Text("Slide...")
                        }

                        Canvas(
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .fillMaxSize(0.5f)
                        ) {
                            drawRect(
                                color = Color.Gray,
                                topLeft = Offset(x = 0f, y = 0f),
                                size = Size(size.width, size.height / 2)
                            )

                            drawRect(
                                color = Color.Blue,
                                topLeft = Offset(x = 0f, y = size.height / 2f),
                                size = Size(
                                    width = size.width * stateAnimationSlidePercentage.value / 100,
                                    height = size.height / 2f
                                )
                            )
                        }
                    }
                }
            }
        }
    }

}