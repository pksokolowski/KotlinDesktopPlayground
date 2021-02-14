package navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class Navigator {
    private val _currentScreen = MutableStateFlow<NavDestination?>(null)
    val currentScreen = _currentScreen.asStateFlow()

    fun navigateTo(destination: NavDestination) {
        _currentScreen.value = destination
    }
}