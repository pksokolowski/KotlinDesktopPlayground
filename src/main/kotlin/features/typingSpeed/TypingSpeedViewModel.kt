package features.typingSpeed

import kotlinx.coroutines.flow.MutableStateFlow
import navigation.NavDestination
import navigation.Navigator

class TypingSpeedViewModel(private val navigator: Navigator) : ITypingSpeedViewModel {
    override val challengeText = MutableStateFlow("")
    override val inputText = MutableStateFlow("")
    override val lastTypingSpeed = MutableStateFlow<Long?>(null)

    private val words = listOf(
        "String",
        "MutableStateFlow",
        "MutableLiveData",
        "Provider",
        "State",
        "MainViewModel",
    )

    private var lastChallengeStart: Long? = null

    init {
        newChallenge()
    }

    private fun newChallenge() {
        challengeText.value = words.random()
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
        navigator.navigateTo(NavDestination.Previous)
    }
}