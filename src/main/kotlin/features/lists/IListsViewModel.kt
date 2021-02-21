package features.lists

import kotlinx.coroutines.flow.StateFlow

interface IListsViewModel {
    val inputText: StateFlow<String>
    val outputList: StateFlow<List<String>>
    fun setInputText(text: String)
    fun addInputTextToOutputList()
    fun goBack()
}