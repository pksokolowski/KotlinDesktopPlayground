package features.secondScreen

import kotlinx.coroutines.flow.MutableStateFlow
import navigation.NavDestination
import navigation.Navigator

class SecondViewModel(private val navigator: Navigator) : ISecondViewModel {
    override val challengeText = MutableStateFlow("")
    override val inputText = MutableStateFlow("")

    private val words = listOf(
        "String",
        "MutableStateFlow",
        "MutableLiveData",
        "Provider",
        "State",
        "MainViewModel",
    )

    override fun goBack() {
        navigator.navigateTo(NavDestination.Previous)
    }
}