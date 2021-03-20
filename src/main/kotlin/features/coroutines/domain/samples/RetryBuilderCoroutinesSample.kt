package features.coroutines.domain.samples

import features.coroutines.domain.custom.stubborn
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlin.random.Random

class RetryBuilderCoroutinesSample : CoroutinesSample(
    command = "stubbornCoroutineBuilder",
    explanation = """
        Using a custom coroutine builder for retrying an operation 
        destined to be ran concurrently. On each failure (exception)
        the block of code provided is retried up to maxAttempts
        
        The code block provided fails with a 90% rate. 
        Up to 10 attempts will be made.
    """.trimIndent()
) {
    override fun start(scope: CoroutineScope, args: List<String>, output: (String) -> Unit) {
        val handler = CoroutineExceptionHandler { context, throwable ->
            output("handled an uncought exception")
        }

        scope.launch(handler) {
            var attemptsMade = 0

            supervisorScope {
                stubborn(10) {
                    attemptsMade++
                    if (Random.nextInt(10) > 8) {
                        output("success!")
                    } else {
                        output("failure...")
                        throw RuntimeException("failure")
                    }
                }
            }
        }
    }
}