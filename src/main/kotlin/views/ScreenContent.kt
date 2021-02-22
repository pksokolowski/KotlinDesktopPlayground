package views

import MainAppBar
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
@Suppress("FunctionName")
fun ScreenContent(
    title: String,
    onBackPress: () -> Unit,
    padding: Dp = 0.dp,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            MainAppBar(
                icon = Icons.Default.ArrowBack,
                title = title,
                onIconClick = { onBackPress() }
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            content()
        }
    }
}