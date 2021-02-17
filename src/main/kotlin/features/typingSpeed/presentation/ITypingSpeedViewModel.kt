package features.typingSpeed.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface ITypingSpeedViewModel {
    val inputText: StateFlow<String>
    val challengeText: MutableStateFlow<String?>
    val lastTypingSpeed: StateFlow<Long?>
    fun goBack()
    fun setInputText(text: String)
    fun saveWord()
}