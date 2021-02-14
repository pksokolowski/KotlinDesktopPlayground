import androidx.compose.desktop.Window
import androidx.compose.runtime.collectAsState
import features.mainScreen.MainScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import modules.mainModule
import navigation.Navigator
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.inject

@ExperimentalCoroutinesApi
fun main() {
    startKoin {
        modules(mainModule)
    }

    val navigator by inject(Navigator::class.java)
    navigator.navigateTo(MainScreen())

    Window {
        val screen = navigator.currentScreen.collectAsState()

        screen.value?.render()
    }
}