package features.secondScreen

import kotlinx.coroutines.flow.StateFlow

interface ITypingSpeedViewModel {
    val inputText: StateFlow<String>
    val challengeText: StateFlow<String>
    val lastTypingSpeed: StateFlow<Long?>
    fun goBack()
    fun setInputText(text: String)
}