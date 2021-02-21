package features.lists

import kotlinx.coroutines.flow.MutableStateFlow
import navigation.NavDestination
import navigation.Navigator

class ListsViewModel(
    private val navigator: Navigator
) : IListsViewModel {
    override val inputText = MutableStateFlow("")
    override val outputList = MutableStateFlow(listOf<String>())

    override fun setInputText(text: String) {
        inputText.value = text
    }

    override fun addInputTextToOutputList() {
        outputList.value = outputList.value + inputText.value
        inputText.value = ""
    }

    override fun goBack() {
        navigator.navigateTo(NavDestination.Previous)
    }
}