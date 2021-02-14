import androidx.compose.desktop.Window
import features.mainScreen.MainScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import modules.mainModule
import org.koin.core.context.startKoin

@ExperimentalCoroutinesApi
fun main() {
    startKoin {
        modules(mainModule)
    }

    Window {
        MainScreen()
    }
}