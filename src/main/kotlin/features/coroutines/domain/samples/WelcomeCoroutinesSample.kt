package features.coroutines.domain.samples

import kotlinx.coroutines.CoroutineScope

class WelcomeCoroutinesSample : CoroutinesSample(
    command = "welcome",
    explanation = """
    This is a welcome command, all it does is saying 
    hello. 
    
    also showcasing the way such commands can be set up.
    
    args
    name - the name of the person to greet
    times - number of greetings
""".trimIndent()
) {
    override fun start(scope: CoroutineScope, args: List<String>, output: (String) -> Unit) {
        val name = args.getOrNull(0) ?: "unknown"
        val times = args.getOrNull(1)?.toIntOrNull() ?: 1

        output("Hello $name ! x$times")
    }
}