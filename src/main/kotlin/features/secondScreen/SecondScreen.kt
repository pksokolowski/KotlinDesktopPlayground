package features.secondScreen

import MainAppBar
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.java.KoinJavaComponent

@Composable
@Suppress("FunctionName")
@ExperimentalCoroutinesApi
fun SecondScreen() {
    val viewModel by KoinJavaComponent.inject(ISecondViewModel::class.java)

    val challengeText = viewModel.challengeText.collectAsState()
    val inputText = viewModel.inputText.collectAsState()

    MaterialTheme {
        Scaffold(
            topBar = { MainAppBar(Icons.Default.Home) }
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp)
            ) {

            }
        }
    }
}
