package features.coroutines.domain.samples

import kotlinx.coroutines.CoroutineScope

class WelcomeCoroutinesSample : CoroutinesSample(
    command = "welcome",
    explanation = """
    This is a welcome command, all it does is saying 
    hello. 
    
    also showcasing the way such commands can be set up.
""".trimIndent()
) {
    override fun start(scope: CoroutineScope, args: List<String>, output: (String) -> Unit) {
        output("Hello!")
    }
}