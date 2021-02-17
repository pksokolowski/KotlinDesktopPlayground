import androidx.compose.desktop.Window
import androidx.compose.runtime.collectAsState
import features.mainScreen.presentation.MainScreen
import features.typingSpeed.presentation.TypingSpeedScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import modules.mainModule
import navigation.NavDestination
import navigation.Navigator
import navigation.ScreenBackStack
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.inject

@ExperimentalCoroutinesApi
fun main() {
    startKoin {
        modules(mainModule)
    }

    val navigator by inject(Navigator::class.java)
    navigator.navigateTo(NavDestination.MainScreen)

    val backStack by inject(ScreenBackStack::class.java)

    Window {
        val navDestinationState = navigator.currentScreen.collectAsState()
        val destination = navDestinationState.value

        val screen = when (destination) {
            is NavDestination.Previous -> {
                backStack.getPrevious()
            }
            is NavDestination.MainScreen -> {
                MainScreen()
            }
            is NavDestination.TypingSpeedScreen -> {
                TypingSpeedScreen()
            }
            null -> {
                null
            }
        }

        if (destination?.addToBackStack == true) backStack.put(screen)
        screen?.render()
    }
}