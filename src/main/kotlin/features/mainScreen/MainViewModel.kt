package features.mainScreen

import kotlinx.coroutines.flow.MutableStateFlow
import navigation.NavDestination
import navigation.Navigator
import kotlin.properties.Delegates

class MainViewModel(private val navigator: Navigator) : IMainViewModel {
    override val inputText = MutableStateFlow("")
    override val outputList = MutableStateFlow(listOf<String>())
    override val showDialog = MutableStateFlow(false)

    private var outputListSource by Delegates.observable(outputList.value) { _, _, new ->
        outputList.value = new
    }

    override fun setInputText(text: String) {
        inputText.value = text
    }

    override fun addInputTextToOutputList() {
        outputListSource = outputListSource + inputText.value
        inputText.value = ""
    }

    override fun showDialog() {
        showDialog.value = true
    }

    override fun dismissDialog() {
        showDialog.value = false
    }

    override fun gotoSecondScreen() {
        navigator.navigateTo(NavDestination.TypingSpeedScreen)
    }
}