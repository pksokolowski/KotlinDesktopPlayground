package features.secondScreen

import kotlinx.coroutines.flow.MutableStateFlow

class SecondViewModel : ISecondViewModel {
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
}