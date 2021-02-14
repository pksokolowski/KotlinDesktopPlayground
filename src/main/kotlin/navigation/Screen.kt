package navigation

import androidx.compose.runtime.Composable

interface Screen {
    @Composable
    fun render()
}