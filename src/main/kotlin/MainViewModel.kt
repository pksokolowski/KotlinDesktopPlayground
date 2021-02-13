import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel {
    private var outputListSource = listOf<String>()

    val inputText = MutableStateFlow("")
    val outputList = MutableStateFlow(outputListSource)
    val showDialog = MutableStateFlow(false)

    fun setInputText(text: String) {
        inputText.value = text
    }

    fun addInputTextToOutputList() {
        outputListSource = outputListSource + inputText.value
        outputList.value = outputListSource
        inputText.value = ""
    }

    fun showDialog() {
        showDialog.value = true
    }

    fun dismissDialog() {
        showDialog.value = false
    }
}