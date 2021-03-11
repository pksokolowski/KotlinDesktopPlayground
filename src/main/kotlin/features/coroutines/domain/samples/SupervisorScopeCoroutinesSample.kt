package features.coroutines.domain.samples

import kotlinx.coroutines.*
import java.lang.RuntimeException

class SupervisorScopeCoroutinesSample : CoroutinesSample(
    command = "supervisorScope",
    explanation = """
        using supervisorScope {} to prevent cancellation propagation across sibling coroutines
        when not using a SupervisorJob-based scope already, this might be of use.
        
        you can also peak what would happen without it by providing a "n" argument for the first
        parameter (see params below).
        
        params:
        useSupervisorScope - y/n to whether supervisorScope {} should be used
    """.trimIndent()
) {
    override fun start(scope: CoroutineScope, args: List<String>, output: (String) -> Unit) {
        val useSupervisorScope = args.getOrNull(0) != "n"

        val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
            output("exception caught: $throwable")
        }

        scope.launch(handler) {
            suspend fun useRightScope(block: suspend CoroutineScope.() -> Unit) =
                if (useSupervisorScope) supervisorScope(block) else coroutineScope(block)

            useRightScope {
                launch {
                    delay(500)
                    throw RuntimeException("unfortunately")
                }

                launch {
                    delay(1000)
                    output("even though a sibling job collapsed into nothing with a crash, this one luckily finished")
                }
            }
        }
    }
}