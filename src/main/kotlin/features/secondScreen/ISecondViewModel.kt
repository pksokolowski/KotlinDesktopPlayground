package features.secondScreen

import kotlinx.coroutines.flow.StateFlow

interface ISecondViewModel {
    val inputText: StateFlow<String>
    val challengeText: StateFlow<String>
    fun goBack()
}