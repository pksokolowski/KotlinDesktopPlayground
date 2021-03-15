package features.mainScreen.presentation

import kotlinx.coroutines.flow.StateFlow

interface IMainViewModel {
    val showDialog: StateFlow<Boolean>
    fun showDialog()
    fun dismissDialog()
    fun gotoTypingSpeedScreen()
    fun gotoCountriesScreen()
    fun gotoListsScreen()
    fun gotoCoroutinesScreen()
    fun gotoAnimationsScreen()
}