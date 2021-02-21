package features.mainScreen.presentation

import kotlinx.coroutines.flow.StateFlow

interface IMainViewModel {
    val inputText: StateFlow<String>
    val outputList: StateFlow<List<String>>
    val showDialog: StateFlow<Boolean>
    fun setInputText(text: String)
    fun addInputTextToOutputList()
    fun showDialog()
    fun dismissDialog()
    fun gotoTypingSpeedScreen()
    fun gotoCountriesScreen()
    fun gotoListsScreen()
}