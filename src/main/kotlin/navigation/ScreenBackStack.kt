package navigation

class ScreenBackStack {
    private val backStack = mutableListOf<Screen>()

    fun getPrevious(): Screen? {
        backStack.removeLastOrNull() ?: return null
        return backStack.lastOrNull()
    }

    fun put(screen: Screen?) {
        if (screen == null) return
        backStack += screen
    }
}