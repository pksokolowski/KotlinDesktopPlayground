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
    samples: List<CoroutinesSample>
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

        inputText
            .debounce(500)
            .map { it.trim() }
            .distinctUntilChanged()
            .onEach { handleInput(it) }
            .launchIn(coroutineScope)
    }

    override fun setInput(input: String) {
        if (input.isNotEmpty() && input.last() == '\n') return
        inputText.value = input
    }

    private fun handleInput(input: String) {
        samplesScope.coroutineContext.cancelChildren()
        outputText.value = ""

        val command = input.substringBefore(" ")
        val args = input
            .substringAfter(" ", "")
            .split(" ")
            .filter { it.isNotEmpty() }

        val sample = commandToSampleMapping[command] ?: run {
            displayGeneralExplanation()
            return
        }
        explanationText.value = sample.explanation
        sample.start(samplesScope, args, ::output)
    }

    @Synchronized
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