package navigation

sealed class NavDestination(val addToBackStack: Boolean = true) {
    object Previous : NavDestination(false)
    object MainScreen : NavDestination()
    object TypingSpeedScreen : NavDestination()
    object CountriesScreen : NavDestination()
    object ListsScreen : NavDestination()
    object CoroutinesScreen : NavDestination()
}