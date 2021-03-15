import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
@Suppress("FunctionName")
fun MainAppBar(icon: ImageVector, title: String, onIconClick: (() -> Unit)? = null) {
    TopAppBar(
        navigationIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "icon of this screen",
                modifier = Modifier
                    .clickable { onIconClick?.invoke() }
                    .padding(12.dp)
            )
        },
        title = { Text(title) }
    )
}