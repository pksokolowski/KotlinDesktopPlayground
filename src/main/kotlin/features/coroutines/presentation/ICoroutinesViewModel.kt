package features.coroutines.presentation

import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.flow.MutableStateFlow

interface ICoroutinesViewModel {

    fun goBack()
    fun setInput(input: TextFieldValue)
    val inputText: MutableStateFlow<TextFieldValue>
    val explanationText: MutableStateFlow<String>
    val outputText: MutableStateFlow<String>
}
