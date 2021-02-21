package features.mainScreen.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import navigation.NavDestination
import navigation.Navigator

class MainViewModel(private val navigator: Navigator) : IMainViewModel {
    override val showDialog = MutableStateFlow(false)

    override fun showDialog() {
        showDialog.value = true
    }

    override fun dismissDialog() {
        showDialog.value = false
    }

    override fun gotoTypingSpeedScreen() {
        navigator.navigateTo(NavDestination.TypingSpeedScreen)
    }

    override fun gotoCountriesScreen() {
        navigator.navigateTo(NavDestination.CountriesScreen)
    }

    override fun gotoListsScreen() {
        navigator.navigateTo(NavDestination.ListsScreen)
    }
}