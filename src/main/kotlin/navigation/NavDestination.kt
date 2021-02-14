package navigation

sealed class NavDestination(val addToBackStack: Boolean = true) {
    object Previous : NavDestination(false)
    object MainScreen : NavDestination()
    object SecondScreen : NavDestination()
}