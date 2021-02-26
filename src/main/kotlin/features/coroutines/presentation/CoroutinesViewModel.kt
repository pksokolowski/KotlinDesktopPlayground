package features.coroutines.presentation

import features.coroutines.domain.samples.CoroutinesSample
import features.coroutines.presentation.state_animations.animateTextHint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import navigation.NavDestination
import navigation.Navigator
import java.lang.StringBuilder

class CoroutinesViewModel(
    private val navigator: Navigator,
    private val samples: List<CoroutinesSample>
) : ICoroutinesViewModel {
    private val coroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private val samplesScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    override val outputText = MutableStateFlow("")
    override val explanationText = MutableStateFlow("")
    override val inputText = MutableStateFlow("")

    private val commandToSampleMapping = samples.map { sample ->
        sample.command to sample
    }.toMap()

    init {
        coroutineScope.animateTextHint("Type commands here") { inputText.value = it }
        displayGeneralExplanation()
    }

    override fun setInput(input: String) {
        if (input.isNotEmpty() && input.last() == '\n') return
        inputText.value = input
        handleCommand(input)
    }

    private fun handleCommand(command: String) {
        val sample = commandToSampleMapping[command] ?: run {
            samplesScope.coroutineContext.cancelChildren()
            displayGeneralExplanation()
            outputText.value = ""
            return
        }
        explanationText.value = sample.explanation
        sample.start(samplesScope, listOf(), ::output)
    }

    private fun output(line: String) {
        outputText.value += "\n$line"
    }

    private fun displayGeneralExplanation() {
        val builder = StringBuilder()
        builder.appendLine("Type one of the below commands:\n")
        commandToSampleMapping.keys.forEach { command ->
            builder.appendLine(command)
        }
        explanationText.value = builder.toString()
    }

    override fun goBack() {
        samplesScope.coroutineContext.cancelChildren()
        coroutineScope.coroutineContext.cancelChildren()
        navigator.navigateTo(NavDestination.Previous)
    }
}