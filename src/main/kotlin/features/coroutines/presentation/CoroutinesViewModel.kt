package features.coroutines.presentation

import features.coroutines.domain.samples.CoroutinesSample
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import navigation.NavDestination
import navigation.Navigator

class CoroutinesViewModel(
    private val navigator: Navigator,
    private val samples: List<CoroutinesSample>
) : ICoroutinesViewModel {
    private val coroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private val samplesScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    override val outputText = MutableStateFlow("")
    override val inputText = MutableStateFlow("")

    private val commandToSampleMapping = samples.map { sample ->
        sample.command to sample
    }.toMap()

    override fun setInput(input: String) {
        if (input.isNotEmpty() && input.last() == '\n') return
        inputText.value = input
        handleCommand(input)
    }

    private fun handleCommand(command: String) {
        val sample = commandToSampleMapping[command] ?: run {
            outputText.value = ""
            samplesScope.coroutineContext.cancelChildren()
            return
        }
        sample.start(samplesScope, listOf(), ::output)
    }

    private fun output(line: String) {
        outputText.value += "\n$line"
    }

    override fun goBack() {
        samplesScope.coroutineContext.cancelChildren()
        coroutineScope.coroutineContext.cancelChildren()
        navigator.navigateTo(NavDestination.Previous)
    }
}