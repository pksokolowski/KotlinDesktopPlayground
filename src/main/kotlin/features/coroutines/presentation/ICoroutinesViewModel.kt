package features.coroutines.presentation

import kotlinx.coroutines.flow.MutableStateFlow

interface ICoroutinesViewModel {

    fun goBack()
    fun setInput(input: String)
    val inputText: MutableStateFlow<String>
    val outputText: MutableStateFlow<String>
}
