package features.typingSpeed.presentation

import features.typingSpeed.`use-cases`.GetTypingSpeedVocabularyUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import navigation.NavDestination
import navigation.Navigator

class TypingSpeedViewModel(
    private val navigator: Navigator,
    private val getTypingSpeedVocabularyUseCase: GetTypingSpeedVocabularyUseCase,
) : ITypingSpeedViewModel {
    private val coroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    override val challengeText = MutableStateFlow<String?>(null)
    override val inputText = MutableStateFlow("")
    override val lastTypingSpeed = MutableStateFlow<Long?>(null)

    private var words = listOf<String>()

    private var lastChallengeStart: Long? = null

    init {
        coroutineScope.launch {
            getTypingSpeedVocabularyUseCase.getVocabulary()
                .collect { vocabulary ->
                    val wasBlankBefore = words.isEmpty()
                    words = vocabulary
                    if (wasBlankBefore && words.isNotEmpty()) {
                        newChallenge()
                    }
                }
        }
    }

    private fun newChallenge() {
        challengeText.value = if (words.isNotEmpty()) words.random() else null
    }

    override fun setInputText(text: String) {
        if (text == challengeText.value) {
            showChallengeResults(text)
            inputText.value = ""
            newChallenge()
            return
        }
        if (inputText.value.isEmpty() && text.length == 1) {
            lastTypingSpeed.value = null
            lastChallengeStart = System.currentTimeMillis()
        }
        inputText.value = text
    }

    override fun saveWord() {
        val word = inputText.value
        coroutineScope.launch {
            getTypingSpeedVocabularyUseCase.saveWord(word)
        }
        inputText.value = ""
    }

    private fun showChallengeResults(text: String) {
        val now = System.currentTimeMillis()
        lastChallengeStart?.let { start ->
            val duration = now - start
            // text len - 1, because we only count since after the first char.
            val durationPerCharacter = duration / (text.length - 1)
            lastTypingSpeed.value = durationPerCharacter
        }
    }

    override fun goBack() {
        coroutineScope.coroutineContext.cancelChildren()
        navigator.navigateTo(NavDestination.Previous)
    }
}