import androidx.compose.desktop.Window
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import feature.dependencies.Dependency
import modules.mainModule
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.inject

fun main() = Window {
    startKoin {
        modules(mainModule)
    }

    var text by remember { mutableStateOf("Hello, World!") }

    val dependency by inject(Dependency::class.java)

    MaterialTheme {
        Button(onClick = {
            text = dependency.getString()
        }) {
            Text(text)
        }
    }
}