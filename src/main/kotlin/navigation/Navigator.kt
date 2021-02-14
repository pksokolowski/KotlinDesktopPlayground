package navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class Navigator {
    private val backStack = mutableListOf<Screen>()
    private val _currentScreen = MutableStateFlow<Screen?>(null)
    val currentScreen = _currentScreen.asStateFlow()

    fun navigateTo(destination: Screen, addToBackStack: Boolean = true) {
        _currentScreen.value = destination
        if (addToBackStack) backStack += destination
    }

    fun popBackStack() {
        backStack.removeLastOrNull() ?: return
        val elementToShow = backStack.lastOrNull() ?: return
        navigateTo(elementToShow, false)
    }
}